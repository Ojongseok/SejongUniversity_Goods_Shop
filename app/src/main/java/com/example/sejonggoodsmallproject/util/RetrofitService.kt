package com.example.sejonggoodsmallproject.util

import com.example.sejonggoodsmallproject.data.model.ProductDetailData
import com.example.sejonggoodsmallproject.data.model.ProductListData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    // 3.2 전체상품 조회
    @GET("items/all")
    suspend fun getAllProducts() : List<ProductListData>

    // 3.4 상품 상세보기
    @GET("items/detail/{itemId}")
    suspend fun getProductDetail(
        @Path("itemId") itemId : Int
    ) : Response<ProductDetailData>


}