package com.example.sejonggoodsmallproject.data.model

import android.os.Parcelable
import java.io.Serializable

data class OptionPicked(
    val option1: String?,
    val option2: String?,
    val quantity: Int
): Serializable
