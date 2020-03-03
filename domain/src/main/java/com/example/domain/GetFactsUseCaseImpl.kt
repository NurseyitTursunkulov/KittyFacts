package com.example.domain

import com.example.data.FactItemModel
import com.example.data.FactsRepository
import com.example.data.Result

class GetFactsUseCaseImpl(val factRepository: FactsRepository) : GetFactsUseCase {

    var page = factRepository.factsRepositoryUtil.START_PAGE

    override suspend fun invoke(): Result<List<FactItemModel>> {
        return factRepository.getFacts((page)).also {result->
            if (result is Result.Success) page += 1
        }
    }

    override suspend fun refreshFactsRepository(): Result<String> {
        return factRepository.refreshFactsRepository()
    }

    suspend fun getFactsItemsSize():Int{
        return factRepository.factsRepositoryUtil.getFactsSize()
    }
}