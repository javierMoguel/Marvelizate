package com.example.myapplication

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getCharacters(@Url url: String): Response<CharacterResponse>

    @GET
    suspend fun getComic(@Url url: String ): Response<ComicResponse>
}