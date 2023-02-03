package com.example.sejonggoodsmallproject.data.model

import com.google.gson.annotations.SerializedName


data class ProductListResponse(
    @SerializedName("id") val id: Long?,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Int,
    @SerializedName("description") val description: String
)