package com.example.data

interface FactsRepositoryUtil {
    val START_PAGE: Int
        get() = 0

    suspend fun getFactsSize():Int
}