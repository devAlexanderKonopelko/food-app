package com.konopelko.foodtestapp.data.di

import com.konopelko.foodtestapp.BuildConfig
import com.konopelko.foodtestapp.data.api.FoodApi
import com.konopelko.foodtestapp.data.http.createHttpClient
import com.konopelko.foodtestapp.data.http.createRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient = createHttpClient()

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit = createRetrofit(
        httpClient = httpClient,
        hostUrl = BuildConfig.FOOD_API_HOST
    )

    @Provides
    @Singleton
    fun provideFoodApi(retrofit: Retrofit): FoodApi = retrofit.create(FoodApi::class.java)
}