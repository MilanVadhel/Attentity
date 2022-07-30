package com.smvs.gkm.di.module

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.smvs.gkm.BuildConfig
import com.smvs.gkm.common.interceptor.AccessTokenInterceptor
import com.smvs.gkm.common.interceptor.ApiInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const val HTTP_REQUEST_TIMEOUT = 2L
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptors: ArrayList<Interceptor>): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .followRedirects(false)
            .callTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.MINUTES)
            .connectTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(HTTP_REQUEST_TIMEOUT, TimeUnit.MINUTES)

        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }

        return clientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideInterceptors(
        apiInterceptor: ApiInterceptor,
        accessTokenInterceptor: AccessTokenInterceptor
    ): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        interceptors.add(loggingInterceptor)
        interceptors.add(accessTokenInterceptor)
        interceptors.add(apiInterceptor)
        return interceptors
    }

}