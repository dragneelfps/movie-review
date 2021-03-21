package io.github.dragneelfps.moviereview

import io.github.dragneelfps.moviereview.datastore.InMemoryMovieStore
import io.github.dragneelfps.moviereview.datastore.InMemoryReviewStore
import io.github.dragneelfps.moviereview.datastore.InMemoryUserStore
import io.github.dragneelfps.moviereview.errors.CannotReviewMovie
import io.github.dragneelfps.moviereview.models.UserType
import io.github.dragneelfps.moviereview.services.*
import java.time.Year
import kotlin.properties.Delegates
import kotlin.test.assertFailsWith

private val addMovieService = AddMovieService(movieStore = InMemoryMovieStore)
private val addUserService = AddUserService(userStore = InMemoryUserStore)
private val reviewMovieService = ReviewMovieService(
    userStore = InMemoryUserStore,
    movieStore = InMemoryMovieStore,
    reviewStore = InMemoryReviewStore
)
private val listMoviesService = ListMoviesService(
    userStore = InMemoryUserStore,
    movieStore = InMemoryMovieStore,
    reviewStore = InMemoryReviewStore,
    scoreCalculator = ScoreCalculator(InMemoryReviewStore, InMemoryUserStore)
)
private val reviewScoreService = ReviewScoreService(
    movieStore = InMemoryMovieStore,
    scoreCalculator = ScoreCalculator(InMemoryReviewStore, InMemoryUserStore)
)


fun main() {
    run { `#1 - Onboard 10 movies onto your platform in 3 different years`() }
    run { `#2 - Add users to the system`() }
    run { `#3 - Add Reviews`() }
    run { `#4 - List top 1 movie by review score in “2006” year`() }
    run { `#5 - List top 1 movie by review score in “Drama” genre`() }
    run { `#6 - List top movie by average review score in “2006” year`() }
}

fun `#1 - Onboard 10 movies onto your platform in 3 different years`() {

    donMovieId = addMovieService.add(title = "Don", releaseDate = Year.of(2006), genre = listOf("Action", "Comedy"))
    tigerMovieId = addMovieService.add(title = "Tiger", releaseDate = Year.of(2008), genre = listOf("Drama"))
    padmaavatMovieId =
        addMovieService.add(title = "Padmaavat", releaseDate = Year.of(2006), genre = listOf("Comedy"))
    lunchBoxMovieId = addMovieService.add(title = "Lunchbox", releaseDate = Year.of(2022), genre = listOf("Drama"))
    guruMovieId = addMovieService.add(title = "Guru", releaseDate = Year.of(2006), genre = listOf("Drama"))
    metroMovieId = addMovieService.add(title = "Metro", releaseDate = Year.of(2006), genre = listOf("Romance"))


    println(InMemoryMovieStore.all())


}

fun `#2 - Add users to the system`() {
    addUserService.add(username = "SRK")
    addUserService.add(username = "Salman")
    addUserService.add(username = "Deepika")

    println(InMemoryUserStore.all())
}

fun `#3 - Add Reviews`() {

    println(InMemoryUserStore.all())

    reviewMovieService.review(reviewer = "SRK", donMovieId, 2, "")
    reviewMovieService.review(reviewer = "SRK", padmaavatMovieId, 8, "")
    reviewMovieService.review(reviewer = "Salman", donMovieId, 5, "")
    reviewMovieService.review(reviewer = "Deepika", donMovieId, 9, "")
    reviewMovieService.review(reviewer = "Deepika", guruMovieId, 6, "")

    assertFailsWith<CannotReviewMovie> {
        reviewMovieService.review(reviewer = "SRK", donMovieId, 10, "")
    }

    assertFailsWith<CannotReviewMovie> {
        reviewMovieService.review(reviewer = "Deepika", lunchBoxMovieId, 5, "")
    }

    reviewMovieService.review(reviewer = "SRK", tigerMovieId, 5, "")
    reviewMovieService.review(reviewer = "SRK", metroMovieId, 7, "")

    println(InMemoryUserStore.all())
    println(InMemoryMovieStore.all())
    println(InMemoryReviewStore.all())
}

fun `#4 - List top 1 movie by review score in “2006” year`() {

    println(
        listMoviesService.list(
            limit = 1,
            movieFilter = MovieYearFilter(Year.of(2006))
        )
    )


    println(
        listMoviesService.list(
            limit = 100,
            movieFilter = MovieYearFilter(Year.of(2006)),
            reviewerFilter = ReviewerTypeFilter(UserType.CRITIC)
        )
    )
}

fun `#5 - List top 1 movie by review score in “Drama” genre`() {
    println(
        listMoviesService.list(
            limit = 1,
            movieFilter = MovieGenreFilter(listOf("Drama"))
        )
    )
}

fun `#6 - List top movie by average review score in “2006” year`() {
    println(
        reviewScoreService.averageScore(
            MovieYearFilter(Year.of(2006))
        )
    )
}


private var donMovieId by Delegates.notNull<Int>()
private var padmaavatMovieId by Delegates.notNull<Int>()
private var guruMovieId by Delegates.notNull<Int>()
private var lunchBoxMovieId by Delegates.notNull<Int>()
private var tigerMovieId by Delegates.notNull<Int>()
private var metroMovieId by Delegates.notNull<Int>()


private fun run(block: () -> Unit) {
    println("==================START==================")
    block()
    println("===================END===================")
}