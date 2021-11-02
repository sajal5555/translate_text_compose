package com.sonyassignment.webservice

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_CONNECT_TIMEOUT: Long = 10 * 1000
private const val READ_CONNECT_TIMEOUT: Long = 30 * 1000
private const val WRITE_CONNECT_TIMEOUT: Long = 30 * 1000

internal class AppRepositoryHelper() {
    val apiInterface: ApiService
    private var gson: Gson = GsonBuilder().create()


    init {
        val headers = HashMap<String, String?>()
        headers["content-type"] = "application/json"


        val retrofit = Retrofit.Builder()
            .client(getRetrofitHTTPClient(headers))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        apiInterface = retrofit.create(ApiService::class.java)
    }


    private fun getRetrofitHTTPClient(headers: HashMap<String, String?>): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
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

        }
        httpClient.connectTimeout(CONNECT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        httpClient.readTimeout(READ_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        httpClient.writeTimeout(WRITE_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)

        return httpClient.build()

    }
}


object ApiRepo {
    val apis: ApiService = AppRepositoryHelper().apiInterface
}