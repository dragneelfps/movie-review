package io.github.dragneelfps.moviereview.services

import io.github.dragneelfps.moviereview.datastore.MovieStore
import io.github.dragneelfps.moviereview.models.Movie
import io.github.dragneelfps.moviereview.models.MovieId
import io.github.dragneelfps.moviereview.usecases.AddMovie
import io.github.dragneelfps.moviereview.utils.generateId
import java.time.Year

class AddMovieService(private val movieStore: MovieStore) : AddMovie {
    override fun add(title: String, releaseDate: Year, genre: List<String>): MovieId {
        val id = generateId()
        movieStore += Movie(
            id = id,
            title = title,
            releaseDate = releaseDate,
            genre = genre
        )
        return id
    }
}