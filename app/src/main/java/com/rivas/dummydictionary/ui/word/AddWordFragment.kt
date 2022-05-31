package com.rivas.dummydictionary.ui.word

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rivas.dummydictionary.DummyDictionaryApplication
import com.rivas.dummydictionary.R
import com.rivas.dummydictionary.databinding.FragmentAddWordBinding
import com.rivas.dummydictionary.data.model.Word
import com.rivas.dummydictionary.ui.ViewModelFactory

class AddWordFragment : Fragment() {
    private val viewModelFactory by lazy {
        val application = requireActivity().application as DummyDictionaryApplication
        ViewModelFactory(application.getDictionaryRepository())
    }
    private val viewModel: WordViewModel by viewModels() {
        viewModelFactory
    }
    private lateinit var binding: FragmentAddWordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_word, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addWordBtn.setOnClickListener {
            val navController = findNavController()

            val tmpWord = Word(binding.newWord.text.toString(), binding.newDefinition.text.toString(), false)
            viewModel.addWord(tmpWord)

            binding.newWord.text.clear()
            binding.newDefinition.text.clear()

            navController.navigate(R.id.action_addWordFragment_to_wordListFragment)
            // Toast.makeText(context, "New word created", Toast.LENGTH_SHORT).show()
        }
    }
}