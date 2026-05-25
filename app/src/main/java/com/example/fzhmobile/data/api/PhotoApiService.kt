package com.example.fzhmobile.data.api

import com.example.fzhmobile.data.model.PhotoModel
import retrofit2.http.GET

interface PhotoApiService {
    @GET("v2/list") // Diubah menjadi v2/list menyesuaikan endpoint asli picsum
    suspend fun getPhotos(): List<PhotoModel>
}