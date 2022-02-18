package com.jonathan.santos.githubchallenge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonathan.santos.githubchallenge.data.repository.GitHubRepository
import com.jonathan.santos.githubchallenge.model.Repository

class MainViewModel(
    val gitHubRepository: GitHubRepository
) : ViewModel() {

    private val _repositoryListLiveData = MutableLiveData<List<Repository>>()
    val repositoryListLiveData = _repositoryListLiveData

    private val _errorGettingRepositoryListLiveData = MutableLiveData<Throwable>()
    val errorGettingRepositoryListLiveData = _errorGettingRepositoryListLiveData

    suspend fun getGithubRepositories(pageNumber: Int) {
        val response = gitHubRepository.getGithubInfo(pageNumber)
        if (response.isSuccessful) {
            _repositoryListLiveData.postValue(response.body()?.list)
        } else {
            _errorGettingRepositoryListLiveData.postValue(
                Throwable(
                    response.errorBody().toString()
                )
            )
        }
    }

}