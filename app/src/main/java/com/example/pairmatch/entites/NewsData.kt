package com.example.pairmatch.entites

import com.google.errorprone.annotations.Keep

@Keep
data class NewsData(
    var news : ArrayList<News> = arrayListOf()
)
