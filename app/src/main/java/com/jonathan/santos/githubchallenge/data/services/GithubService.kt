package com.jonathan.santos.githubchallenge.data.services

import com.jonathan.santos.githubchallenge.model.RepositoryList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @GET("repositories?q=language:kotlin")
    suspend fun getGithubRepositories(@Query("page") pageNumber: Int, @Query("sort") sort:String? = "stars"): Response<RepositoryList>
}