package com.example.characterapp.repository

import com.example.characterapp.api.AppApi
import com.example.characterapp.model.character.CharacterResult
import com.example.characterapp.model.character.TransformationResult
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val appApi: AppApi
){
    suspend fun getCharacters(): CharacterResult {
        return appApi.getCharacters();
    }

    suspend fun getTransformationById(id : Int): TransformationResult {
        return appApi.getTransformationById(id);
    }
}