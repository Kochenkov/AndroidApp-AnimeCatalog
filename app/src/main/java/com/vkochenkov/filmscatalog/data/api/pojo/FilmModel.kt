package com.vkochenkov.filmscatalog.data.api.pojo

import com.google.gson.annotations.SerializedName

data class FilmModel(
    @SerializedName("attributes") val attributes: Attributes
)