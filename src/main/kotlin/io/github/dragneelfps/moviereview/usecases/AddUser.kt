package io.github.dragneelfps.moviereview.usecases

import io.github.dragneelfps.moviereview.errors.UserAlreadyExists
import io.github.dragneelfps.moviereview.models.Username

fun interface AddUser {
    @Throws(UserAlreadyExists::class)
    fun add(username: Username)
}