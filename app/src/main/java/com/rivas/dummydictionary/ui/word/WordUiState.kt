package com.rivas.dummydictionary.ui.word

import androidx.lifecycle.LiveData
import com.rivas.dummydictionary.data.model.Word

sealed class WordUiState {
    object Loading: WordUiState()
    class Error(val exception:Exception):WordUiState()
    data class Success(val word: LiveData<List<Word>>): WordUiState()
}