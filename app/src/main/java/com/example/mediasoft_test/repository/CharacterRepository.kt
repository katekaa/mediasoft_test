package com.example.mediasoft_test.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.example.mediasoft_test.model.Character
import com.example.mediasoft_test.model.CharacterPagingSource
import io.reactivex.rxjava3.core.Flowable

class CharacterRepository() {

    fun getCharacters(): Flowable<PagingData<Character>> {
        Log.d("aboba", "make pager")
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40),
            pagingSourceFactory = { CharacterPagingSource() }
        ).flowable

    }
}