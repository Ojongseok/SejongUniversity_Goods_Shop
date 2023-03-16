package com.sejong.sejonggoodsmallproject.util

import com.sejong.sejonggoodsmallproject.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {
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

    // 1.3 이메일 찾기
    @POST("auth/find/email")
    suspend fun findEmail(
        @Body findEmailPost: FindEmailPost
    ) : Response<FindEmailResponse>

    // 1.4 비밀번호 찾기
    @POST("auth/find/password")
    suspend fun findPassword(
        @Body findPasswordPost: FindPasswordPost
    ) : Response<FindPasswordResponse>

    // 1.5 비밀번호 찾기 - 인증번호 일치 확인
    @POST("auth/check/authNumber")
    suspend fun findPasswordCheck(
        @Body findPasswordPost2: FindPasswordPost2
    ) : Response<FindPasswordResponse2>

    // 1.6 비밀번호 변경
    @POST("auth/update/password")
    suspend fun updatePassword(
        @Body findPasswordPost3: FindPasswordPost3
    ) : Response<FindPasswordResponse3>

    // 3.2 전체상품 조회
    @GET("items/all")
    suspend fun getAllProducts(
        @Header("Authorization") BearerToken: String
    ) : List<ProductListResponse>

    // 3.4 상품 상세보기
    @GET("items/detail/{itemId}")
    suspend fun getProductDetail(
        @Header("Authorization") BearerToken: String,
        @Path("itemId") itemId: Int
    ) : Response<ProductDetailResponse>

    // 5.1 장바구니 담기
    @POST("cart/{itemId}")
    suspend fun postAddCart(
        @Header("Authorization") BearerToken: String,
        @Path("itemId") itemId: String,
        @Body addCartPost: AddCartPost
    ) : Response<AddCartResponse>

    // 5.2 장바구니 조회
    @GET("cart/all")
    suspend fun getCartList(
        @Header("Authorization") BearerToken: String,
    ) : List<CartListResponse>

    // 5.3 장바구니 삭제
    @DELETE("cart/delete/{cartId}")
    suspend fun deleteCart(
        @Header("Authorization") BearerToken: String,
        @Path("cartId") cartId: Long
    ) : List<CartListResponse>

    // 5.4 장바구니 수정
    @PATCH("cart/update")
    suspend fun updateCart(
        @Header("Authorization") BearerToken: String,
        @Body updateCartPost: UpdateCartPost
    ) : CartListResponse

    // 6.1 상세보기에서 주문
    @POST("order/{itemId}")
    suspend fun orderInDetail(
        @Header("Authorization") BearerToken: String,
        @Body orderDetailPost: OrderDetailPost,
        @Path("itemId") itemId: Long
    ) : Response<OrderDetailResponse>

    // 6.2 장바구니에서 주문
    @POST("order/cart")
    suspend fun orderInCart(
        @Header("Authorization") BearerToken: String,
        @Body orderCartPost: OrderCartPost
    ) : Response<OrderDetailResponse>

    // 6.3 주문 상품 전체 조회
    @GET("order/list/all")
    suspend fun getOrderList(
        @Header("Authorization") BearerToken: String
    ) : List<OrderListResponse>

    // 7.1 찜하기
    @POST("scrap/{itemId}")
    suspend fun addFavorite(
        @Header("Authorization") BearerToken: String,
        @Path("itemId") itemId: Long
    ) : Response<FavoriteResponse>

    // 7.2 찜하기 취소
    @DELETE("scrap/delete/{itemId}")
    suspend fun deleteFavorite(
        @Header("Authorization") BearerToken: String,
        @Path("itemId") itemId: Long
    ) : Response<FavoriteResponse>

    // 7.3 찜한 상품 조회
    @GET("scrap/list")
    suspend fun getFavorite(
        @Header("Authorization") BearerToken: String
    ) : List<FavoriteListResponse>

}