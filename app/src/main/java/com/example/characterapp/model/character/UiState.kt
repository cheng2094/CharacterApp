package com.example.characterapp.model.character

//data class CharacterUiState(
//    val isLoading: Boolean = false,
//    val errorMessage: String? = null,
//    val data: List<Character> = emptyList()
//)

data class TransformationUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: List<Transformation> = emptyList()
)