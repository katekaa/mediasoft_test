package com.example.mediasoft_test.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mediasoft_test.model.Character

@Entity(tableName = "character")
data class CharacterRoomEntity(
    @PrimaryKey val character_id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "species") val species: String,
    @ColumnInfo(name = "img") val img: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "is_selected") var isSelected: Boolean
) {
    fun toCharacter(): Character {
        return Character(
            id = character_id,
            name = name,
            species = species,
            gender = gender,
            status = status,
            type = type,
            img = img,
            selected = isSelected
        )
    }
}
