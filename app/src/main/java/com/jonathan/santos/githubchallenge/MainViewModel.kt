package com.jonathan.santos.githubchallenge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonathan.santos.githubchallenge.data.repository.GitHubRepository
import com.jonathan.santos.githubchallenge.model.Repository
import com.jonathan.santos.githubchallenge.model.RepositoryList
import com.jonathan.santos.githubchallenge.data.services.GithubService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    val gitHubRepository: GitHubRepository
): ViewModel() {

    private val _repositoryListLiveData = MutableLiveData<List<Repository>>()
    val repositoryListLiveData = _repositoryListLiveData

    private val _errorGettingRepositoryListLiveData = MutableLiveData<Throwable>()
    val errorGettingRepositoryListLiveData = _errorGettingRepositoryListLiveData

    fun getGithubRepositories(pageNumber: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = gitHubRepository.getGithubInfo(pageNumber)
            if (response.isSuccessful) {
                _repositoryListLiveData.postValue(response.body()?.list)
            } else {
                _errorGettingRepositoryListLiveData.postValue(Throwable(response.errorBody().toString()))
            }
        }
    }
}