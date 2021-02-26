package com.vkochenkov.filmscatalog.data.api.pojo

import com.google.gson.annotations.SerializedName

data class Attributes(
    @SerializedName("posterImage") val posterImage: PosterImage,
    @SerializedName("slug") val title: String,
    @SerializedName("description") val description: String,
)
