package io.github.dragneelfps.moviereview.usecases

import io.github.dragneelfps.moviereview.models.MovieId
import java.time.Year

fun interface AddMovie {
    fun add(title: String, releaseDate: Year, genre: List<String>): MovieId
}