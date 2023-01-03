package com.example.youtubeapirepitition.utils

import com.example.youtubeapirepitition.models.YouTubeData

sealed class YouTubeResource {

    object Loading : YouTubeResource()

    data class Success(val youTubeData: YouTubeData) : YouTubeResource()

    data class Error(val message: String) : YouTubeResource()

}