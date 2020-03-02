package com.example.data

import android.util.Log
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.data.local.FactsDao
import com.example.data.remote.FactServiceApi

class FactRepositoryImpl(val factServiceApi: FactServiceApi, val factsDao: FactsDao) : FactsRepository {
    override suspend fun getFacts(): Result<List<FactItemModel>> {
        try {
            val call = factsDao.getFacts()
            if (call.isNotEmpty()) {
                return Result.Success(factsDao.getFacts())
            } else {
                return Result.Error(Exception("no connection"))
            }
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun refreshFactsRepository() : Result<String>{
        factsDao.delete()
        try {
            val call = factServiceApi.getFacts().await()
            if (call.isSuccessful) {
                val list = ArrayList<FactItemModel>()

                call.body()?.data?.forEachIndexed { index, factItemModel ->
                    factItemModel.factNumber = index
                    list.add(factItemModel)

                    factsDao.insertAll(factItemModel)
                }
                return Result.Success("Success")
            } else {
                return Result.Error(Exception("no connection"))
            }
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun getFactsSize(): Int {
        return factsDao.getSize()
    }
}