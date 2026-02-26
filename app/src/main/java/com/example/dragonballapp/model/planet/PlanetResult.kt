package com.example.dragonballapp.model.planet

import com.example.dragonballapp.model.character.Links
import com.example.dragonballapp.model.character.Meta
import com.google.gson.annotations.SerializedName

data class PlanetResult(
    @SerializedName("items")
    val planets: List<Planet>,
    val links: Links,
    val meta: Meta
)