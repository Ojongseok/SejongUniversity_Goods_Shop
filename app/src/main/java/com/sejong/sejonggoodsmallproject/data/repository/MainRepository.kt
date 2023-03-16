package com.sejong.sejonggoodsmallproject.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.sejong.sejonggoodsmallproject.data.model.*
import com.sejong.sejonggoodsmallproject.data.room.RecentSearchDatabase
import com.sejong.sejonggoodsmallproject.data.room.RecentSearchModel
import com.sejong.sejonggoodsmallproject.util.MyApplication
import com.sejong.sejonggoodsmallproject.util.RetrofitInstance.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainRepository(application: Application) {
    val myToken = MyApplication.prefs.getString("accessToken","")

    // 메인화면, 상품 목록
    suspend fun getAllProducts() = retrofitService.getAllProducts("Bearer $myToken")

    // 상품 상세 정보
    suspend fun getProductDetail(itemId: Int) = retrofitService.getProductDetail("Bearer $myToken", itemId)

    // 찜하기
    suspend fun addFavorite(itemId: Long) = retrofitService.addFavorite("Bearer $myToken", itemId)
    // 찜하기 취소
    suspend fun deleteFavorite(itemId: Long) = retrofitService.deleteFavorite("Bearer $myToken", itemId)
    // 찜한 상품 조회
    suspend fun getFavoriteList() = retrofitService.getFavorite("Bearer $myToken")

    // 상품상세에서 주문
    suspend fun postOrderInDetail(orderDetailPost: OrderDetailPost, itemId: Long) =
        retrofitService.orderInDetail("Bearer $myToken", orderDetailPost, itemId)

    // 장바구니에서 주문
    suspend fun postOrderInCart(orderCartPost: OrderCartPost) =
        retrofitService.orderInCart("Bearer $myToken", orderCartPost)

    // 장바구니 담기
    suspend fun addCart(itemId: String, addCartPost: AddCartPost) = retrofitService.postAddCart("Bearer $myToken", itemId, addCartPost)

    // 장바구니 조회
    suspend fun getCart() = retrofitService.getCartList("Bearer $myToken")

    // 장바구니 삭제
    suspend fun deleteCart(cartId: Long) = retrofitService.deleteCart("Bearer $myToken", cartId)

    // 장바구니 수정
    suspend fun updateCart(cartId: Long, quantity: Int) =
        retrofitService.updateCart("Bearer $myToken", UpdateCartPost(cartId, quantity))

    // 주문내역 전체 조회
    suspend fun getOrderList() = retrofitService.getOrderList("Bearer $myToken")

    // Room, 최근검색어
    private val mRecentSearchDatabase = RecentSearchDatabase.getInstance(application)
    private val mRecentSearchDAO = mRecentSearchDatabase.recentSearchDao()

    fun getRecentSearchItemsList() : LiveData<List<RecentSearchModel>> {
        return mRecentSearchDAO.getRecentSearched()
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