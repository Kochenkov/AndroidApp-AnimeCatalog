package com.vkochenkov.filmscatalog.data.api.pojo

import com.google.gson.annotations.SerializedName

data class ResponseFromApi(
    @SerializedName("data") val data: List<FilmModel>
)