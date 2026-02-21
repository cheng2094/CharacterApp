package com.example.characterapp.repository

import com.example.characterapp.api.AppApi
import com.example.characterapp.model.planet.PlanetResult
import com.example.characterapp.utils.Result
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