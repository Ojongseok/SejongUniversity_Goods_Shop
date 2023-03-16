package com.sejong.sejonggoodsmallproject.data.model

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("username") val username: String?,
    @SerializedName("birth") val birth: String
)