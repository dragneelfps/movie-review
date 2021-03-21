package io.github.dragneelfps.moviereview.services

import io.github.dragneelfps.moviereview.datastore.MovieStore
import io.github.dragneelfps.moviereview.datastore.ReviewStore
import io.github.dragneelfps.moviereview.datastore.UserStore
import io.github.dragneelfps.moviereview.models.Movie
import io.github.dragneelfps.moviereview.models.UserType
import io.github.dragneelfps.moviereview.usecases.ListMovies
import io.github.dragneelfps.moviereview.usecases.MovieFilter
import io.github.dragneelfps.moviereview.usecases.UserFilter
import java.time.Year

class ListMoviesService(
    private val movieStore: MovieStore,
    private val reviewStore: ReviewStore,
    private val userStore: UserStore,
    private val scoreCalculator: ScoreCalculator
) : ListMovies {
    override fun list(limit: Int, movieFilter: MovieFilter, reviewerFilter: UserFilter): List<Movie> {
        return movieStore.all()
//            .asSequence()
            .filter(movieFilter)
            .map { movie ->
                movie to scoreCalculator.calculate(movie.id, reviewerFilter)
            }
            .sortedByDescending { (_, score) -> score }
            .map { (movie, _) -> movie }
            .toList()
            .take(limit)
    }
}


fun MovieYearFilter(year: Year): MovieFilter = { movie ->
    movie.releaseDate == year
}

fun MovieGenreFilter(genre: List<String>): MovieFilter = { movie ->
    movie.genre.containsAll(genre)
}

fun ReviewerTypeFilter(reviewerType: UserType): UserFilter = { user ->
    user.type == reviewerType
}

