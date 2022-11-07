package com.example.flickr.di

import com.example.flickr.repo.Repo
import com.example.flickr.repo.network.Service
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun providePhotosRepository(service: Service) = Repo(service)
}