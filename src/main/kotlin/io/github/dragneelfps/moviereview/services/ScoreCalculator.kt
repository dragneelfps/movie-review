package io.github.dragneelfps.moviereview.services

import io.github.dragneelfps.moviereview.datastore.ReviewStore
import io.github.dragneelfps.moviereview.datastore.UserStore
import io.github.dragneelfps.moviereview.models.MovieId
import io.github.dragneelfps.moviereview.models.User
import io.github.dragneelfps.moviereview.models.UserType
import io.github.dragneelfps.moviereview.models.Username
import io.github.dragneelfps.moviereview.usecases.UserFilter

class ScoreCalculator(private val reviewStore: ReviewStore, private val userStore: UserStore) {

    fun calculate(movieId: MovieId, reviewFilter: UserFilter = { true }): Int? {
        val reviews = reviewStore.getMovieReviews(movieId)
        val users = reviews
            .asSequence()
            .map { review -> review.reviewer }
            .toSet()
            .fold(emptyMap<Username, User>()) { acc, username -> acc + (username to userStore[username]) }
        return if (reviews.isNotEmpty()) {
            reviews.sumBy { review ->
                val reviewer = users[review.reviewer]!!
                if (reviewFilter(reviewer)) {
                    val multiplier = if (reviewer.type == UserType.CRITIC) 2 else 1
                    review.score * multiplier
                } else 0
            }
        } else {
            null
        }
    }
}