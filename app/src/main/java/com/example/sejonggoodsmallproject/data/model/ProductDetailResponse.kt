package com.example.sejonggoodsmallproject.data.model

import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Int,
    @SerializedName("description") val description: String?,
    @SerializedName("color") val color: String?,
    @SerializedName("size") val size: String
)