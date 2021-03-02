package com.vkochenkov.filmscatalog.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val title: String,
    val description: String,
    val imageUrl: String
): Parcelable {
    var selected = false
    var liked = false
}