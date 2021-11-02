package com.sonyassignment.repo

import android.util.Log
import com.sonyassignment.webservice.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Response
import okhttp3.ResponseBody
import org.koin.java.KoinJavaComponent.getKoin

class TranslationDataSource(
    private val apiService: ApiService = getKoin().get()
) : ITranslationDataSource {

    override suspend fun downloadFile(): Flow<ResponseBody> {
        return flow {
            val response = apiService.downloadFile()
            emit(response)
        }
    }
}