package com.example.mediasoft_test.model.data

import com.example.mediasoft_test.model.Character

data class Result(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) {
    fun toCharacter(): Character {
        return Character(
            id = id,
            name = name,
            species = species,
            gender = gender,
            status = status,
            type = type,
            img = image
        )
    }
}