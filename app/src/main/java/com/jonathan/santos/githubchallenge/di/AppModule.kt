package com.jonathan.santos.githubchallenge.di

import com.jonathan.santos.githubchallenge.MainViewModel
import com.jonathan.santos.githubchallenge.data.repository.GitHubRepository
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel {
        MainViewModel(
            gitHubRepository = get()
        )
    }

    single {
        GitHubRepository(
            get()
        )
    }

    single<Picasso> {
        val builder = Picasso.Builder(androidContext())
        builder.build()
    }
}