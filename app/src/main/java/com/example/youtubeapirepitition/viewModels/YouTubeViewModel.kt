package com.example.youtubeapirepitition.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeapirepitition.repository.YouTubeRepository
import com.example.youtubeapirepitition.retrofit.ApiClient
import com.example.youtubeapirepitition.utils.NetworkHelper
import com.example.youtubeapirepitition.utils.YouTubeResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class YouTubeViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val youtubeRepository = YouTubeRepository(ApiClient.apiService)
    private val stateFlow = MutableStateFlow<YouTubeResource>(YouTubeResource.Loading)

    init {
        getVideos()
    }

    private fun getVideos() {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                youtubeRepository.getVideos()
                    .catch {
                        stateFlow.value = YouTubeResource.Error(it.message.toString())
                    }.collect {
                        if (it.isSuccessful && it.body() != null) {
                            stateFlow.value = YouTubeResource.Success(it.body()!!)
                        } else {
                            stateFlow.value = YouTubeResource.Error(it.message())
                        }
                    }
            }
        } else {
            stateFlow.value = YouTubeResource.Error("No internet connection")
        }
    }

    fun getStateVideos(): StateFlow<YouTubeResource> {
        return stateFlow
    }
}