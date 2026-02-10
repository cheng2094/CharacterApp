package com.example.characterapp.api

import com.example.characterapp.model.character.CharacterResult
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {
    @GET("characters")
    suspend fun getCharacter(
        @Query("limit") limit: Int = 100
    ): CharacterResult
}