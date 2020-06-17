package com.example.kittyfacts

import androidx.annotation.VisibleForTesting
import com.example.data.FactItemModel
import com.example.data.Result
import com.example.domain.GetFactsUseCase

class FakeGetFactsUseCase : GetFactsUseCase {
    val factItemList: LinkedHashMap<Int, FactItemModel> = LinkedHashMap()
    var result:Result<List<FactItemModel>> = Result.Success(factItemList.values.toList())
    private val startPage = 1

    override suspend fun invoke(): Result<List<FactItemModel>> {
        return result
    }

    override suspend fun refreshFactsRepository(): Result<String> {
        return Result.Success("Success")
    }

    override var page: Int = startPage

    override suspend fun getFactsItemsSize(): Int {
        return factItemList.size
    }

    @VisibleForTesting
    fun addNews(vararg news: FactItemModel) {
        for (newsItem in news) {
            factItemList[newsItem.factNumber] = newsItem
        }
    }

}