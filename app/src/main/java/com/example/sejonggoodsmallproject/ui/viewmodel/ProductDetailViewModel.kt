package com.example.sejonggoodsmallproject.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sejonggoodsmallproject.data.model.ProductDetailData
import com.example.sejonggoodsmallproject.data.repository.MainRepository
import retrofit2.Response

class ProductDetailViewModel(private val mainRepository: MainRepository, private val itemId: Int) : ViewModel() {

    // 상품상세 조회
    suspend fun getProductDetail(itemId: Int) : Response<ProductDetailData> {
        return mainRepository.getProductDetail(itemId)
    }
}