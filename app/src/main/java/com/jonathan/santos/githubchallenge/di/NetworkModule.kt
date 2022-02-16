package com.jonathan.santos.githubchallenge.di

import com.jonathan.santos.githubchallenge.data.services.GithubService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val githubApi = "https://api.github.com/search/"
val HTTP_LOGGING_QUALIFIER = named("HTTP_LOGGING_QUALIFIER")
val API_CACHE = named("API_CACHE")

val networkModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(githubApi)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<GithubService> {
        get<Retrofit>().create(GithubService::class.java)
    }

    single<OkHttpClient> {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(get(HTTP_LOGGING_QUALIFIER))
        builder.addInterceptor(get(API_CACHE))
        builder.build()
    }

    single<Interceptor>(HTTP_LOGGING_QUALIFIER) {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single<Interceptor>(API_CACHE){
        Interceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            val maxAge = 60 * 5
            originalResponse.newBuilder().header("Cache-Control", "public, max-age=$maxAge").build()
        }
    }
}