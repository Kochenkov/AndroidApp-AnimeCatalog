package com.vkochenkov.filmscatalog.model.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Films")
data class Film(
    @PrimaryKey
    val serverName: String,
    val title: String,
    val description: String,
    val imageUrl: String
): Parcelable {
    var liked = false
}