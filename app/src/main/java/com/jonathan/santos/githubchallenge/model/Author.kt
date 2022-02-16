package com.jonathan.santos.githubchallenge.model

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("login")
    val name: String,

    @SerializedName("avatar_url")
    val picture: String
)