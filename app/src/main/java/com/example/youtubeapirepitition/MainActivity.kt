package com.example.youtubeapirepitition

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.youtubeapirepitition.adapters.YouTubeAdapter
import com.example.youtubeapirepitition.databinding.ActivityMainBinding
import com.example.youtubeapirepitition.utils.NetworkHelper
import com.example.youtubeapirepitition.utils.YouTubeResource
import com.example.youtubeapirepitition.utils.hide
import com.example.youtubeapirepitition.utils.show
import com.example.youtubeapirepitition.viewModels.ViewmodelFactory
import com.example.youtubeapirepitition.viewModels.YouTubeViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var binding: ActivityMainBinding
    private lateinit var youTubeViewModel: YouTubeViewModel
    private lateinit var networkHelper: NetworkHelper
    private lateinit var job: Job
    private val TAG = "MainActivity"
    private lateinit var youTubeAdapter: YouTubeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        job = Job()
        networkHelper = NetworkHelper(this)

        youTubeViewModel =
            ViewModelProvider(this, ViewmodelFactory(networkHelper))[YouTubeViewModel::class.java]
        youTubeAdapter = YouTubeAdapter(object : YouTubeAdapter.OnItemClickListener {
            override fun onItemClick(videoId: String) {
                val intent = Intent(this@MainActivity, VideoActivity::class.java)
                intent.putExtra("video_id", videoId)
                startActivity(intent)
            }
        })
        binding.rv.adapter = youTubeAdapter
        binding.progressBar.show()
        launch {
            youTubeViewModel.getStateVideos().collect {
                withContext(Dispatchers.Main) {
                    when (it) {
                        is YouTubeResource.Loading -> {
                            binding.progressBar.show()
                            Log.d(TAG, "onCreate: Loading..............")
                        }
                        is YouTubeResource.Error -> {
                            binding.progressBar.hide()
                            Log.d(TAG, "onCreate: ${it.message}")
                        }
                        is YouTubeResource.Success -> {
                            binding.progressBar.hide()
                            Log.d(TAG, "onCreate: ${it.youTubeData}")
                            youTubeAdapter.submitList(it.youTubeData.items)
                        }
                    }
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = job
}