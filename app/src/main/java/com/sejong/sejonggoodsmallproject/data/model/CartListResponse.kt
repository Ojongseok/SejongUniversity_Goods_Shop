package com.sejong.sejonggoodsmallproject.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CartListResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("memberId") val memberId: Long,
    @SerializedName("itemId") val itemId: Long,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("color") val color: String?,
    @SerializedName("size") val size: String?,
    @SerializedName("price") val price: Int,
    @SerializedName("title") val title: String,
    @SerializedName("repImage") val repImage: RepImageResult,
    @SerializedName("seller") val seller: String,
    @SerializedName("cartMethod") val cartMethod: String
) : Serializable

data class RepImageResult(
    @SerializedName("id") val id: Long,
    @SerializedName("imgName") val imgName: String,
    @SerializedName("oriImgName") val oriImgName: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("repImgUrl") val repImgUrl: String
) : Serializable