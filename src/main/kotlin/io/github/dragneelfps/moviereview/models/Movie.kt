package io.github.dragneelfps.moviereview.models

import java.time.Year

typealias MovieId = Int

data class Movie(
    val id: MovieId,
    val title: String,
    val releaseDate: Year,
    val genre: List<String>
) {
    val released: Boolean
        get() = Year.now().isAfter(releaseDate)
}
