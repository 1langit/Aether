package com.pkmaether.aether.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BLYNK_URL = "https://blynk.cloud/external/api/"
    private val okHttpClient = buildOkHttpClient()

    fun getIotApiInstance(): BlynkService {
        val builder = Retrofit.Builder()
            .baseUrl(BLYNK_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return builder.create(BlynkService::class.java)
    }

    private fun buildOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }
}