package com.example.flickr.useCases

import com.example.flickr.repo.IRepo
import com.example.flickr.repo.PhotosRequestModel

class PhotosUseCase(private val repository: IRepo) : IPhotosUseCase {
    override fun photos(photosRequestModel: PhotosRequestModel) =
        repository.photos(photosRequestModel)
}