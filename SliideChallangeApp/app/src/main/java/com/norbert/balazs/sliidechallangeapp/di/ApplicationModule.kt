package com.norbert.balazs.sliidechallangeapp.di

import android.content.Context
import com.norbert.balazs.sliidechallangeapp.common.API_KEY
import com.norbert.balazs.sliidechallangeapp.common.BASE_USERS_DB_URL
import com.norbert.balazs.sliidechallangeapp.data.remote.UsersDbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    private const val READ_TIMEOUT = 30
    private const val WRITE_TIMEOUT = 30
    private const val CONNECTION_TIMEOUT = 10
    private const val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB

    @Provides
    @Singleton
    @Named("HeaderInterceptor")
    fun provideHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $API_KEY")
            chain.proceed(requestBuilder.build())
        }
    }

    @Provides
    @Singleton
    @Named("HttpCache")
    internal fun provideCache(
        @ApplicationContext context: Context
    ): Cache {
        val httpCacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
        return Cache(httpCacheDirectory, CACHE_SIZE_BYTES)
    }

    @Provides
    @Singleton
    @Named("OkHttpClient")
    fun provideOkHttpClient(
        @Named("HeaderInterceptor") headerInterceptor: Interceptor,
        @Named("HttpCache") cache: Cache
    ): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
            .apply {
                connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
                readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
                writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
                cache(cache)
                addInterceptor(headerInterceptor)
            }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    @Named("UsersDbRetrofit")
    fun provideUsersRetrofit(
        @Named("OkHttpClient") client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_USERS_DB_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("UsersDbApi")
    fun provideUsersDbApi(
        @Named("UsersDbRetrofit") retrofit: Retrofit
    ): UsersDbApi {
        return retrofit.create(UsersDbApi::class.java)
    }
}