package com.example.characterapp.repository

import android.util.Log
import com.example.characterapp.utils.Result
import com.example.characterapp.api.AppApi
import com.example.characterapp.model.character.CharacterResult
import com.example.characterapp.model.character.Transformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val appApi: AppApi
){

    // Cache in repository
    private val transformationCache = mutableMapOf<Int, List<Transformation>>()

    // ---------- CHARACTERS ----------
    suspend fun getCharacters(): Result<CharacterResult> {
        return try {
            val result = appApi.getCharacters()
            Log.d("CALL CHARACTERS API", "$result")
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error("Error loading characters", e)
        }
    }

    // ---------- TRANSFORMATIONS ----------
    fun getTransformationsById(id: Int): Flow<Result<List<Transformation>>> = flow {

        emit(Result.Loading)

        // Emit from cache if exist
        transformationCache[id]?.let {
            emit(Result.Success(it))
        }

        // Call API
        val response = appApi.getTransformationById(id)
        Log.d("CALL TRANSFORMATION API", "$response")

        // Save in cache
        transformationCache[id] = response.transformations

        // Emit update
        emit(Result.Success(response.transformations))

    }.catch { e ->
        // if error, emit cache only
        emit(
            if (transformationCache[id] != null)
                Result.Success(transformationCache[id]!!)
            else Result.Error("Error loading transformations", e)
        )
    }
}