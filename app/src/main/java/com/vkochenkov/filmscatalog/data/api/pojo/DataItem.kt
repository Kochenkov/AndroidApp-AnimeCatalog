package com.vkochenkov.filmscatalog.data.api.pojo

import com.google.gson.annotations.SerializedName

data class DataItem(
    @SerializedName("attributes") val attributes: Attributes
)