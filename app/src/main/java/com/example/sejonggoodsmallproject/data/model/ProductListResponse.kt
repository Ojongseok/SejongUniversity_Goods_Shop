package com.example.sejonggoodsmallproject.data.model

import com.google.gson.annotations.SerializedName


data class ProductListResponse(
    @SerializedName("id") val id: Long?,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Int,
    @SerializedName("categoryId") val categoryId: Long,
    @SerializedName("description") val description: String,
    @SerializedName("itemImages") val img: List<imgProductListResult>
)

data class imgProductListResult(
    @SerializedName("id") val id: Long,
    @SerializedName("imgName") val imgName: String,
    @SerializedName("oriImgName") val oriImgName: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("repImgUrl") val repImgUrl: String
)
