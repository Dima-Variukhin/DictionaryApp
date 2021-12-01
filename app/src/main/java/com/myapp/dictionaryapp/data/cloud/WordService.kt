package com.myapp.dictionaryapp.data.cloud

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WordService {
    @GET("api/v2/entries/en/{hello}")
    suspend fun fetchWord(@Path("hello") word: String): Response<List<WordCloud>>
}
