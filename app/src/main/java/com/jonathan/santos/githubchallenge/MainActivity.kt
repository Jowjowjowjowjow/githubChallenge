package com.jonathan.santos.githubchallenge

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.jonathan.santos.githubchallenge.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    lateinit var repositoryAdapter: RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupToolbar()
        setupRecyclerView()
        subscribeGetDataFromApi()
        subscribeErrorWhenGettingInfoFromApi()
    }

    private fun setupToolbar() {
        binding.toolbar.title = resources.getString(R.string.app_name)
        binding.toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(binding.toolbar)
    }

    private fun setupRecyclerView() {
        binding.recyclerViewRepositories.apply {
            repositoryAdapter = RepositoryAdapter()
            repositoryAdapter.setLoadNextPageFunction {
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.getGithubRepositories(it)
                }
                binding.progressBarLoadingMoreItems.visibility = View.VISIBLE
            }

            adapter = repositoryAdapter
        }

    }

    private fun subscribeGetDataFromApi() {
        viewModel.repositoryListLiveData.observe(this, { repositoryList ->
            (binding.recyclerViewRepositories.adapter as RepositoryAdapter).mergeItemsList(
                repositoryList
            )
            binding.progressBarLoadingMoreItems.visibility = View.GONE
        })
    }

    private fun subscribeErrorWhenGettingInfoFromApi() {
        viewModel.errorGettingRepositoryListLiveData.observe(this, {
            Toast.makeText(this@MainActivity, "Erro ao buscar dados - ${it.message}", Toast.LENGTH_LONG)
                .show()
            binding.progressBarLoadingMoreItems.visibility = View.GONE
        })
    }
}