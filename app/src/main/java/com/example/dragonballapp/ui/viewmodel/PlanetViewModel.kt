package com.example.dragonballapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dragonballapp.model.planet.PlanetResult
import com.example.dragonballapp.repository.PlanetRepository
import com.example.dragonballapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetViewModel @Inject constructor(
    private val repo: PlanetRepository
): ViewModel() {

    private val _state = MutableStateFlow<Result<PlanetResult>>(Result.Loading)
    val state: StateFlow<Result<PlanetResult>>
        get() = _state

    //Get Characters
    init {
        viewModelScope.launch {
            _state.value = repo.getPlanets()
        }
    }
}