package com.badront.pokedex.item.core.data.remote.mapper

import com.badront.pokedex.item.core.data.remote.model.ItemDto
import com.badront.pokedex.item.core.domain.model.Item
import javax.inject.Inject

internal class ItemDtoMapper @Inject constructor(
    private val imageDtoMapper: ItemImageDtoMapper
) {
    fun map(dto: ItemDto): Item {
        return Item(
            id = dto.id,
            name = dto.name,
            image = imageDtoMapper.map(dto.name)
        )
    }
}