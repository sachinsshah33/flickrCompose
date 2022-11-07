package com.example.flickr.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.flickr.repo.PhotosRequestModel
import com.example.flickr.ui.extensions.sanitizeTag
import com.example.flickr.ui.models.PhotoLocal
import com.example.flickr.useCases.PhotosUseCase
import com.example.flickr.useCases.UsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


const val STATE_KEY_QUERY = "state.query.key"

@HiltViewModel
class ViewModel @Inject constructor(
    private val usersUseCase: UsersUseCase, //IPhotosUseCase
    private val photosUseCase: PhotosUseCase, //IPhotosUseCase
    private val savedStateHandle: SavedStateHandle? = null,
) : ViewModel() {

    init {
        savedStateHandle?.get<String>(STATE_KEY_QUERY)?.let {
            setQuery(it)
        }
    }

    val usernameQuery = mutableStateOf("")
    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    private fun setQuery(query: String) {
        this.usernameQuery.value = query
        changeOwnerId(query)
        savedStateHandle?.set(STATE_KEY_QUERY, query)
    }


    fun photos(photosRequestModel: PhotosRequestModel) =
        photosUseCase.photos(photosRequestModel).cachedIn(viewModelScope)

    val photosRequestModelCompose: MutableState<PhotosRequestModel> =
        mutableStateOf(PhotosRequestModel())
    /*val tags get() = photosRequestModel?.tags
    val ownerId get() = photosRequestModel?.ownerId*/

    private val _photosRequestModel = MutableLiveData(PhotosRequestModel())
    val photosRequestModel: LiveData<PhotosRequestModel>
        get() = _photosRequestModel


    private val _uiState = MutableStateFlow(PhotosRequestModel())
    val uiState: StateFlow<PhotosRequestModel> = _uiState.asStateFlow()


    fun addTag(tag: String) {
        val sanitizedTag = tag.sanitizeTag
        _photosRequestModel.value?.tags?.apply {
            if (!contains(sanitizedTag)) add(sanitizedTag)
        }
        _photosRequestModel.value = _photosRequestModel.value
        //https://stackoverflow.com/a/52287357


        //fixme, this isnt updating
        this@ViewModel.photosRequestModelCompose.value.tags.apply {
            if (!contains(sanitizedTag)) add(sanitizedTag)
        }
        this@ViewModel.photosRequestModelCompose.value =
            this@ViewModel.photosRequestModelCompose.value.copy()
    }

    fun removeTag(tag: String) {
        this@ViewModel.photosRequestModelCompose.value.tags.remove(tag.sanitizeTag)
        this@ViewModel.photosRequestModelCompose.value =
            this@ViewModel.photosRequestModelCompose.value.copy()


        _photosRequestModel.value?.tags?.remove(tag.sanitizeTag)
        _photosRequestModel.value = _photosRequestModel.value
    }

    fun changeOwnerId(username: String?) {
        if (username.isNullOrEmpty()) {

            this@ViewModel.photosRequestModelCompose.value =
                this@ViewModel.photosRequestModelCompose.value.copy().apply {
                    ownerId = null //for compose
                }

            _photosRequestModel.value?.ownerId = null
            _photosRequestModel.value = _photosRequestModel.value
        } else {
            viewModelScope.launch {
                val response = try {
                    usersUseCase.findByUsername(username.trim())
                } catch (e: Exception) {
                    null
                }

                this@ViewModel.photosRequestModelCompose.value =
                    this@ViewModel.photosRequestModelCompose.value.copy().apply {
                        ownerId = response?.body()?.user?.nsid //for compose
                    }

                _photosRequestModel.value?.ownerId = response?.body()?.user?.nsid
                _photosRequestModel.value = _photosRequestModel.value
            }
        }
    }

    fun changeTagMode(all: Boolean) {
        this@ViewModel.photosRequestModelCompose.value.setTagMode(all)
        this@ViewModel.photosRequestModelCompose.value =
            this@ViewModel.photosRequestModelCompose.value.copy()


        _photosRequestModel.value?.setTagMode(all)
        _photosRequestModel.value = _photosRequestModel.value
    }


    private val _photosUIState = MutableLiveData<PhotosUIState>()
    val photosUIState: LiveData<PhotosUIState>
        get() = _photosUIState

    sealed class PhotosUIState {
        object Loading : PhotosUIState()
        data class Data(val tasks: PagingData<PhotoLocal>) : PhotosUIState()
        data class Error(val error: String) : PhotosUIState()
    }

    fun fetchPhotos(photosRequestModel: PhotosRequestModel) {
        viewModelScope.launch {
            try {
                _photosUIState.value = PhotosUIState.Loading
                photosUseCase.photos(photosRequestModel)
                    .cachedIn(viewModelScope)
                    .collectLatest {
                        _photosUIState.value = PhotosUIState.Data(it)
                    }
            } catch (e: Exception) {
                _photosUIState.value = PhotosUIState.Error("")
            }
        }
    }
}