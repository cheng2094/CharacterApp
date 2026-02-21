package com.example.characterapp.repository

import com.example.characterapp.utils.Result
import com.example.characterapp.api.AppApi
import com.example.characterapp.model.character.CharacterResult
import com.example.characterapp.model.character.Transformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val appApi: AppApi
){

    // Cache in repository
    private val transformationCache = mutableMapOf<Int, List<Transformation>>()
    private val characterCache = mutableListOf<CharacterResult>()

    // ---------- CHARACTERS ----------
    fun getCharacters(): Flow<Result<CharacterResult>> = flow {

        emit(Result.Loading)

        // Emit cache if available
        if (characterCache.isNotEmpty()) {
            emit(Result.Success(characterCache.first()))
        }

        // API Call
        val result = appApi.getCharacters()
        val response = result.body()

        Timber.tag("CALL CHARACTERS API").d("$response")

        if (result.isSuccessful && response != null) {

            // save in cache
            characterCache.clear()
            characterCache.add(response)

            emit(Result.Success(response))

        } else {
            val throwable = Throwable(
                "There was an issue fetching characters: ${result.errorBody().toString()}"
            )
            Timber.e(throwable)
            throw throwable
        }

    }.catch { e ->
        // if error but cache exists → emit cache
        if (characterCache.isNotEmpty()) {
            emit(Result.Success(characterCache.first()))
        } else {
            emit(Result.Error(e))
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
        Timber.tag("CALL TRANSFORMATION API").d("$response")

        // Save in cache
        transformationCache[id] = response.transformations

        // Emit update
        emit(Result.Success(response.transformations))

    }.catch { e ->
        // if error, emit cache only
        emit(
            if (transformationCache[id] != null)
                Result.Success(transformationCache[id]!!)
            else Result.Error( e)
        )
    }
}