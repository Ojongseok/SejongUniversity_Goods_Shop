package com.example.sejonggoodsmallproject.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.sejonggoodsmallproject.data.model.ProductDetailData
import com.example.sejonggoodsmallproject.data.model.ProductListData
import com.example.sejonggoodsmallproject.data.room.RecentSearchDatabase
import com.example.sejonggoodsmallproject.data.room.RecentSearchModel
import com.example.sejonggoodsmallproject.util.RetrofitInstance.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainRepository(application: Application) {

    suspend fun getAllProducts() : List<ProductListData> {
        return retrofitService.getAllProducts()
    }

    suspend fun getProductDetail(itemId: Int) : Response<ProductDetailData> {
        return retrofitService.getProductDetail(itemId)
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