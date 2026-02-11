package com.example.characterapp.model.character

data class TransformationResult(
    val affiliation: String,
    val deletedAt: Any,
    val description: String,
    val gender: String,
    val id: Int,
    val image: String,
    val ki: String,
    val maxKi: String,
    val name: String,
    val originPlanet: OriginPlanet,
    val race: String,
    val transformations: List<Transformation>
)