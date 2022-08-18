package com.example.mediasoft_test.repository

import android.view.View
import androidx.paging.*
import androidx.paging.rxjava3.flowable
import com.example.mediasoft_test.App
import com.example.mediasoft_test.model.CharacterRemoteMediator
import com.example.mediasoft_test.model.room.AppDatabase
import com.example.mediasoft_test.model.room.CharacterRoomEntity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.core.Flowable

class CharacterRepository() {

    private var database = App.appContext.let { AppDatabase.getDatabase(it) }

    @ExperimentalPagingApi
    fun getCharacters(
        chars: List<CharacterRoomEntity>?,
        view: View?
    ): Flowable<PagingData<CharacterRoomEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40
            ),
            remoteMediator = CharacterRemoteMediator(database),
            pagingSourceFactory = { getPSource(chars, view) }
        ).flowable
    }

    private fun getPSource(
        chars: List<CharacterRoomEntity>?,
        view: View?
    ): PagingSource<Int, CharacterRoomEntity> {
        if (chars != null) {
            val charsToDelete =
                chars.filter { database.getCharacterDAO().checkCharacter(it.character_id) == 1 }
            if (charsToDelete.isNotEmpty()) {
                val names = charsToDelete.map { it.name }
                val namesStr = names.joinToString(", ")
                charsToDelete.forEach {
                    database.getCharacterDAO().deleteCharacters(it.character_id)
                }
                if (view != null) {
                    Snackbar.make(view, "$namesStr deleted", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        return database.getCharacterDAO().selectAll()
    }
}
