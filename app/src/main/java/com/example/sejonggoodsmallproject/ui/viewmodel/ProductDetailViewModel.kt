package com.example.sejonggoodsmallproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sejonggoodsmallproject.data.model.AddCartPost
import com.example.sejonggoodsmallproject.data.model.AddCartResponse
import com.example.sejonggoodsmallproject.data.model.ProductDetailResponse
import com.example.sejonggoodsmallproject.data.repository.MainRepository
import retrofit2.Response

class ProductDetailViewModel(private val mainRepository: MainRepository) : ViewModel() {

    // 상품상세 조회
    suspend fun getProductDetail(itemId: Int) : Response<ProductDetailResponse> {
        return mainRepository.getProductDetail(itemId)
    }

    // 카트 담기
    suspend fun addCart(addCartPost: AddCartPost, itemId: Int) : Response<AddCartResponse> {
        return mainRepository.addCart(itemId.toString(), addCartPost)
    }
}