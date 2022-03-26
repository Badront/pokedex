package com.badront.pokedex.item.core.domain.usecase

import com.badront.pokedex.core.model.Either
import com.badront.pokedex.item.core.domain.ItemRepository
import com.badront.pokedex.item.core.domain.exception.ItemLoadingException
import com.badront.pokedex.item.core.domain.model.Item
import com.badront.pokedex.item.core.domain.model.ItemId
import javax.inject.Inject

class LoadItem @Inject constructor(
    private val itemRepository: ItemRepository
) {
    suspend operator fun invoke(id: ItemId): Either<Item, ItemLoadingException> {
        return itemRepository.loadItem(id)
    }
}