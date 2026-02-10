package com.example.characterapp.repository

import com.example.characterapp.api.AppApi
import com.example.characterapp.model.planet.PlanetResult
import javax.inject.Inject

class PlanetRepository @Inject constructor(
    private val appApi: AppApi
){
    suspend fun getPlanets(): PlanetResult {
        return appApi.getPlanets();
    }
}