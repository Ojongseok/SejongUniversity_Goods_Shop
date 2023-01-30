package com.example.sejonggoodsmallproject.data.repository

import com.example.sejonggoodsmallproject.data.model.ProductListData
import com.example.sejonggoodsmallproject.util.RetrofitInstance.retrofitService
import retrofit2.Response

class MainRepository {
    suspend fun getTestData() : List<ProductListData> {
        return retrofitService.getTestData()
    }
}