package com.konopelko.foodtestapp.presentation.home

import com.konopelko.foodtestapp.data.api.model.getfood.Food

data class HomeUiState(
    val food: List<Food> = listOf(),
    val isLoadingFood: Boolean = false,
    val searchInput: String = ""
) {

    sealed interface PartialState {
        data object LoadingFood: PartialState

        data class FoodLoaded(val food: List<Food>) : PartialState
        data class SearchInputChange(val searchInput: String) : PartialState
    }
}
