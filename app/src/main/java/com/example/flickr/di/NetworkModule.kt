package com.example.flickr.di

import android.content.Context
import android.util.Log
import com.example.flickr.BuildConfig
import com.example.flickr.repo.network.Endpoints
import com.example.flickr.repo.network.NetworkConstants
import com.example.flickr.repo.network.Service
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context) = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addNetworkInterceptor(Interceptor {
            val original = it.request()
            val originalHttpUrl = original.url
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("format", "json")
                .addQueryParameter("nojsoncallback", "1") //todo, might be able to move this
                .apply {
                    val isPhotosAPIMethod = NetworkConstants.APIMethod.PhotosAPIMethod.values.any {
                        originalHttpUrl.toString().contains(it)
                    }
                    if (isPhotosAPIMethod) {
                        addQueryParameter("media", "photos")
                        addQueryParameter("extras", "owner_name,tags")
                    }
                }
                .addQueryParameter(
                    "api_key",
                    "7621cc4cb5714bd49a2ac518c04535e6"
                ) //todo, find secure way to provide this, i.e. user authentication and then provide the key from server
                .build()
            val requestBuilder = original.newBuilder().url(url)
            val request = requestBuilder.build()
            it.proceed(request)
        })
        /*.addNetworkInterceptor(Interceptor {
            val requestBuilder = it.request().newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            it.proceed(requestBuilder.build())
        })*/
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor {
                    Log.e("retrofit", it)
                }.apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                })
            }
        }
        .build()


    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ) =
        Retrofit.Builder()
            .baseUrl("https://api.flickr.com/services/rest/")
            .client(okHttpClient)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter())
                        .add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()

    @Singleton
    @Provides
    fun provideEndpoints(
        retrofit: Retrofit
    ) = retrofit.create(Endpoints::class.java)

    @Singleton
    @Provides
    fun provideService(
        endpoints: Endpoints
    ) = Service(endpoints)
}