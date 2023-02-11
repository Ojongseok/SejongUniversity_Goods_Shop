package com.example.sejonggoodsmallproject.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.sejonggoodsmallproject.data.model.*
import com.example.sejonggoodsmallproject.data.room.RecentSearchDatabase
import com.example.sejonggoodsmallproject.data.room.RecentSearchModel
import com.example.sejonggoodsmallproject.util.MyApplication
import com.example.sejonggoodsmallproject.util.RetrofitInstance.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainRepository(application: Application) {
    val myToken = MyApplication.prefs.getString("accessToken","")

    suspend fun getAllProducts() : List<ProductListResponse> {
        return retrofitService.getAllProducts()
    }

    suspend fun getProductDetail(itemId: Int) : Response<ProductDetailResponse> {
        return retrofitService.getProductDetail(itemId)
    }

    // 카트 담기
    suspend fun addCart(itemId: String, addCartPost: AddCartPost) : Response<AddCartResponse> {
        return retrofitService.postAddCart("Bearer $myToken", itemId, addCartPost)
    }

    //카트 조회
    suspend fun getCart() : List<CartListResponse> {
        return retrofitService.getCartList("Bearer $myToken")
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
    fun deleteRecentSearchedAll() {
        CoroutineScope(Dispatchers.IO).launch {
            mRecentSearchDAO.deleteRecentSearchedAll()
        }
    }
}