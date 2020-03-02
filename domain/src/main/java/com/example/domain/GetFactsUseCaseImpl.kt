package com.example.domain

import com.example.data.FactItemModel
import com.example.data.FactsRepository
import com.example.data.Result

class GetFactsUseCaseImpl(val factRepository: FactsRepository):GetFactsUseCase {
    override suspend fun invoke(): Result<List<FactItemModel>> {
        return factRepository.getFacts()
    }
}