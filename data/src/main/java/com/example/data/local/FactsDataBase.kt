package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.FactItemModel
import com.example.data.TotalFactsSize

@Database(entities = [FactItemModel::class, TotalFactsSize::class], version = 6, exportSchema = false)
abstract class FactsDataBase: RoomDatabase() {
    abstract fun factsDao(): FactsDao

    companion object {
        private var INSTANCE: FactsDataBase? = null

        private val lock = Any()

        fun getInstance(context: Context): FactsDataBase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FactsDataBase::class.java, "FactsDataBase.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}