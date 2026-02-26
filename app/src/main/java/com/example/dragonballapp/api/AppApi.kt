package com.example.dragonballapp.api

import com.example.dragonballapp.model.character.CharacterResult
import com.example.dragonballapp.model.character.TransformationResult
import com.example.dragonballapp.model.planet.PlanetResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppApi {
    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int = 100
    ): Response<CharacterResult>

    @GET("characters/{id}")
    suspend fun getTransformationById(
        @Path("id") id: Int
    ): TransformationResult

    @GET("planets")
    suspend fun getPlanets(
        @Query("limit") limit: Int = 50
    ): PlanetResult
}