package com.sejong.sejonggoodsmallproject.data.model

import com.google.gson.annotations.SerializedName

data class FavoriteListResponse(
    @SerializedName("itemId") val itemId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Int,
    @SerializedName("repImage") val repImage: RepImage
)

data class RepImage(
    @SerializedName("id") val id: Long,
    @SerializedName("imgName") val imgName: String,
    @SerializedName("oriImgName") val oriImgName: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("repImgUrl") val repImgUrl: String
)
