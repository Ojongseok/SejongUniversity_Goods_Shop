package com.example.sejonggoodsmallproject.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.sejonggoodsmallproject.data.model.ProductListData
import com.example.sejonggoodsmallproject.data.room.RecentSearchDatabase
import com.example.sejonggoodsmallproject.data.room.RecentSearchModel
import com.example.sejonggoodsmallproject.util.RetrofitInstance.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainRepository(application: Application) {

    suspend fun getTestData() : List<ProductListData> {
        return retrofitService.getTestData()
    }

    // Room, 최근검색어
    private val mRecentSearchDatabase = RecentSearchDatabase.getInstance(application)
    private val mRecentSearchDAO = mRecentSearchDatabase.recentSearchDao()
    private val mRecentSearchItems = mRecentSearchDAO.getRecentSearched()

    fun getRecentSearchItemsList() : LiveData<List<RecentSearchModel>> {
        return mRecentSearchItems
    }

    fun insertRecentSearched(recentSearchModel: RecentSearchModel) {
        CoroutineScope(Dispatchers.IO).launch {
            mRecentSearchDAO.insertRecentSearched(recentSearchModel)
        }
    }

    fun deleteRecentSearched(recentSearchModel: RecentSearchModel) {
        CoroutineScope(Dispatchers.IO).launch {
            mRecentSearchDAO.deleteRecentSearched(recentSearchModel)
        }
    }
}