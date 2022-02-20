package com.jonathan.santos.githubchallenge

import androidx.lifecycle.Observer
import com.jonathan.santos.githubchallenge.data.repository.GitHubRepository
import com.jonathan.santos.githubchallenge.model.Author
import com.jonathan.santos.githubchallenge.model.Repository
import com.jonathan.santos.githubchallenge.model.RepositoryList
import io.mockk.*
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response

@ExtendWith(InstantExecutorExtension::class)
internal class MainViewModelTest {
    private lateinit var githubRepositoryMock: GitHubRepository
    private lateinit var subject: MainViewModel

    private lateinit var repositoryListLiveDataObserver: Observer<List<Repository>>
    private lateinit var errorGettingRepositoryListLiveDataObserver: Observer<Throwable>

    @BeforeEach
    fun beforeEach() {
        githubRepositoryMock = mockk()
        repositoryListLiveDataObserver = mockk { every { onChanged(any()) } just Runs }
        errorGettingRepositoryListLiveDataObserver = mockk { every { onChanged(any()) } just Runs }
        subject = MainViewModel(
            githubRepositoryMock
        ).apply {
            repositoryListLiveData.observeForever(repositoryListLiveDataObserver)
            errorGettingRepositoryListLiveData.observeForever(
                errorGettingRepositoryListLiveDataObserver
            )
        }
    }

    @Test
    fun `When calling getGithubRepositories Should set value for repositoryListLiveData`() =
        runBlocking {
            val repositoryListStub = RepositoryList(
                listOf(
                    Repository(
                        "GithubChallenge",
                        12,
                        15,
                        Author(
                            "Autor",
                            "https://img.elo7.com.br/product/zoom/34A8A81/caneca-graphic-design-is-my-passion-cachorro.jpg"
                        )
                    )
                )
            )

            coEvery { githubRepositoryMock.getGithubInfo(any()) } returns Response.success(
                repositoryListStub
            )

            subject.getGithubRepositories(1)

            verify {
                repositoryListLiveDataObserver.onChanged(repositoryListStub.list)
            }
            assertEquals(repositoryListStub.list, subject.repositoryListLiveData.value)
        }

    @Test
    fun `When fail calling getGithubRepositories Should set value for errorGettingRepositoryListLiveData`() =
        runBlocking {

            val response = Response.error<RepositoryList>(400, ResponseBody.create(MediaType.get("application/json"), RepositoryList(listOf()).toString()))
            coEvery { githubRepositoryMock.getGithubInfo(any()) } returns response

            subject.getGithubRepositories(1)

            verify {
                errorGettingRepositoryListLiveDataObserver.onChanged(any())
            }
        }
}