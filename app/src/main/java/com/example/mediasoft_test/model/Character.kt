package com.example.mediasoft_test.model

import com.example.mediasoft_test.model.room.CharacterRoomEntity
import com.google.gson.annotations.SerializedName

data class Character(
    val id: Int,
    val name: String,
    val gender: String,
    val species: String,
    val status: String,
    val type: String,
    @SerializedName("image") val img: String,
    var selected: Boolean
) {
    fun toCharacterRoomEntity(): CharacterRoomEntity {
        return CharacterRoomEntity(
            character_id = id,
            name = name,
            species = species,
            gender = gender,
            status = status,
            type = type,
            img = img,
            isSelected = false,
        )
    }
}
