package com.example.mediasoft_test.model

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.example.mediasoft_test.model.data.CharacterDT
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CharacterPagingSource: RxPagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        val anchor = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchor) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Character>> {
        val position = params.key ?: 0
        Log.d("aboba", "loadsingle")
        return Api.retrofitService.getCharactersList(position)
            .subscribeOn(Schedulers.io())
            //.map{ it -> it.results.map { it -> it.data.results.map { it.toCharacter() } }}
            .map { mapper(it, position) }
            .onErrorReturn { LoadResult.Error(it) }

    }



    private fun mapper(chars: CharacterResponse, page: Int): PagingSource.LoadResult<Int, Character> {
        val list: List<Character> = chars.data.results.map { it.toCharacter() }
        Log.d("aboba", "mapaem ${list.size}")
        return PagingSource.LoadResult.Page(
            //data = chars.list.data.results.map { it.toCharacter() },
            data = chars.data.results.map{it.toCharacter()},
            prevKey = if (page == 1) null else page - 1,
            nextKey = if (page == chars.total) null else page + 1
        )
    }
}