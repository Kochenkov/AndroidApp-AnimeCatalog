package com.vkochenkov.filmscatalog.model.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Films")
data class Film(
    @PrimaryKey val serverName: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "startDate") val startDate: String,
    @ColumnInfo(name = "ageRating") val ageRating: String,
    @ColumnInfo(name = "episodeCount") val episodeCount: Int,
    @ColumnInfo(name = "averageRating") val averageRating: Double,
    @ColumnInfo(name = "isLiked") var liked: Boolean,
    @ColumnInfo(name = "isNotify") var notify: Boolean
): Parcelable