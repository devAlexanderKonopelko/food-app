package com.konopelko.foodtestapp.domain.usecase

import com.konopelko.foodtestapp.data.api.model.getfood.Food
import com.konopelko.foodtestapp.data.utils.Result

private typealias SearchInput = String

fun interface GetFoodUseCase : suspend (SearchInput) -> Result<List<Food>>