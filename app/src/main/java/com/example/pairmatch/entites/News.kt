package com.example.pairmatch.entites

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class News(
    var header: String? = null,
    var date: String? = null,
    var text: String? = null,
    var logo: String? = null
): Parcelable
