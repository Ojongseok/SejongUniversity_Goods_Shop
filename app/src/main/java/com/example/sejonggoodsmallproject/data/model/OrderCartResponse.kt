package com.example.sejonggoodsmallproject.data.model

import com.google.gson.annotations.SerializedName

data class OrderCartResponse(
    @SerializedName("buyerName") val buyerName: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("orderMethod") val orderMethod: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("address") val address: OcrAddress,
    @SerializedName("orderItems") val orderItems: List<OdcOrderItems>,
    @SerializedName("seller") val seller: OcrSeller
)

data class OcrAddress(
    @SerializedName("mainAddress") val mainAddress: String,
    @SerializedName("detailAddress") val detailAddress: String,
    @SerializedName("zipcode") val zipcode: String
)

data class OdcOrderItems(
    @SerializedName("color") val color: String,
    @SerializedName("size") val size: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("price") val price: Int
)

data class OcrSeller(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("accountHolder") val accountHolder: String,
    @SerializedName("bank") val bank: String,
    @SerializedName("account") val account: String,
    @SerializedName("method") val method: String
)