package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.FactItemModel

@Database(entities = [FactItemModel::class], version = 5, exportSchema = false)
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