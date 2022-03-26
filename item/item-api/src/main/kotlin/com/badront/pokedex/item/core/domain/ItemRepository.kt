package com.badront.pokedex.item.core.domain

import com.badront.pokedex.core.model.Either
import com.badront.pokedex.item.core.domain.exception.ItemLoadingException
import com.badront.pokedex.item.core.domain.model.Item
import com.badront.pokedex.item.core.domain.model.ItemId

interface ItemRepository {
    suspend fun loadItem(id: ItemId): Either<Item, ItemLoadingException>
}