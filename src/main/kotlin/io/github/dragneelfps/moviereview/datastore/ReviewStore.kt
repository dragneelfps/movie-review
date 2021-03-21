package io.github.dragneelfps.moviereview.datastore

import io.github.dragneelfps.moviereview.errors.ReviewDoesNotExists
import io.github.dragneelfps.moviereview.models.MovieId
import io.github.dragneelfps.moviereview.models.Review
import io.github.dragneelfps.moviereview.models.ReviewId
import io.github.dragneelfps.moviereview.models.Username

interface ReviewStore {
    operator fun contains(reviewId: ReviewId): Boolean

    operator fun plusAssign(review: Review)

    @Throws(ReviewDoesNotExists::class)
    operator fun get(reviewId: ReviewId): Review

    @Throws(ReviewDoesNotExists::class)
    fun get(username: Username, movieId: MovieId): Review

    fun get(username: Username): List<Review>

    fun getMovieReviews(movieId: MovieId): List<Review>

    fun all(): List<Review>
}

object InMemoryReviewStore : ReviewStore {

    private val reviews = mutableMapOf<ReviewId, Review>()

    override fun contains(reviewId: ReviewId) = reviewId in reviews

    override fun plusAssign(review: Review) {
        reviews[review.id] = review
    }

    override fun get(reviewId: ReviewId): Review {
        if (reviewId !in this) throw ReviewDoesNotExists(reviewId)
        return reviews[reviewId]!!
    }

    override fun get(username: Username, movieId: MovieId): Review {
        return reviews.values.firstOrNull { review -> review.reviewer == username && review.movieId == movieId }
            ?: throw ReviewDoesNotExists(
                username = username,
                movieId = movieId
            )
    }

    override fun get(username: Username): List<Review> =
        reviews.values.filter { review -> review.reviewer == username }

    override fun getMovieReviews(movieId: MovieId): List<Review> =
        reviews.values.filter { review -> review.movieId == movieId }

    override fun all(): List<Review> = reviews.values.toList()
}