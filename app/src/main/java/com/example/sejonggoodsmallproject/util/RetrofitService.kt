package com.example.sejonggoodsmallproject.util

import com.example.sejonggoodsmallproject.data.model.RetrofitResponse
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {
    @GET("/")
    suspend fun getTestData() : Response<RetrofitResponse>
}