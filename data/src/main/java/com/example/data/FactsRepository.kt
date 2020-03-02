package com.example.data

import androidx.fragment.app.Fragment

interface FactsRepository {
    suspend fun getFacts(): Result<List<FactItemModel>>
    suspend fun refreshFactsRepository(): Result<String>

    /** this method will be used later for pagination*/
    suspend fun getFactsSize():Int
}