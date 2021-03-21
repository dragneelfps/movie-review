package io.github.dragneelfps.moviereview.errors

import io.github.dragneelfps.moviereview.models.MovieId
import io.github.dragneelfps.moviereview.models.ReviewId
import io.github.dragneelfps.moviereview.models.Username

data class UserAlreadyExists(val username: Username) : RuntimeException()
data class UserDoesNotExists(val username: Username) : RuntimeException()

data class MovieDoesNotExists(val movieId: MovieId) : RuntimeException()

data class ReviewDoesNotExists(
    val reviewId: ReviewId? = null,
    val username: Username? = null,
    val movieId: MovieId? = null
) : RuntimeException()

data class CannotReviewMovie(val reason: String) : RuntimeException()

data class InvalidReviewScore(val reason: String) : RuntimeException()