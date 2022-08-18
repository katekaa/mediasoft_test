package com.example.mediasoft_test.model.room

import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface CharacterDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<CharacterRoomEntity>)

    @Query("SELECT * FROM character ORDER BY character_id ASC")
    fun selectAll(): PagingSource<Int, CharacterRoomEntity>

    @Query("DELETE FROM character WHERE  character_id = :id")
    fun deleteCharacters(id: Int)

    @Query("DELETE FROM character")
    fun clearCharacters()

    @Query("SELECT Count(character_id) FROM character WHERE character_id = :id")
    fun checkCharacter(id: Int): Int

}