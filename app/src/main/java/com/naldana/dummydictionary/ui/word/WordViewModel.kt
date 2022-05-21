package com.naldana.dummydictionary.ui.word

import androidx.lifecycle.ViewModel
import com.naldana.dummydictionary.repository.DictionaryRepository

class WordViewModel(private val repository: DictionaryRepository): ViewModel() {
    val words = repository.words
}