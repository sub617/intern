package com.example.internship2.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MyCoordinatesModel(

    @SerializedName("lat")
    @Expose
    var lat: String? = null,

    @SerializedName("lon")
    @Expose
    var lon:String? = null,
    )
