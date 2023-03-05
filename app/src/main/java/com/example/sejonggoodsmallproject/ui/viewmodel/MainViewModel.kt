package com.example.sejonggoodsmallproject.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sejonggoodsmallproject.data.model.MemberIdPost
import com.example.sejonggoodsmallproject.data.model.OrderCartPost
import com.example.sejonggoodsmallproject.data.model.OrderCartResponse
import com.example.sejonggoodsmallproject.data.model.ProductListResponse
import com.example.sejonggoodsmallproject.data.repository.MainRepository
import com.example.sejonggoodsmallproject.data.room.RecentSearchModel
import retrofit2.Response

class MainViewModel(private val mainRepository: MainRepository): ViewModel() {

    // 전체상품 조회
    suspend fun getAllProducts(memberId: MemberIdPost) = mainRepository.getAllProducts(memberId)

    // 장바구니 조회
    suspend fun getCartList() = mainRepository.getCart()

    // 장바구니 삭제
    suspend fun deleteCart(cartId: Long) = mainRepository.deleteCart(cartId)

    // 장바구니 수정
    suspend fun updateCart(cartId: Long, quantity: Int) = mainRepository.updateCart(cartId, quantity)

    // 장바구니에서 주문
    suspend fun postOrderInCart(orderCartPost: OrderCartPost) = mainRepository.postOrderInCart(orderCartPost)

    // 주문내역 전체 조회
    suspend fun getOrderList() = mainRepository.getOrderList()

    // 찜한 상품 조회
    suspend fun getFavoriteList() = mainRepository.getFavoriteList()

    // Search 관련
    fun getRecentSearchItemsList() : LiveData<List<RecentSearchModel>> {
        return mainRepository.getRecentSearchItemsList()
    }
    fun insertRecentSearch(recentSearchModel: RecentSearchModel) {
        mainRepository.insertRecentSearched(recentSearchModel)
    }
    fun deleteRecentSearch(recentSearchModel: RecentSearchModel) {
        mainRepository.deleteRecentSearched(recentSearchModel)
    }
    fun deleteRecentSearchedAll() {
        mainRepository.deleteRecentSearchedAll()
    }
}