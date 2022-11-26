package com.meow.meowsic.models.playlistmodel

import com.meow.meowsic.models.playlistmodel.Item

data class Tracks(
    val href: String,
    val items: List<Item>,
    val limit: Int,
    val next: Any,
    val offset: Int,
    val previous: Any,
    val total: Int
)