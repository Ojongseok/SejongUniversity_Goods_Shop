package com.sejong.sejonggoodsmallproject.data.model

import com.google.gson.annotations.SerializedName

data class AddCartResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("memberId") val memberId: Long,
    @SerializedName("itemId") val itemId: Long,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("color") val color: String?,
    @SerializedName("size") val size: String?,
    @SerializedName("price") val price: Int
)
