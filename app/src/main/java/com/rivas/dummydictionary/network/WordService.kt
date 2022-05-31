package com.rivas.dummydictionary.network

import com.rivas.dummydictionary.network.dto.LoginRequest
import com.rivas.dummydictionary.network.dto.LoginResponse
import com.rivas.dummydictionary.network.dto.WordsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WordService {
    @GET("/words")
    suspend fun getAllWord(): WordsResponse

    @POST("/login")
    suspend fun login(@Body credentials: LoginRequest): LoginResponse
}