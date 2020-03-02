package com.example.data

interface FactsRepository {
    suspend fun getFacts(): Result<List<FactItemModel>>
    suspend fun refreshFacts()
}