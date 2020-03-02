package com.example.data

import android.util.Log

class FactRepositoryImpl(val factServiceApi: FactServiceApi) : FactsRepository {
    override suspend fun getFacts(): Result<List<FactItemModel>> {
        try {
            val call = factServiceApi.getFacts().await()
            Log.d("Nurs" , "$call")
            if (call.isSuccessful) {
                val list = ArrayList<FactItemModel>()

                call.body()?.data?.forEach {
                    list.add(it)
                }
                return Result.Success(list)
            }
            else {
                return Result.Error(Exception("no connection"))
            }
        }catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun refreshFacts() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}