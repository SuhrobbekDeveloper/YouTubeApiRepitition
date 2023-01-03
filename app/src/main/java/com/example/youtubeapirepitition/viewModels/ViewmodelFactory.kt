package com.example.youtubeapirepitition.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeapirepitition.utils.NetworkHelper

class ViewmodelFactory(private val networkHelper: NetworkHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YouTubeViewModel::class.java)) {
            return YouTubeViewModel(networkHelper) as T
        }
        throw Exception("Error")
    }
}