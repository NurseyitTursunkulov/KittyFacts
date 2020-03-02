package com.example.data.remote

import com.example.data.FactListModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface FactServiceApi {
    @GET("facts/?limit=1000")
    fun getFacts(): Deferred<Response<FactListModel>>
}