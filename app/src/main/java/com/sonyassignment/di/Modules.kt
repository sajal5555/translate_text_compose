package com.sonyassignment.di

import android.content.Context
import com.sonyassignment.repo.TranslationRepo
import com.sonyassignment.webservice.ApiService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit


fun getModules() =
    listOf(
        networkModule,
        repositoryModule
    )


private val repositoryModule = module {
    single { TranslationRepo(get()) }
}


private val networkModule = module {
    fun provideNetworkHelper(context: Context) = NetworkHelper(context)

    fun provideRetrofit(): Retrofit = RepositoryHelper().repositoryBuilder()

    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    single { provideRetrofit() }
    single { provideApiService(get()) }
    single { provideNetworkHelper(androidContext()) }

}