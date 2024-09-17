package com.manganello.fetchsubmission

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manganello.fetchsubmission.entity.RemoteData
import com.manganello.fetchsubmission.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainFragmentViewModel : ViewModel() {
    private val _remoteData = MutableLiveData<List<RemoteData>>()
    val remoteData: LiveData<List<RemoteData>> get() = _remoteData

    fun retrieveData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitInstance = retrofit.create(RetrofitService::class.java)

        retrofitInstance.getItems().enqueue(object : Callback<List<RemoteData>> {
            override fun onResponse(call: Call<List<RemoteData>>, response: Response<List<RemoteData>>) {
                if (response.isSuccessful) {
                    val items = response.body() ?: emptyList()
                    _remoteData.postValue(items)
                }
            }

            override fun onFailure(call: Call<List<RemoteData>>, t: Throwable) {
                Log.e("MainActivity", "Error fetching data: ${t.cause} ${t.message}", t)
            }
        })
    }
}