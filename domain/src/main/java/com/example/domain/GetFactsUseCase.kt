package com.example.domain

import com.example.data.FactItemModel
import com.example.data.Result

interface GetFactsUseCase {
    suspend operator fun invoke(): Result<List<FactItemModel>>
    suspend fun refreshFactsRepository(): Result<String>
    var page: Int
    suspend fun getFactsItemsSize(): Int
}