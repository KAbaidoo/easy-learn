package com.example.easylearn.ui.course

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat


import androidx.media3.common.MediaItem
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easylearn.data.db.entities.LessonDb
import com.example.easylearn.databinding.ActivityCourseBinding

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CourseActivity : AppCompatActivity(), CourseLessonAdapter.OnItemClickListener {

    private val viewModel: CourseViewModel by viewModels()
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    private lateinit var binding: ActivityCourseBinding
    private var playlist: List<LessonDb>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val courseLessonAdapter = CourseLessonAdapter(this)





        binding.apply {
            //            Play media
            lessonDbRecyclerView.apply {
                adapter = courseLessonAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }

            viewModel.courseWithLessons.observe(this@CourseActivity) {
                courseLessonAdapter.submitList(it.lessons)
                playlist = it.lessons
                playlist?.let {
                    initializePlayer()
                }
            }

        }
    }

    private fun initializePlayer() {

        player = ExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                binding.playerView.player = exoPlayer
                playlist?.forEach {
                    val mediaItem = MediaItem.fromUri(it.src)
                    exoPlayer.addMediaItem(mediaItem)
                }
                exoPlayer.playWhenReady = playWhenReady
                exoPlayer.seekTo(currentItem, playbackPosition)
                exoPlayer.prepare()

            }

    }



    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
//        hideSystemUi()
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
            Log.d(TAG,"$currentItem")
        }
    }


    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.playerView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

    override fun onItemClick(lessonDb: LessonDb) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TAG = "CourseActivity"
    }


}






