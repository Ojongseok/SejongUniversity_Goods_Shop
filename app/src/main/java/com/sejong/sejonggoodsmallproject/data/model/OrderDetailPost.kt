package com.sejong.sejonggoodsmallproject.data.model

data class OrderDetailPost(
    val buyerName: String,
    val phoneNumber: String,
    val orderMethod: String,
    val address: OdpAddress?,
    val orderItems: List<OdpOrderItems>,
    val deliveryRequest: String?
)

data class OdpAddress(
    val mainAddress: String?,
    val detailAddress: String?,
    val zipcode: String?
)

data class OdpOrderItems(
    val color: String?,
    val size: String?,
    val quantity: Int,
    val price: Int
)