package com.example.pairmatch.entites

import androidx.annotation.Keep

@Keep
data class NewsData(
    var news : ArrayList<News> = arrayListOf()
)

