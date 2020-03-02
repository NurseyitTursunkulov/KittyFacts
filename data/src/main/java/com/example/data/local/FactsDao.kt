package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.data.FactItemModel

@Dao
interface FactsDao {
//TODO add pagination
//    @Query("SELECT * FROM FactItemModel where factNumber between :page and :page+50")

    @Query("SELECT * FROM FactItemModel ")
    fun getFacts(): List<FactItemModel>

    @Query("SELECT MAX(factNumber) from FactItemModel")
    fun getSize(): Int

    @Insert
    fun insertAll(vararg users: FactItemModel)

    @Query("DELETE FROM FactItemModel")
    fun delete()

}