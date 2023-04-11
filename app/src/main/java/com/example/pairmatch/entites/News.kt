package com.example.pairmatch.entites

import androidx.annotation.Keep

@Keep
data class News(
    var header: String? = null,
    var date: String? = null,
    var text: String? = null,
    var logo: String? = null
)
