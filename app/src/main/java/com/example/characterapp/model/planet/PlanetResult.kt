package com.example.characterapp.model.planet

import com.example.characterapp.model.character.Links
import com.example.characterapp.model.character.Meta
import com.google.gson.annotations.SerializedName

data class PlanetResult(
    @SerializedName("items")
    val planets: List<Planet>,
    val links: Links,
    val meta: Meta
)