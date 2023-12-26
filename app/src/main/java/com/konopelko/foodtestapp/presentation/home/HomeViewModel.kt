package com.konopelko.foodtestapp.presentation.home

import com.konopelko.foodtestapp.domain.usecase.GetFoodUseCase
import com.konopelko.foodtestapp.presentation.common.BaseViewModel
import com.konopelko.foodtestapp.presentation.home.HomeFragmentEvent.ShowFoodName
import com.konopelko.foodtestapp.presentation.home.HomeFragmentEvent.ShowLoadingError
import com.konopelko.foodtestapp.presentation.home.HomeIntent.FoodNameClicked
import com.konopelko.foodtestapp.presentation.home.HomeIntent.SearchInputChanged
import com.konopelko.foodtestapp.presentation.home.HomeUiState.PartialState
import com.konopelko.foodtestapp.presentation.home.HomeUiState.PartialState.FoodLoaded
import com.konopelko.foodtestapp.presentation.home.HomeUiState.PartialState.LoadingFood
import com.konopelko.foodtestapp.presentation.home.HomeUiState.PartialState.SearchInputChange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val MIN_INPUT_LENGTH = 3

@HiltViewModel
class HomeViewModel @Inject constructor(
    initialState: HomeUiState,
    private val getFoodUseCase: GetFoodUseCase
) : BaseViewModel<HomeUiState, PartialState, HomeFragmentEvent, HomeIntent>(initialState = initialState) {

    override fun mapIntents(intent: HomeIntent): Flow<PartialState> = when (intent) {
        is SearchInputChanged -> onSearchInputChanged(intent.searchInput)
        is FoodNameClicked -> onFoodNameClicked(intent.name)
    }

    override fun reduceUiState(
        previousState: HomeUiState,
        partialState: PartialState
    ): HomeUiState = when (partialState) {
        LoadingFood -> previousState.copy(isLoadingFood = true)

        is SearchInputChange -> previousState.copy(searchInput = partialState.searchInput)
        is FoodLoaded -> previousState.copy(
            food = partialState.food,
            isLoadingFood = false
        )
    }

    private fun onSearchInputChanged(searchInput: String): Flow<PartialState> = flow {
        emit(SearchInputChange(searchInput = searchInput))

        if (searchInput.isInputValid() && uiState.value.isLoadingFood.not()) {
            emit(LoadingFood)
            loadFood(searchInput)
        } else {
            emit(FoodLoaded(food = emptyList()))
        }
    }

    private suspend fun FlowCollector<PartialState>.loadFood(searchInput: String) =
        getFoodUseCase(searchInput).onSuccess {
            emit(FoodLoaded(food = it))
        }.onError {
            emit(FoodLoaded(food = emptyList()))
            publishFragmentEvent(
                ShowLoadingError(
                    errorMessage = it.exception.message ?: it.exception.stackTraceToString()
                )
            )
        }

    private fun onFoodNameClicked(name: String): Flow<PartialState> = flow {
        publishFragmentEvent(ShowFoodName(name = name))
    }
}

private fun String.isInputValid(): Boolean = isNotEmpty() && length >= MIN_INPUT_LENGTH