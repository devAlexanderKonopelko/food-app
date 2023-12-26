package com.konopelko.foodtestapp.data.di

import com.konopelko.foodtestapp.data.repository.FoodRepositoryImpl
import com.konopelko.foodtestapp.domain.repository.FoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface FoodModule {

    @Binds
    fun bindFoodRepository(repository: FoodRepositoryImpl): FoodRepository
}