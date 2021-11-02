package com.sonyassignment.webservice

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url


interface ApiService {
    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl: String): Response<RepoResult<String>>
}