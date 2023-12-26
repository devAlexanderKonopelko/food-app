package com.konopelko.foodtestapp.domain.repository

import com.konopelko.foodtestapp.data.api.model.getfood.Food
import com.konopelko.foodtestapp.data.utils.Result

interface FoodRepository {

    suspend fun getFoodList(searchInput: String): Result<List<Food>>
}