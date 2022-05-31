package com.rivas.dummydictionary.ui.word

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rivas.dummydictionary.DummyDictionaryApplication
import com.rivas.dummydictionary.R
import com.rivas.dummydictionary.ui.ViewModelFactory
import com.rivas.dummydictionary.databinding.FragmentWordListBinding

class WordListFragment : Fragment() {
    // Optimizacion -> Llamara el repositorio hasta que lo necesite
    private val viewModelFactory by lazy {
        val application = requireActivity().application as DummyDictionaryApplication
        ViewModelFactory(application.getDictionaryRepository())
    }
    private val viewModel: WordViewModel by viewModels() {
        viewModelFactory
    }
    private lateinit var binding: FragmentWordListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_word_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wordListRecyclerView = binding.wordListRecyclerView
        val wordAdapter = WordAdapter()

        // Se indica que adaptador usara el RecycleView
        wordListRecyclerView.apply {
            adapter = wordAdapter
        }

        viewModel.getAllWords()

        viewModel.status.observe(viewLifecycleOwner) { status ->
            when (status) {
                is WordUiState.Error -> Log.d(
                    "Word List Status",
                    "Error",
                    status.exception
                )
                WordUiState.Loading -> Log.d("Word List Status", "Loading")
                is WordUiState.Success -> handleSuccess(status, wordAdapter)
            }
        }


        binding.actionAddWord.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_wordListFragment_to_addWordFragment)
        }
    }

    private fun handleSuccess(status: WordUiState.Success, wordAdapter: WordAdapter) {
        status.word.observe(viewLifecycleOwner) { data ->
            wordAdapter.setData(data)
        }
    }
}