package com.example.characterapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.characterapp.model.character.CharacterResult
import com.example.characterapp.model.character.TransformationUiState
import com.example.characterapp.utils.Result
import com.example.characterapp.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repo: CharacterRepository
): ViewModel() {


    //---------------------------SEARCH INPUT-------------------------
    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> get() = _inputText.asStateFlow()

    fun setInputText(searchText: String) {
        _inputText.value = searchText
    }

    //---------------------------CHARACTER-------------------------
    //Get Characters
    val state: StateFlow<Result<CharacterResult>> =
        combine(
            repo.getCharacters(),
            _inputText.debounce(250)
        ) { result, searchText ->

            // if result still Loading or Error → re emmit
            if (result !is Result.Success) {
                return@combine result
            }

            // Original list
            val characters = result.data.characters

            // Filtering by name
            val filteredList = characters.filter { character ->
                character.name.contains(searchText, ignoreCase = true)
            }

            Result.Success(
                result.data.copy(
                    characters = filteredList
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    //------------------------TRANSFORMATION--------------------------
    private val transformationStates = mutableMapOf<Int, MutableStateFlow<TransformationUiState>>()

    fun transformations(id: Int): StateFlow<TransformationUiState> {
        //Get or add transformation list
        return transformationStates.getOrPut(id) {
            MutableStateFlow(TransformationUiState()).also { uiState ->
                //Get the list of transformations from repo
                viewModelScope.launch {
                    repo.getTransformationsById(id).collect { result ->
                        uiState.update { current ->
                            when (result) {
                                is Result.Loading -> {
                                    current.copy(
                                        isLoading = true,
                                        errorMessage = null,
                                        data = emptyList()
                                    )
                                }

                                is Result.Error -> {
                                    current.copy(
                                        isLoading = false,
                                        errorMessage = result.throwable.message
                                    )
                                }

                                is Result.Success -> {
                                    current.copy(
                                        isLoading = false,
                                        errorMessage = null,
                                        data = result.data
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
