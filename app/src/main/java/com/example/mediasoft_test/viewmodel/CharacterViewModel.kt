package com.example.mediasoft_test.viewmodel

import android.content.Context
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.example.mediasoft_test.R
import com.example.mediasoft_test.model.Api
import com.example.mediasoft_test.model.data.Result
import com.example.mediasoft_test.model.Character
import com.example.mediasoft_test.model.data.CharacterDT
import com.example.mediasoft_test.repository.CharacterRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class CharacterViewModelFactory(private val repo: CharacterRepository): ViewModelProvider.NewInstanceFactory() {
    @ExperimentalCoroutinesApi
    override fun <T : ViewModel> create(modelClass: Class<T>) = CharacterViewModel(repo) as T
}



@ExperimentalCoroutinesApi
class CharacterViewModel(private val repo: CharacterRepository): ViewModel() {
    //lateinit var characterList: MutableLiveData<MutableList<Character>>


    //init {
        //characterList = MutableLiveData()
     //   callApi()
    //}

    //fun getCharacterListObserver (): MutableLiveData<MutableList<Character>> {
    //    return characterList
    //}

    @ExperimentalCoroutinesApi
    fun callApi (): Flowable<PagingData<Character>> {
        Log.d("aboba", "call api from vm ${repo.getCharacters()}")
        return repo.getCharacters().cachedIn(viewModelScope)
    }




}