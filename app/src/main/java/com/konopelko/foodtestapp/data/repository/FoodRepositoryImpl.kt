package com.konopelko.foodtestapp.data.repository

import com.konopelko.foodtestapp.data.api.FoodApi
import com.konopelko.foodtestapp.data.api.model.getfood.Food
import com.konopelko.foodtestapp.data.utils.Result
import com.konopelko.foodtestapp.data.utils.apiCall
import com.konopelko.foodtestapp.data.utils.catchErrors
import com.konopelko.foodtestapp.domain.repository.FoodRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val api: FoodApi
) : FoodRepository {

    override suspend fun getFoodList(searchInput: String): Result<List<Food>> =
        apiCall(Dispatchers.IO) {
            api.getFoodList(searchInput)
                .catchErrors()
        }
}