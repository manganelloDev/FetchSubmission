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
    private val _recyclerData = MutableLiveData<Map<Int, List<RemoteData>>>()
    val recyclerData: LiveData<Map<Int, List<RemoteData>>> get() = _recyclerData

    private val _listIds = MutableLiveData<List<Int>>()
    val listIds: LiveData<List<Int>> get() = _listIds

    fun retrieveData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitInstance = retrofit.create(RetrofitService::class.java)

        retrofitInstance.getItems().enqueue(object : Callback<List<RemoteData>> {
            override fun onResponse(call: Call<List<RemoteData>>, response: Response<List<RemoteData>>) {
                if (response.isSuccessful) {
                    val items = response.body()
                        ?.filter { !it.name.isNullOrBlank() }
                        ?.groupBy { it.listId }
                        ?.toSortedMap()
                        ?.mapValues { (_, items) -> items.sortedBy { it.name } }
                        ?: emptyMap()

                    _recyclerData.postValue(items)

                    val listIds = items.keys.toList()
                    _listIds.postValue(listIds)
                }
            }

            override fun onFailure(call: Call<List<RemoteData>>, t: Throwable) {
                // Additional error handling logic could be implemented here to alert the user of the issue
                Log.e("MainActivity", "Error fetching data: ${t.cause} ${t.message}", t)
            }
        })
    }
}