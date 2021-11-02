package com.sonyassignment.webservice

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming


interface ApiService {
    @Streaming
    @GET("trpjj0svhw3u2cui/TranslationFile.csv")
    suspend fun downloadFile(): ResponseBody
}