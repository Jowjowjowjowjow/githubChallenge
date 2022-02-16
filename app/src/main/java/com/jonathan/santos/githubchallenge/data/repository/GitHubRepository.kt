package com.jonathan.santos.githubchallenge.data.repository

import com.jonathan.santos.githubchallenge.data.services.GithubService
import com.jonathan.santos.githubchallenge.model.RepositoryList
import retrofit2.Response

class GitHubRepository(private val githubService: GithubService) {

    suspend fun getGithubInfo(pageNumber: Int): Response<RepositoryList> = githubService.getGithubRepositories(pageNumber = pageNumber)
}