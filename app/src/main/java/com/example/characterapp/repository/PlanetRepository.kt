package com.example.characterapp.repository

import android.util.Log
import com.example.characterapp.api.AppApi
import com.example.characterapp.model.planet.PlanetResult
import com.example.characterapp.utils.Result
import javax.inject.Inject

class PlanetRepository @Inject constructor(
    private val appApi: AppApi
){
    suspend fun getPlanets(): Result<PlanetResult> {
        return try {
            val result = appApi.getPlanets()
            Log.d("CALL PLANETS API", "$result")
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error("Error loading planets", e)
        }
    }
}