package com.example.sejonggoodsmallproject.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductDetailResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("price") val price: Int,
    @SerializedName("description") val description: String?,
    @SerializedName("color") val color: String?,
    @SerializedName("size") val size: String,
    @SerializedName("itemInfos") val detailImg : List<imgProductDetailInfoResult>,
    @SerializedName("itemImages") val img: List<imgProductDetailResult>,
    @SerializedName("seller") val seller: Seller
): Serializable

data class Seller(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("method") val method: String
): Serializable

data class imgProductDetailResult(
    @SerializedName("id") val id: Long,
    @SerializedName("imgName") val imgName: String,
    @SerializedName("oriImgName") val oriImgName: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("repImgUrl") val repImgUrl: String
)

data class imgProductDetailInfoResult(
    @SerializedName("infoUrl") val infoUrl: String
) : Serializable