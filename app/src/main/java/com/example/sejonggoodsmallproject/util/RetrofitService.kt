package com.example.sejonggoodsmallproject.util

import com.example.sejonggoodsmallproject.data.model.*
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
        @Body userInfo : SignupPost
    ) : Response<SignupResponse>

    // 1.2 로그인
    @POST("auth/signin")
    suspend fun authLogin(
        @Body userInfo: LoginPost
    ) : Response<LoginResponse>
}