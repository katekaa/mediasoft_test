package com.example.mediasoft_test.model

import com.example.mediasoft_test.model.data.CharacterDT
import com.example.mediasoft_test.model.data.Data
import com.google.gson.annotations.SerializedName

data class CharacterResponse (
    //@SerializedName("info") val pageInfo: PageInfo,
    val total: Int = 0,
    val offset: Int = 0,
    val data: Data
    )

data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: Int?,
    val prev: Int?
    )

    data class Character(
        val id: Int,
        val name: String,
        val description: String,
        val thumbnail: String,
        val thumbnailExt: String,
        val modified: String,
        @SerializedName("resourceURI") val img: String
        )
