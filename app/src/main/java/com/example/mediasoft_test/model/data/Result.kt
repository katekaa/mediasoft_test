package com.example.mediasoft_test.model.data

import com.example.mediasoft_test.model.Character

data class Result(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
) {
    fun toCharacter(): Character {
        return Character(
            id = id,
            description = description,
            name = name,
            thumbnail = thumbnail.path,
            thumbnailExt = thumbnail.extension,
            modified = modified.substringBefore('T'),
            img = "${thumbnail.path}/portrait_xlarge.${thumbnail.extension}"
        )
    }
}