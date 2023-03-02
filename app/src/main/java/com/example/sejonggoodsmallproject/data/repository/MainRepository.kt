package com.example.sejonggoodsmallproject.data.repository

import android.app.Application
import android.util.Log
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

    // 메인화면, 상품 목록
    suspend fun getAllProducts(memberId: MemberIdPost) : List<ProductListResponse> {
        return retrofitService.getAllProducts(memberId)
    }

    // 상품 상세 정보
    suspend fun getProductDetail(itemId: Int) : Response<ProductDetailResponse> {
        return retrofitService.getProductDetail(itemId)
    }

    // 찜하기
    suspend fun addFavorite(itemId: Long) : Response<FavoriteResponse> {
        return retrofitService.addFavorite("Bearer $myToken", itemId)
    }
    // 찜하기 취소
    suspend fun deleteFavorite(itemId: Long) : Response<FavoriteResponse> {
        return retrofitService.deleteFavorite("Bearer $myToken", itemId)
    }
    // 찜한 상품 조회
    suspend fun getFavoriteList() = retrofitService.getFavorite("Bearer $myToken")

    // 상품상세에서 주문
    suspend fun postOrderInDetail(orderDetailPost: OrderDetailPost, itemId: Long) : Response<OrderDetailResponse> {
        return retrofitService.orderInDetail("Bearer $myToken", orderDetailPost, itemId)
    }
    // 장바구니에서 주문
    suspend fun postOrderInCart(orderCartPost: OrderCartPost) : Response<OrderCartResponse> {
        return retrofitService.orderInCart("Bearer $myToken", orderCartPost)
    }

    // 장바구니 담기
    suspend fun addCart(itemId: String, addCartPost: AddCartPost) : Response<AddCartResponse> {
        return retrofitService.postAddCart("Bearer $myToken", itemId, addCartPost)
    }

    // 장바구니 조회
    suspend fun getCart() : List<CartListResponse> {
        return retrofitService.getCartList("Bearer $myToken")
    }

    // 장바구니 삭제
    suspend fun deleteCart(cartId: Long) : List<CartListResponse> {
        return retrofitService.deleteCart("Bearer $myToken", cartId)
    }

    // 장바구니 수정
    suspend fun updateCart(cartId: Long, quantity: Int) : CartListResponse {
        return retrofitService.updateCart("Bearer $myToken", UpdateCartPost(cartId, quantity))
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