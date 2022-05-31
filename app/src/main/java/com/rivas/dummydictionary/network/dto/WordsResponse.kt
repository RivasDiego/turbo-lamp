package com.rivas.dummydictionary.network.dto

import com.google.gson.annotations.SerializedName
import com.rivas.dummydictionary.data.model.Word

data class WordsResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("words")
    val words: List<Word>
)