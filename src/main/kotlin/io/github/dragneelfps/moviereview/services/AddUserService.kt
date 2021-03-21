package io.github.dragneelfps.moviereview.services

import io.github.dragneelfps.moviereview.datastore.UserStore
import io.github.dragneelfps.moviereview.errors.UserAlreadyExists
import io.github.dragneelfps.moviereview.models.User
import io.github.dragneelfps.moviereview.models.UserType
import io.github.dragneelfps.moviereview.models.Username
import io.github.dragneelfps.moviereview.usecases.AddUser

class AddUserService(private val userStore: UserStore) : AddUser {
    override fun add(username: Username) {
        if (username in userStore) throw UserAlreadyExists(username)
        userStore += User(
            username = username,
            type = UserType.VIEWER
        )
    }
}