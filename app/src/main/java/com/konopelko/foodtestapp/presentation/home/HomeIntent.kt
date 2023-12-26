package com.konopelko.foodtestapp.presentation.home

sealed interface HomeIntent {

    data class SearchInputChanged(val searchInput: String): HomeIntent
    data class FoodNameClicked(val name: String): HomeIntent
}