package com.example.data.remote

import com.example.data.FactListModel
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FactServiceApi {
    @GET("facts/?limit=10&")
    fun getFacts(@Query("page") page: String): Deferred<Response<FactListModel>>
}