package com.konopelko.foodtestapp.data.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

internal fun createHttpClient(): OkHttpClient =
    OkHttpClient
        .Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()