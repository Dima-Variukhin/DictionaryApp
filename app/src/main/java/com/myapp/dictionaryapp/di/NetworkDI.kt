package com.myapp.dictionaryapp.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.myapp.dictionaryapp.core.ConnectionManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkDI {
    private const val BASE_URL = "https://api.dictionaryapi.dev/"
    private lateinit var retrofit: Retrofit
    lateinit var connectionManager: ConnectionManager

    fun initialize(app: Application) {
        retrofit = getRetrofit(getOkHttpClient(getInterceptor()))
        connectionManager = ConnectionManager.Base(getConnectivityManager(app))
    }

    private fun getConnectivityManager(context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    fun <T> getService(className: Class<T>): T = retrofit.create(className)

    private fun getRetrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

    private fun getOkHttpClient(interceptor: Interceptor) =
        OkHttpClient().newBuilder()
            .addInterceptor(interceptor)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .build()

    private fun getInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
}