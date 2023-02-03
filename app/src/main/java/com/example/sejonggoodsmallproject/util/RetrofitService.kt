package com.example.sejonggoodsmallproject.util

import com.example.sejonggoodsmallproject.data.model.ProductDetailResponse
import com.example.sejonggoodsmallproject.data.model.ProductListResponse
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
    // 3.2 전체상품 조회
    @GET("items/all")
    suspend fun getAllProducts() : List<ProductListResponse>

    // 3.4 상품 상세보기
    @GET("items/detail/{itemId}")
    suspend fun getProductDetail(
        @Path("itemId") itemId: Int
    ) : Response<ProductDetailResponse>

    // 1.1 회원가입
    @POST("auth/signup")
    suspend fun authSignup(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("username") username: String,
        @Field("birth") birth: String
    ) : Response<*>
}