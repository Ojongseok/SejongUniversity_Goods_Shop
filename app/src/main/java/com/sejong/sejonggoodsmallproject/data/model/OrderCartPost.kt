package com.sejong.sejonggoodsmallproject.data.model

data class OrderCartPost(
    val buyerName: String,
    val phoneNumber: String,
    val orderMethod: String,
    val address: OcpAddress?,
    val cartIdList: List<Long>,
    val deliveryRequest: String?
)

data class OcpAddress(
    val mainAddress: String?,
    val detailAddress: String?,
    val zipcode: String?
)
