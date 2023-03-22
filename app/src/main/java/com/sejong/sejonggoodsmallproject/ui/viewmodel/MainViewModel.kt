package com.sejong.sejonggoodsmallproject.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sejong.sejonggoodsmallproject.data.model.*
import com.sejong.sejonggoodsmallproject.data.repository.MainRepository
import com.sejong.sejonggoodsmallproject.data.room.RecentSearchModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val mainRepository: MainRepository): ViewModel() {

    // 전체상품 조회
    suspend fun getAllProducts() = mainRepository.getAllProducts()

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

    // 상품상세 조회
    suspend fun getProductDetail(itemId: Int) : Response<ProductDetailResponse> {
        return mainRepository.getProductDetail(itemId)
    }

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