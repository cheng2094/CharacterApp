package com.example.characterapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.characterapp.model.CharacterResult
import com.example.characterapp.model.Links
import com.example.characterapp.model.Meta
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
    private val _state = MutableStateFlow(
        CharacterResult(
            meta = Meta(
                currentPage = 0,
                itemCount = 0,
                itemsPerPage = 0,
                totalItems = 0,
                totalPages = 0
            ),
            characters = emptyList(),
            links = Links(
                first = "0",
                last = "0",
                next = "0",
                previous = "0"
            )
        )
    )
    val state: StateFlow<CharacterResult>
        get() = _state

    init {
        viewModelScope.launch {
            _state.value = repo.getCharacter()
        }

    }
}