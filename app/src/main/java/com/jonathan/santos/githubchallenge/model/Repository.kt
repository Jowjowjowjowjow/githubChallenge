package com.jonathan.santos.githubchallenge.model

import com.google.gson.annotations.SerializedName

data class Repository(
    @SerializedName("name")
    val name: String,

    @SerializedName("stargazers_count")
    val numberOfStars: Int,

    @SerializedName("forks")
    val numberOfForks: Int,

    @SerializedName("owner")
    val author: Author
)
