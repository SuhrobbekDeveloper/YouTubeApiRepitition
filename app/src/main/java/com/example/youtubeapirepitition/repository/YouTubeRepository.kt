package com.example.youtubeapirepitition.repository

import com.example.youtubeapirepitition.retrofit.ApiService
import kotlinx.coroutines.flow.flow

class YouTubeRepository(private val apiService: ApiService) {
    suspend fun getVideos() = flow { emit(apiService.getChannelVideos()) }
}