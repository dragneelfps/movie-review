package io.github.dragneelfps.moviereview.usecases

import io.github.dragneelfps.moviereview.models.MovieId
import io.github.dragneelfps.moviereview.models.Review
import io.github.dragneelfps.moviereview.models.Username

fun interface ReviewMovie {
    fun review(reviewer: Username, movieId: MovieId, score: Int, content: String): Review
}