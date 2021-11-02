package com.sonyassignment.di

import com.google.gson.GsonBuilder
import com.sonyassignment.webservice.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


private const val CONNECT_CONNECT_TIMEOUT: Long = 10000L
private const val READ_CONNECT_TIMEOUT: Long = 30000L
private const val WRITE_CONNECT_TIMEOUT: Long = 30000L

class RepositoryHelper {
    fun repositoryBuilder(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient()
                        .create()
                )
            )
            .build()

    private fun okHttpClient() = OkHttpClient.Builder().addInterceptor { chain ->
        val headers = HashMap<String, String?>()
        headers["content-type"] = "application/json"

        val original = chain.request()
        // Request customization: add request headers
        val requestBuilder = original.newBuilder()

        for ((key, value) in headers) {
            if (value != null) {
                requestBuilder.header(key, value)
            }
        }
        requestBuilder.method(original.method, original.body)
        val request = requestBuilder.build()

        chain.proceed(request)

    }.apply {
        connectTimeout(CONNECT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        readTimeout(READ_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        writeTimeout(WRITE_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)

    }.build()
}