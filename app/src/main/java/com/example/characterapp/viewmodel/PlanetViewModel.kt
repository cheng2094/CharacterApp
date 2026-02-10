package com.example.characterapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.characterapp.model.character.Links
import com.example.characterapp.model.character.Meta
import com.example.characterapp.model.planet.PlanetResult
import com.example.characterapp.repository.PlanetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetViewModel @Inject constructor(
    private val repo: PlanetRepository
): ViewModel() {
    private val _state = MutableStateFlow(
        PlanetResult(
            meta = Meta(
                currentPage = 0,
                itemCount = 0,
                itemsPerPage = 0,
                totalItems = 0,
                totalPages = 0
            ),
            planets = emptyList(),
            links = Links(
                first = "0",
                last = "0",
                next = "0",
                previous = "0"
            )
        )
    )
    val state: StateFlow<PlanetResult>
        get() = _state

    init {
        viewModelScope.launch {
            _state.value = repo.getPlanets()
        }

    }
}