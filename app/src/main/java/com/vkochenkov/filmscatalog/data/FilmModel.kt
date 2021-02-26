package com.vkochenkov.filmscatalog.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmModel(
    val title: String,
    val description: String,
    val imageUrl: String
): Parcelable {
    var selected = false
    var liked = false
}