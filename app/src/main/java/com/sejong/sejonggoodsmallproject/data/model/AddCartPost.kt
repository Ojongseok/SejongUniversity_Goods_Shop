package com.sejong.sejonggoodsmallproject.data.model

data class AddCartPost(
    val quantity: String,
    val color: String?,
    val size: String?,
    val cartMethod: String
)