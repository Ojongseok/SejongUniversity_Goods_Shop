package com.sejong.sejonggoodsmallproject.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [RecentSearchModel::class],
    version = 1,
    exportSchema = false
)
abstract class RecentSearchDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDAO

    companion object {
        @Volatile
        private var INSTANCE: RecentSearchDatabase? = null

        private fun buildDatabase(context: Context): RecentSearchDatabase =
            Room.databaseBuilder(
                context.applicationContext,
                RecentSearchDatabase::class.java,
                "RecentSearch.db"
            ).build()

        fun getInstance(context: Context): RecentSearchDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
    }
}