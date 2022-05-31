package com.rivas.dummydictionary

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.rivas.dummydictionary.data.model.DummyDictionaryDatabase
import com.rivas.dummydictionary.network.RetrofitInstance
import com.rivas.dummydictionary.repository.DictionaryRepository
import com.rivas.dummydictionary.repository.LoginRepository

class DummyDictionaryApplication : Application() {
    // Fuente instancias de repositorio y BD
    // Encargado de almacenar el token
    private val prefs: SharedPreferences by lazy {
        getSharedPreferences("DummyDictionary", Context.MODE_PRIVATE)
    }

    val dataBase by lazy {
        DummyDictionaryDatabase.getInstance(this)
    }

    private fun getAPIService() = with(RetrofitInstance) {
        setToken(getToken())
        getWordServices()
    }

    fun getDictionaryRepository() =
        DictionaryRepository(dataBase, getAPIService())

    fun getLoginRepository() =
        LoginRepository(getAPIService())

    private fun getToken(): String = prefs.getString(USER_TOKEN, "")!!

    fun isUserLogin() = getToken() != ""

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    companion object {
        const val USER_TOKEN = "user_token"
    }
}