package com.sonyassignment.repo

import com.sonyassignment.webservice.ApiService
import com.sonyassignment.webservice.RepoResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.Koin

class TranslationDataSource(
    private val apiService: ApiService = Koin().get()
) : ITranslationDataSource {

    override suspend fun downloadFile(url: String): Flow<RepoResult<*>?> {
        return flow {
            val responseBody =
                apiService.downloadFile(url)
                    .body()
            emit(responseBody)
        }
    }
}