package com.example.sejonggoodsmallproject.data.model

import com.google.gson.annotations.SerializedName

data class ProductDetailResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Int,
    @SerializedName("description") val description: String?,
    @SerializedName("color") val color: String?,
    @SerializedName("size") val size: String,
    @SerializedName("itemImages") val img: List<imgProductDetailResult>
)

data class imgProductDetailResult(
    @SerializedName("id") val id: Long,
    @SerializedName("imgName") val imgName: String,
    @SerializedName("oriImgName") val oriImgName: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("repImgUrl") val repImgUrl: String
)
