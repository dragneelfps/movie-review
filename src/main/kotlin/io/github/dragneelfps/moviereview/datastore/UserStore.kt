package io.github.dragneelfps.moviereview.datastore

import io.github.dragneelfps.moviereview.errors.UserDoesNotExists
import io.github.dragneelfps.moviereview.models.User
import io.github.dragneelfps.moviereview.models.UserType
import io.github.dragneelfps.moviereview.models.Username

interface UserStore {
    operator fun contains(username: Username): Boolean

    operator fun plusAssign(user: User)

    @Throws(UserDoesNotExists::class)
    operator fun get(username: Username): User

    fun all(): List<User>

    fun updateUserType(username: Username, userType: UserType)
}

object InMemoryUserStore : UserStore {
    private val users = mutableMapOf<Username, User>()

    override operator fun contains(username: Username) = username in users

    override operator fun plusAssign(user: User) {
        users[user.username] = user
    }

    override operator fun get(username: Username): User {
        if (username !in users) throw UserDoesNotExists(username)
        return users[username]!!
    }

    override fun all(): List<User> = users.values.toList()

    override fun updateUserType(username: Username, userType: UserType) {
        val user = get(username)
        users[username] = user.copy(type = userType)
    }
}