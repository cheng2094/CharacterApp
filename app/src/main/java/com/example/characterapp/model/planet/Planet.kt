package com.example.characterapp.model.planet

data class Planet(
    val deletedAt: Any,
    val description: String,
    val id: Int,
    val image: String,
    val isDestroyed: Boolean,
    val name: String
)