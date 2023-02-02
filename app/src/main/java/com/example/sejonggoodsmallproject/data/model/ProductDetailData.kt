package com.example.sejonggoodsmallproject.data.model

data class ProductDetailData(
    val id: Long,
    val title: String,
    val price: Int,
    val description: String?,
    val color: String?,
    val size: String,
)