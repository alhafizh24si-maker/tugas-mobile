package com.example.fzhmobile.data.api

import com.example.fzhmobile.data.model.CatFactModel
import retrofit2.http.GET

interface CatFactApiService {
    @GET("fact")
    suspend fun getCatFact(): CatFactModel
}