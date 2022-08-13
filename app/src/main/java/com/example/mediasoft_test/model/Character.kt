package com.example.mediasoft_test.model

import com.example.mediasoft_test.model.data.CharacterDT
import com.example.mediasoft_test.model.data.Info
import com.google.gson.annotations.SerializedName

/*data class CharacterResponse (
    val info: Info,
    val data: Data
    )

data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: Int?,
    val prev: Int?
    )*/

    data class Character(
        val id: Int,
        val name: String,
        val gender: String,
        val species: String,
        val status: String,
        val type: String,
        @SerializedName("image") val img: String
        )
