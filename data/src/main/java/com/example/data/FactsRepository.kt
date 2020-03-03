package com.example.data

import androidx.fragment.app.Fragment

interface FactsRepository {
    val factsRepositoryUtil:FactsRepositoryUtil

    suspend fun getFacts(page:Int): Result<List<FactItemModel>>

    suspend fun refreshFactsRepository(): Result<String>
}