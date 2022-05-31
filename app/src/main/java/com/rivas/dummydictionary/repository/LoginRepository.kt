package com.rivas.dummydictionary.repository

import android.util.Log
import com.rivas.dummydictionary.network.ApiResponse
import com.rivas.dummydictionary.network.WordService
import com.rivas.dummydictionary.network.dto.LoginRequest
import retrofit2.HttpException
import java.io.IOException

class LoginRepository(private val api: WordService) {

    suspend fun login(username: String, password: String): ApiResponse<String> {
        try {

            val response = api.login(LoginRequest(username, password))
            // Log.d("LOGIN_TOKEN", "Mi token ${response.token}")
            return ApiResponse.Success(response.token)

        } catch (e: HttpException) {
            Log.d("LOGIN_ERROR", "info ${e.response()?.errorBody().toString()}")
            if (e.code() == 400) {
                // TODO: esta babosada hay que convertirla a obj kotlin
                    // e.response()?.body().toString()
                return ApiResponse.ErrorWithMessage("Invalid credentials")
            }
            return ApiResponse.Error(e)
        } catch (e: IOException) {
            return ApiResponse.Error(e)
        }
    }
}