package com.example.flickr.di

import com.example.flickr.repo.Repo
import com.example.flickr.useCases.PhotosUseCase
import com.example.flickr.useCases.UsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Singleton
    @Provides
    fun provideUsersUseCase(repository: Repo) = UsersUseCase(repository)

    @Singleton
    @Provides
    fun providePhotosUseCase(repository: Repo) = PhotosUseCase(repository)
}