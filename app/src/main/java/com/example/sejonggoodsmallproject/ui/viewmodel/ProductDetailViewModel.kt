package com.example.sejonggoodsmallproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sejonggoodsmallproject.data.model.*
import com.example.sejonggoodsmallproject.data.repository.MainRepository
import retrofit2.Response

class ProductDetailViewModel(private val mainRepository: MainRepository) : ViewModel() {
    // 상품상세 조회
    suspend fun getProductDetail(itemId: Int) : Response<ProductDetailResponse> {
        return mainRepository.getProductDetail(itemId)
    }

    // 상품상세에서 주문
    suspend fun postOrderInDetail(orderDetailPost: OrderDetailPost, itemId: Long) : Response<OrderDetailResponse> {
        return mainRepository.postOrderInDetail(orderDetailPost, itemId)
    }

    // 찜하기
    suspend fun addFavorite(itemId: Long) : Response<FavoriteResponse> {
        return mainRepository.addFavorite(itemId)
    }
    // 찜하기 취소
    suspend fun deleteFavorite(itemId: Long) : Response<FavoriteResponse> {
        return mainRepository.deleteFavorite(itemId)
    }

    // 카트 담기
    suspend fun addCart(addCartPost: AddCartPost, itemId: Int) : Response<AddCartResponse> {
        return mainRepository.addCart(itemId.toString(), addCartPost)
    }
}