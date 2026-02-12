package com.example.characterapp.repository

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
    suspend fun getCharacters(): CharacterResult {
        return appApi.getCharacters()
    }

    // ---------- TRANSFORMATIONS ----------
    fun getTransformationsById(id: Int): Flow<List<Transformation>> = flow {

        // 1. Emit from cache if exist
        transformationCache[id]?.let {
            emit(it)
        }

        // 2. Call API
        val response = appApi.getTransformationById(id)

        // 3. Save in cache
        transformationCache[id] = response.transformations

        // 4. Emit update
        emit(response.transformations)

    }.catch { _ ->
        // if error, emit cache only
        emit(transformationCache[id] ?: emptyList())
    }
}