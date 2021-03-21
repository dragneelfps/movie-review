package io.github.dragneelfps.moviereview.usecases

import io.github.dragneelfps.moviereview.models.Movie
import io.github.dragneelfps.moviereview.models.Review
import io.github.dragneelfps.moviereview.models.User

interface ListMovies {
    fun list(limit: Int, movieFilter: MovieFilter = { true }, reviewerFilter: UserFilter = { true }): List<Movie>
}


typealias MovieFilter = (Movie) -> Boolean
typealias ReviewFilter = (Review) -> Boolean
typealias UserFilter = (User) -> Boolean
