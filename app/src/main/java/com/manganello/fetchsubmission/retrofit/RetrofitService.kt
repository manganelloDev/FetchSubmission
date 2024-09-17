package com.manganello.fetchsubmission.retrofit

import com.manganello.fetchsubmission.entity.RemoteData
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("hiring.json") // Replace with the actual API path
    fun getItems(): Call<List<RemoteData>>
}