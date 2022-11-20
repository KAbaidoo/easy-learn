package com.example.easylearn.ui.course

import android.annotation.SuppressLint
import android.os.Bundle

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
    val videoUrl =
        "https://firebasestorage.googleapis.com/v0/b/bucketlist-10f69.appspot.com/o/video%2FPlaceholder_Video_oznr-1-poSU_135.mp4?alt=media&token=a5019ced-a5da-4223-8945-5d2d016914f0"
    lateinit var binding: ActivityCourseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val courseLessonAdapter = CourseLessonAdapter(this)
        initializePlayer()


        binding.apply {
            //            Play media


            lessonDbRecyclerView.apply {
                adapter = courseLessonAdapter
                layoutManager = LinearLayoutManager(this@CourseActivity)
                setHasFixedSize(true)
            }

            viewModel.courseWithLessons.observe(this@CourseActivity) {
                courseLessonAdapter.submitList(it.lessons)
            }


        }
    }

    private fun initializePlayer() {


        player = ExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                binding.playerView.player = exoPlayer
                val mediaItem = MediaItem.fromUri(videoUrl)
                exoPlayer.setMediaItem(mediaItem)

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
        hideSystemUi()
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer()
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
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
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
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

//    companion object {
//        private const val TAG = "CourseFragment"
//    }


}






