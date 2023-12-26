package com.konopelko.foodtestapp.presentation.home

import com.konopelko.foodtestapp.domain.repository.FoodRepository
import com.konopelko.foodtestapp.domain.usecase.GetFoodUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @Provides
    fun provideInitialHomeUiState(): HomeUiState = HomeUiState()

    @Provides
    fun provideGetFoodUseCase(foodRepository: FoodRepository): GetFoodUseCase =
        GetFoodUseCase(foodRepository::getFoodList)
}