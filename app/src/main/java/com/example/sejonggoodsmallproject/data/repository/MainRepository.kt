package com.example.sejonggoodsmallproject.data.repository

import com.example.sejonggoodsmallproject.util.RetrofitInstance.retrofitService

class MainRepository {
    suspend fun getTestData() {
        retrofitService.getTestData()
    }
}