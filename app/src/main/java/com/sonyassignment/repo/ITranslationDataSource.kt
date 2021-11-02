package com.sonyassignment.repo

import com.sonyassignment.webservice.RepoResult
import kotlinx.coroutines.flow.Flow


interface ITranslationDataSource {
    suspend fun downloadFile(url: String): Flow<RepoResult<*>?>
}
