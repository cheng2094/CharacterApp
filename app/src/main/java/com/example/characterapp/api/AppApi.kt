package com.example.characterapp.api

import com.example.characterapp.model.character.CharacterResult
import com.example.characterapp.model.character.TransformationResult
import com.example.characterapp.model.planet.PlanetResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppApi {
    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int = 100
    ): CharacterResult

    @GET("characters/{id}")
    suspend fun getTransformationById(
        @Path("id") id: Int
    ): TransformationResult

    @GET("planets")
    suspend fun getPlanets(
        @Query("limit") limit: Int = 50
    ): PlanetResult
}