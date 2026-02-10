package com.example.characterapp.model

import com.google.gson.annotations.SerializedName

data class CharacterResult(
    @SerializedName("items")
    val characters: List<Character>,
    val links: Links,
    val meta: Meta
)