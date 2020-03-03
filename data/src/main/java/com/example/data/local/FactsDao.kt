package com.example.data.local

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.data.FactItemModel
import com.example.data.TotalFactsSize

@Dao
interface FactsDao {

    @Query("SELECT * FROM FactItemModel  where factNumber between (:page*10-9) and :page*10 ")
    fun getFacts(page:Int): List<FactItemModel>

    //TODO need to test it more errorProne
    @Query("SELECT * from TotalFactsSize")
    fun getSize(): TotalFactsSize?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setSize(size: TotalFactsSize)

    @Insert
    fun insertAll(vararg users: FactItemModel)

    @Query("DELETE FROM FactItemModel")
    fun delete()

}