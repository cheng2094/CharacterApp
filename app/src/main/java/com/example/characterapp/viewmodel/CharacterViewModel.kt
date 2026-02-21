package com.example.characterapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.characterapp.model.character.CharacterResult
import com.example.characterapp.model.character.TransformationUiState
import com.example.characterapp.utils.Result
import com.example.characterapp.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repo: CharacterRepository
): ViewModel() {

    //---------------------------CHARACTER-------------------------
    private val _state = MutableStateFlow<Result<CharacterResult>>(Result.Loading)
    val state: StateFlow<Result<CharacterResult>>
        get() = _state

    //Get Characters
    init {
        viewModelScope.launch {
            _state.value = repo.getCharacters()
        }
    }

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
