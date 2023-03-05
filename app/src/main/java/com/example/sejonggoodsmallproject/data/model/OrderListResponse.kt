package com.example.sejonggoodsmallproject.data.model

import com.google.gson.annotations.SerializedName

data class OrderListResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("buyerName") val buyerName: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("orderMethod") val orderMethod: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("address") val address: OrderListAddress,
    @SerializedName("status") val status: String,
    @SerializedName("orderItems") val orderItems: List<OrderListItems>,
    @SerializedName("seller") val seller: OrderListSeller
)

data class OrderListAddress(
    @SerializedName("mainAddress") val mainAddress: String,
    @SerializedName("detailAddress") val detailAddress: String,
    @SerializedName("zipcode") val zipcode: String
)

data class OrderListItems(
    @SerializedName("itemId") val itemId: Long,
    @SerializedName("color") val color: String,
    @SerializedName("size") val size: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("price") val price: Int
)

data class OrderListSeller(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("accountHolder") val accountHolder: String,
    @SerializedName("bank") val bank: String,
    @SerializedName("account") val account: String,
    @SerializedName("method") val method: String
)