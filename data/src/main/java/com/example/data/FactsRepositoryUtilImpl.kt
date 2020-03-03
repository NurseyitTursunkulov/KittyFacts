package com.example.data

import android.util.Log
import com.example.data.local.FactsDao
import com.example.data.remote.FactServiceApi
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.FlexibleTypeDeserializer

class FactsRepositoryUtilImpl(
    val factServiceApi: FactServiceApi,
    val factsDao: FactsDao
) : FactsRepositoryUtil {
    override suspend fun getFactsSize(): Int {

        try {
            val facts = factsDao.getSize()
            when {
                facts!= null -> return facts.size
                else -> {
                    val call = factServiceApi.getFacts("1").await()
                    if (call.isSuccessful) {
                        call.body()?.totalItemsSize?.let {
                            factsDao.setSize(TotalFactsSize(it))
                            return it
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("Nurs", "error in FactsRepositoryUtil: $e")
        }
        return 0
    }
}