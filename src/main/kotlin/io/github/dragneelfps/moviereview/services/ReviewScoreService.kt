package io.github.dragneelfps.moviereview.services

import io.github.dragneelfps.moviereview.datastore.MovieStore
import io.github.dragneelfps.moviereview.usecases.MovieFilter

class ReviewScoreService(private val movieStore: MovieStore, private val scoreCalculator: ScoreCalculator) {

    fun averageScore(movieFilter: MovieFilter): Int? = movieStore.all()
        .filter(movieFilter)
        .takeIf { it.isNotEmpty() }
        ?.mapNotNull { movie -> scoreCalculator.calculate(movie.id) }
        ?.average()
        ?.toInt()
}