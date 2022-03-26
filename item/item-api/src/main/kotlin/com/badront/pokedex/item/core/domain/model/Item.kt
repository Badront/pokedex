package com.badront.pokedex.item.core.domain.model

class Item(
    val id: ItemId,
    val name: String,
    val image: String?
)

typealias ItemId = Int