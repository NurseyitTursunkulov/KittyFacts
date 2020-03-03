package com.example.data

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
            if (facts.isNotEmpty()) {
                return Result.Success(facts)
            }
            else if (page == factsRepositoryUtil.START_PAGE) {
                refreshFactsRepository()
                val getFactsAfterRefresh = factsDao.getFacts(page)
                if (getFactsAfterRefresh.isNotEmpty()) {
                    return Result.Success(getFactsAfterRefresh)
                }
                return Result.Error(Exception("no connection"))
            }
            else{
                return Result.Success(emptyList())
            }

        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun refreshFactsRepository(): Result<String> {
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
}