package com.example.sejonggoodsmallproject.data.model

import com.google.gson.annotations.SerializedName

data class FavoriteResponse(
    @SerializedName("memberId") val memberId: Long,
    @SerializedName("itemId") val itemId: Long,
    @SerializedName("scrapCount") val scrapCount: Int
)