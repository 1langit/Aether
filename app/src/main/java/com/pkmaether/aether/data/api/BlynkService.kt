package com.pkmaether.aether.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface BlynkService {

    @GET("get")
    suspend fun getNO2(
        @Query("token") token: String,
        @Query("pin") pin: String
    ): String
}