package com.badront.pokedex.item.core.data.remote

import com.badront.pokedex.item.core.data.remote.model.ItemDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ItemApi {
    @GET("item/{id}")
    suspend fun getItemById(@Path("id") id: Int): ItemDto
}