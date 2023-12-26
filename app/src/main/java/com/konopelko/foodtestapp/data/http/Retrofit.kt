package com.konopelko.foodtestapp.data.http

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal fun createRetrofit(
	httpClient: OkHttpClient,
	hostUrl: String
): Retrofit =
	Retrofit
		.Builder()
		.baseUrl(hostUrl)
		.addConverterFactory(GsonConverterFactory.create())
		.client(httpClient)
		.build()