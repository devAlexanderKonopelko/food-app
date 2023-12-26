package com.konopelko.foodtestapp.presentation.home

sealed interface HomeFragmentEvent {


    data class ShowFoodName(val name: String): HomeFragmentEvent
    data class ShowLoadingError(val errorMessage: String): HomeFragmentEvent
}