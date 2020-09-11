package com.example.systemtaskvennilag.model


import com.google.gson.annotations.SerializedName

data class prices(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("timestamp")
    val timestamp: Long
)