package com.example.characterapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.characterapp.model.character.CharacterResult
import com.example.characterapp.model.character.Transformation
import com.example.characterapp.utils.Result
import com.example.characterapp.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val transformationStates = mutableMapOf<Int, MutableStateFlow<Result<List<Transformation>>>>()

    fun transformations(id: Int): StateFlow<Result<List<Transformation>>> {
        //Get or add transformation list
        return transformationStates.getOrPut(id) {
            MutableStateFlow<Result<List<Transformation>>>(Result.Loading).also { stateFlow ->
                //Get the list of transformations from repo
                viewModelScope.launch {
                    repo.getTransformationsById(id).collect {
                        //Set value into stateFlow
                        stateFlow.value = it
                    }
                }
            }
        }
    }
}
