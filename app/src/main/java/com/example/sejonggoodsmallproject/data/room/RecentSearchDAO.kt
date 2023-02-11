package com.example.sejonggoodsmallproject.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecentSearchDAO {
    @Query("SELECT * FROM RecentSearch ORDER BY timestamp ASC")
    fun getRecentSearched(): LiveData<List<RecentSearchModel>>

    @Insert
    fun insertRecentSearched(recentSearchModel: RecentSearchModel)

    @Delete
    fun deleteRecentSearched(recentSearchModel: RecentSearchModel)

    @Query("DELETE FROM RecentSearch")
    fun deleteRecentSearchedAll()
}