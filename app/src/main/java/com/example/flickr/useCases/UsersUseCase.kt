package com.example.flickr.useCases

import com.example.flickr.repo.IRepo

class UsersUseCase(private val repository: IRepo) : IUsersUseCase {
    override suspend fun findByUsername(username: String) = repository.findByUsername(username)
}