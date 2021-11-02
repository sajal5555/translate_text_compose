package com.sonyassignment.repo

import kotlinx.coroutines.flow.Flow
import okhttp3.Response
import okhttp3.ResponseBody


interface ITranslationDataSource {
    suspend fun downloadFile(): Flow<ResponseBody>
}
