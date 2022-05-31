package com.rivas.dummydictionary.repository

import androidx.lifecycle.LiveData
import com.rivas.dummydictionary.data.model.DummyDictionaryDatabase
import com.rivas.dummydictionary.data.model.Word
import com.rivas.dummydictionary.network.ApiResponse
import com.rivas.dummydictionary.network.WordService
import retrofit2.HttpException
import java.io.IOException

class DictionaryRepository(
    database: DummyDictionaryDatabase,
    private val api: WordService
) {
    private val wordDoa = database.wordDao()

    suspend fun getAllWords(): ApiResponse<LiveData<List<Word>>> { // WHY
        // Try to get words for api
        return try {
            val response = api.getAllWord()
            // Use database as cache
            if(response.count > 0){
                wordDoa.insertWords(response.words) // TODO: cruzar los dedos
            }
            ApiResponse.Success(data = wordDoa.getAllWords())
        } catch (e: HttpException){
            // handles exception with the request
            ApiResponse.Error(exception = e)
        } catch(e: IOException) {
            // handles no internet exception
            ApiResponse.Error(exception = e)
        }
    }

    suspend fun addWord(word: Word) {
        wordDoa.addWord(word)
    }
}