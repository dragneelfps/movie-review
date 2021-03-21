package io.github.dragneelfps.moviereview.utils

import java.util.concurrent.ThreadLocalRandom

fun generateId() = ThreadLocalRandom.current().nextInt(0, Int.MAX_VALUE)