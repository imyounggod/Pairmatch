package com.example.pairmatch.entites

import androidx.annotation.Keep

@Keep
data class User(
    var user_uid: String? = null,
    var user_name: String? = null,
    var user_email: String? = null,
    var user_gender: String? = null,
    var user_balance: Double? = 0.0
)
