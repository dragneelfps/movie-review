package io.github.dragneelfps.moviereview.models

typealias ReviewId = Int

data class Review(
    val id: ReviewId,
    val movieId: MovieId,
    val reviewer: Username,
    val content: String,
    val score: Int
)


