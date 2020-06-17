package com.example.domain

import com.example.data.FactItemModel
import com.example.data.FactsRepository
import com.example.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetFactsUseCaseImpl(
    val factRepository: FactsRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GetFactsUseCase {

    override var page = factRepository.factsRepositoryUtil.START_PAGE

    override suspend fun invoke(): Result<List<FactItemModel>> {

        return withContext(ioDispatcher) {
            factRepository.getFacts((page)).also { result ->
                if (result is Result.Success) page += 1
            }
        }
    }

    override suspend fun refreshFactsRepository(): Result<String> {
        return factRepository.refreshFactsRepository()
    }

    override suspend fun getFactsItemsSize(): Int {
        return factRepository.factsRepositoryUtil.getFactsSize()
    }
}