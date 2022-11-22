package com.example.easylearn.ui.course

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle


import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easylearn.R
import com.example.easylearn.data.db.entities.LessonDb

import com.example.easylearn.databinding.FragmentCourseBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CourseFragment : Fragment(R.layout.fragment_course), CourseLessonAdapter.OnItemClickListener,
    Player.Listener {


    private val viewModel: CourseViewModel by viewModels()
    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L

    private lateinit var binding: FragmentCourseBinding
    private var playlist: List<LessonDb>? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCourseBinding.bind(view)
        val courseLessonAdapter = CourseLessonAdapter(this)





        binding.apply {
            //            Play media
            lessonDbRecyclerView.apply {
                adapter = courseLessonAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewModel.courseLessons.observe(viewLifecycleOwner) {
                courseLessonAdapter.submitList(it)
                playlist = it
                playlist?.let {
                    initializePlayer()
                }
                player?.addListener(this@CourseFragment)


            }
        }

    }




    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        viewModel.currentIndex.value = player?.currentMediaItemIndex
        viewModel.setCompleted()
    }

    private fun initializePlayer() {

        player = ExoPlayer.Builder(requireContext())
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

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()

        }

    }


    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }

    }

//    @SuppressLint("InlinedApi")
//    private fun hideSystemUi() {
//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        WindowInsetsControllerCompat(window, binding.playerView).let { controller ->
//            controller.hide(WindowInsetsCompat.Type.systemBars())
//            controller.systemBarsBehavior =
//                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//        }
//    }

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
        private const val TAG = "CourseFragment"
    }

}






