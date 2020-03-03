package com.example.data

import android.util.Log
import com.example.data.local.FactsDao
import com.example.data.remote.FactServiceApi

class FactRepositoryImpl(
        val factServiceApi: FactServiceApi,
        val factsDao: FactsDao,
        override val factsRepositoryUtil: FactsRepositoryUtil
) : FactsRepository {

    override suspend fun getFacts(page: Int): Result<List<FactItemModel>> {
        try {
            val facts = factsDao.getFacts(page)
            when {
                facts.isNotEmpty() -> return Result.Success(facts)

                else -> try {
                    val call = factServiceApi.getFacts(page.toString()).await()

                    return if (call.isSuccessful) {
                        val list = ArrayList<FactItemModel>()
                        val factListModel = call.body()
                        factListModel?.data?.forEachIndexed { index, factItemModel ->
                            factItemModel.factNumber = factListModel.fromIndex + index
                            list.add(factItemModel)
                            factsDao.insertAll(factItemModel)
                        }
                        Result.Success(list)
                    } else {
                        Result.Error(Exception("no connection"))
                    }

                } catch (e: Error) {
                    return Result.Error(Exception())
                }
            }
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun refreshFactsRepository(): Result<String> {
        factsDao.delete()
        try {
            val call = factServiceApi.getFacts(factsRepositoryUtil.START_PAGE.toString()).await()
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
}