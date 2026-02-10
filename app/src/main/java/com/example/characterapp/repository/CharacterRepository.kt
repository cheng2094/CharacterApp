package com.example.characterapp.repository

import com.example.characterapp.api.CharacterApi
import com.example.characterapp.model.character.CharacterResult
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterApi: CharacterApi
){
    suspend fun getCharacter(): CharacterResult {
        return characterApi.getCharacter();
    }
}