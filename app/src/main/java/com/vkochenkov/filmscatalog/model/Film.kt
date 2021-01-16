package com.vkochenkov.filmscatalog.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val titleRes: Int,
    val descriptionRes: Int,
    val imageRes: Int
): Parcelable