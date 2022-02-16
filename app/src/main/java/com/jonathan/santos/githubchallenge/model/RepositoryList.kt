package com.jonathan.santos.githubchallenge.model

import com.google.gson.annotations.SerializedName

data class RepositoryList(
    @SerializedName("items")
    val list: List<Repository>
    )
