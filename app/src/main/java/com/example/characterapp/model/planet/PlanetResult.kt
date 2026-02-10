package com.example.characterapp.model.planet

import com.google.gson.annotations.SerializedName

data class PlanetResult(
    @SerializedName("items")
    val planets: List<Planet>,
    val links: Links,
    val meta: Meta
)