package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.data.FactItemModel

@Dao
interface FactsDao {

    @Query("SELECT * FROM FactItemModel  where factNumber between :page and :page+9 ")
    fun getFacts(page:Int): List<FactItemModel>

    @Query("SELECT MAX(factNumber) from FactItemModel")
    fun getSize(): Int

    @Insert
    fun insertAll(vararg users: FactItemModel)

    @Query("DELETE FROM FactItemModel")
    fun delete()

}