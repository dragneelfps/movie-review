package io.github.dragneelfps.moviereview.models

typealias Username = String

data class User(
    val username: Username,
    val type: UserType,
)

enum class UserType {
    VIEWER,
    CRITIC,
    EXPERT,
    ADMIN
}


