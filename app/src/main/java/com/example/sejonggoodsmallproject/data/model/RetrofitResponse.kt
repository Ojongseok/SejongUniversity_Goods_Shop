package com.example.sejonggoodsmallproject.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class RetrofitResponse(
    val member_id: Int,
    val age: Int,
    val name: String
)