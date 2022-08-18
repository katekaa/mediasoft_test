package com.example.mediasoft_test.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.example.mediasoft_test.model.room.CharacterRoomEntity
import com.example.mediasoft_test.repository.CharacterRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class CharacterViewModelFactory(private val repo: CharacterRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    override fun <T : ViewModel> create(modelClass: Class<T>) = CharacterViewModel(repo) as T
}

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class CharacterViewModel(private val repo: CharacterRepository) : ViewModel() {

    @ExperimentalPagingApi
    @ExperimentalCoroutinesApi
    fun callApi(
        chars: List<CharacterRoomEntity>?,
        view: View?
    ): Flowable<PagingData<CharacterRoomEntity>> {
        return repo.getCharacters(chars, view).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).cachedIn(viewModelScope)
    }
}
