package com.example.sejonggoodsmallproject.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sejonggoodsmallproject.data.repository.MainRepository
import com.example.sejonggoodsmallproject.data.room.RecentSearchModel

class MainViewModel(private val mainRepository: MainRepository): ViewModel() {

    // 전체상품 조회
    suspend fun getAllProducts() = mainRepository.getAllProducts()

    // 장바구니 조회
    suspend fun getCartList() = mainRepository.getCart()

    // 장바구니 삭제
    suspend fun deleteCart(cartId: Long) = mainRepository.deleteCart(cartId)

    // 장바구니 수정
    suspend fun updateCart(cartId: Long, quantity: Int) = mainRepository.updateCart(cartId, quantity)

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