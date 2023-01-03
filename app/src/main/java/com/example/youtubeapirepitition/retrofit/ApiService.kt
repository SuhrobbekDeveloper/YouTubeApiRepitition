package com.example.youtubeapirepitition.retrofit

import com.example.youtubeapirepitition.models.YouTubeData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun getChannelVideos(
        @Query("key") key: String = "AIzaSyDmGiwohN1UXFds_kE_zZkOt-6a5kIKmf8",
        @Query("channelId") channelId: String = "UCb6dc_1RkwR7G3anuO6N8Kg",
        @Query("part") part: String = "snippet,id",
        @Query("order") order: String = "date",
        @Query("maxResults") maxResults: Int = 20
    ): Response<YouTubeData>
}