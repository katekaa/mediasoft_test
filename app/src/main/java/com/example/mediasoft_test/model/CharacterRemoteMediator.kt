package com.example.mediasoft_test.model

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import com.example.mediasoft_test.model.data.CharacterDT
import com.example.mediasoft_test.model.room.AppDatabase
import io.reactivex.rxjava3.core.Single
import com.example.mediasoft_test.model.room.CharacterRoomEntity
import io.reactivex.rxjava3.schedulers.Schedulers

@ExperimentalPagingApi
class CharacterRemoteMediator(
    private val database: AppDatabase
) : RxRemoteMediator<Int, CharacterRoomEntity>() {

    private var pageIndex = 1

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, CharacterRoomEntity>
    ): Single<MediatorResult> {

        pageIndex = getPageIndex(loadType) ?: return Single.just(
            MediatorResult.Success(endOfPaginationReached = true)
        )

        return Single.just(loadType).subscribeOn(Schedulers.io())
            .flatMap {
                Api.retrofitService.getCharactersList(pageIndex)
                    .map { it -> mapper(it) }
                    .map { insertToDb(loadType, it) }
                    .map<MediatorResult> {
                        MediatorResult.Success(endOfPaginationReached = it.size < state.config.pageSize)
                    }
            }.onErrorReturn { MediatorResult.Error(it) }
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        pageIndex = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.APPEND -> ++pageIndex
            LoadType.PREPEND -> return null
        }
        return pageIndex
    }

    private fun mapper(chars: CharacterDT?): List<CharacterRoomEntity> {
        return chars?.results?.map { it.toCharacterRoomEntity() } ?: listOf()
    }

    private fun insertToDb(
        loadType: LoadType,
        list: List<CharacterRoomEntity>
    ): List<CharacterRoomEntity> {

        if (loadType == LoadType.REFRESH) {
            database.getCharacterDAO().clearCharacters()
        }

        database.getCharacterDAO().insertAll(list)

        return list

    }
}
