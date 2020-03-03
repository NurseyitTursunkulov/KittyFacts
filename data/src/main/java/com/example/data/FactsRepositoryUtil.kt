package com.example.data

interface FactsRepositoryUtil {
    val START_PAGE: Int
        get() = 1

    suspend fun getFactsSize():Int
}