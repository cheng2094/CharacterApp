package com.example.dragonballapp.repository

import com.example.dragonballapp.api.AppApi
import com.example.dragonballapp.model.planet.PlanetResult
import com.example.dragonballapp.utils.Result
import timber.log.Timber
import javax.inject.Inject

class PlanetRepository @Inject constructor(
    private val appApi: AppApi
){
    suspend fun getPlanets(): Result<PlanetResult> {
        return try {
            val result = appApi.getPlanets()
            Timber.tag("CALL PLANETS API").d("$result")
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}