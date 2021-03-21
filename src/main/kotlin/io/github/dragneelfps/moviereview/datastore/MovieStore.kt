package io.github.dragneelfps.moviereview.datastore

import io.github.dragneelfps.moviereview.errors.MovieDoesNotExists
import io.github.dragneelfps.moviereview.models.Movie
import io.github.dragneelfps.moviereview.models.MovieId


interface MovieStore {
    operator fun contains(id: MovieId): Boolean

    operator fun plusAssign(movie: Movie)

    @Throws(MovieDoesNotExists::class)
    operator fun get(id: MovieId): Movie

    fun all(): List<Movie>
}

object InMemoryMovieStore : MovieStore {

    private val movies = mutableMapOf<MovieId, Movie>()

    override operator fun contains(id: MovieId): Boolean = id in movies

    override operator fun plusAssign(movie: Movie) {
        movies[movie.id] = movie
    }

    override operator fun get(id: MovieId): Movie {
        if (id !in this) throw MovieDoesNotExists(id)
        return movies[id]!!
    }

    override fun all(): List<Movie> = movies.values.toList()
}