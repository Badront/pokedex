package com.badront.pokedex.item.core.data

import com.badront.pokedex.core.model.Either
import com.badront.pokedex.item.core.data.remote.ItemApi
import com.badront.pokedex.item.core.data.remote.mapper.ItemDtoMapper
import com.badront.pokedex.item.core.domain.ItemRepository
import com.badront.pokedex.item.core.domain.exception.ItemLoadingException
import com.badront.pokedex.item.core.domain.model.Item
import com.badront.pokedex.item.core.domain.model.ItemId
import javax.inject.Inject

internal class ItemRepositoryImpl @Inject constructor(
    private val itemApi: ItemApi,
    private val itemDtoMapper: ItemDtoMapper
) : ItemRepository {
    override suspend fun loadItem(id: ItemId): Either<Item, ItemLoadingException> {
        return runCatching {
            itemApi.getItemById(id)
        }.fold(
            onSuccess = { dto ->
                Either.success(itemDtoMapper.map(dto))
            },
            onFailure = { cause ->
                Either.failure(ItemLoadingException(cause))
            }
        )
    }
}