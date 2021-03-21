package io.github.dragneelfps.moviereview.services

import io.github.dragneelfps.moviereview.datastore.MovieStore
import io.github.dragneelfps.moviereview.datastore.ReviewStore
import io.github.dragneelfps.moviereview.datastore.UserStore
import io.github.dragneelfps.moviereview.errors.CannotReviewMovie
import io.github.dragneelfps.moviereview.errors.InvalidReviewScore
import io.github.dragneelfps.moviereview.models.*
import io.github.dragneelfps.moviereview.usecases.ReviewMovie
import io.github.dragneelfps.moviereview.utils.generateId

class ReviewMovieService(
    private val userStore: UserStore,
    private val movieStore: MovieStore,
    private val reviewStore: ReviewStore
) : ReviewMovie {
    override fun review(reviewer: Username, movieId: MovieId, score: Int, content: String): Review {
        checkIfValidScore(score)

        val user = userStore[reviewer]
        val movie = movieStore[movieId]

        checkIfUserCanReview(reviewer, movie)

        val review = Review(
            id = generateId(),
            movieId = movieId,
            reviewer = reviewer,
            content = content,
            score = score
        )
        reviewStore += review

        upgradeUserIfEligible(user)

        return review
    }

    private fun upgradeUserIfEligible(user: User) {
        if (user.type == UserType.VIEWER && reviewStore.get(user.username).size > 3) {
            userStore.updateUserType(user.username, UserType.CRITIC)
        }
    }

    private fun checkIfValidScore(score: Int) {
        if (score !in 1..10) throw InvalidReviewScore("Score should be between 1 and 10")
    }

    private fun checkIfUserCanReview(username: Username, movie: Movie) {
        if (!movie.released) throw CannotReviewMovie("movie is unreleased")

        val existingReview = kotlin.runCatching { reviewStore.get(username, movie.id) }.getOrNull()
        if (existingReview != null) throw CannotReviewMovie("already reviewed")
    }
}