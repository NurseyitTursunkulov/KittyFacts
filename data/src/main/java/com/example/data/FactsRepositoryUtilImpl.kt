package com.example.data

import com.example.data.local.FactsDao

class FactsRepositoryUtilImpl(
    val factsDao: FactsDao
) : FactsRepositoryUtil {
    override suspend fun getFactsSize(): Int {
        return factsDao.getSize()
    }
}