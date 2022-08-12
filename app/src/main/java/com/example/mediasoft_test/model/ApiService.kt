package com.example.mediasoft_test.model

import com.example.mediasoft_test.model.data.CharacterDT
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL= "https://gateway.marvel.com/"

private val logging = run {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.apply {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }
}
var clientM: OkHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .baseUrl(BASE_URL).client(clientM).build()

interface ApiService {
    @GET("v1/public/characters?ts=1&apikey=1cadb66a117fd6e06d0e32e09bfb3bed&hash=8ffb610d5c9aafa787f5aaa1bd4c6548")
    fun getCharactersList(@Query("offset") page: Int): Single<CharacterResponse>
}

object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}