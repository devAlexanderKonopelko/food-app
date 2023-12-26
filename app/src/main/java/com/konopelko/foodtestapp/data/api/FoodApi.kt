package com.konopelko.foodtestapp.data.api

import com.konopelko.foodtestapp.data.api.model.getfood.Food
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {

    @GET("dev/search")
    suspend fun getFoodList(
        @Query("kv") enteredSearchInput: String
    ): Response<List<Food>>
}