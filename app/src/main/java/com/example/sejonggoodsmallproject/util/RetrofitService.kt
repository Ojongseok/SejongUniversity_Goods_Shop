package com.example.sejonggoodsmallproject.util

import com.example.sejonggoodsmallproject.data.model.ProductListData
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {
    @GET("items/all")
    suspend fun getTestData() : List<ProductListData>
}