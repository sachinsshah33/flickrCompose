package com.example.flickr.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.flickr.repo.network.IService
import com.example.flickr.repo.network.models.PhotoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotosPagingDataSource(
    private val service: IService,
    private val photosRequestModel: PhotosRequestModel
) : PagingSource<Int, PhotoResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val page = params.key ?: 1
                try {
                    val response = service.photos(photosRequestModel.apply {
                        this.page = page
                    })

                    if (response.body() != null) {
                        LoadResult.Page(
                            data = response.body()!!.photos.photo,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (page < response.body()!!.photos.pages) response.body()!!.photos.page.plus(
                                1
                            ) else null
                        )
                    } else {
                        LoadResult.Error(Throwable())
                        //todo maybe throw an error with more meaningful error message
                    }
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }

            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PhotoResponse>) = state.anchorPosition
}