package com.example.homework

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class VideoLandingActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var mediaController: MediaController
    private lateinit var mediaControllerContainer: LinearLayout

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_landing_page)

        // 获取 VideoView 控件
        videoView = findViewById(R.id.video_view)

        // 获取 MediaController 控件和容器
        mediaController = MediaController(this)
        mediaControllerContainer = findViewById(R.id.media_controller_container)
        mediaController.setAnchorView(mediaControllerContainer)
        videoView.setMediaController(mediaController)
        Log.d("AddActivity", "SharedPreferences updated:")

        // 设置视频文件的路径
        // val path = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
        val path = intent.getStringExtra("videoUrl")
        videoView.setVideoPath(path)

        // 开始播放视频
        videoView.start()

        // 当用户触摸屏幕时，显示 MediaController
        videoView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                mediaControllerContainer.visibility = View.VISIBLE
            }
            false
        }

        // 当视频播放结束时，隐藏 MediaController
        videoView.setOnCompletionListener {
            mediaControllerContainer.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 释放资源
        videoView.stopPlayback()
    }
}