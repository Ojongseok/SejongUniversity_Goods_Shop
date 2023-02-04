package com.example.sejonggoodsmallproject.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("token") val token: String,
    @SerializedName("email") val email: String
)
