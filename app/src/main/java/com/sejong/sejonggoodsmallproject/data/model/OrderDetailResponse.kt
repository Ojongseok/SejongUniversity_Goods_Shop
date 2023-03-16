package com.sejong.sejonggoodsmallproject.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrderDetailResponse(
    @SerializedName("buyerName") val buyerName: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("orderMethod") val orderMethod: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("address") val address: OdrAddress,
    @SerializedName("orderItems") val orderItems: List<OdrOrderItems>,
) : Serializable

data class OdrAddress(
    @SerializedName("mainAddress") val mainAddress: String,
    @SerializedName("detailAddress") val detailAddress: String,
    @SerializedName("zipcode") val zipcode: String
) : Serializable

data class OdrOrderItems(
    @SerializedName("color") val color: String,
    @SerializedName("size") val size: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("price") val price: Int,
    @SerializedName("seller") val seller: OdrSeller
) : Serializable

data class OdrSeller(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("accountHolder") val accountHolder: String,
    @SerializedName("bank") val bank: String,
    @SerializedName("account") val account: String,
    @SerializedName("method") val method: String
) : Serializable