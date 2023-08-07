package com.example.homework

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.FieldPosition

class VideoActivity : AppCompatActivity() {
    private val videolist = ArrayList<VideoData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        initkey()
        // 获取RecyclerView控件的引用
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.adapter = VideoAdapter(dataList)
        recyclerView.adapter = VideoAdapter(videolist)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_option1 -> {
                    // 处理选项1的跳转逻辑
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_option2 -> {
                    // 处理选项2的跳转逻辑
                    val intent = Intent(this, VideoActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_option3 -> {
                    // 处理选项3的跳转逻辑
                    val intent = Intent(this, PersonActivity::class.java)
                    startActivity(intent)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun initkey() {
        repeat(5) {
            videolist.add(
                VideoData(
                    1,
                    "视频1",
                    "https://lmg.jj20.com/up/allimg/1113/092219135626/1Z922135626-1-1200.jpg",
                    "https://media.w3.org/2010/05/sintel/trailer.mp4"
                )
            )
            videolist.add(
                VideoData(
                    1,
                    "视频2",
                    "https://lmg.jj20.com/up/allimg/1113/051220112022/200512112022-1-1200.jpg",
                    "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"
                )
            )
            videolist.add(
                VideoData(
                    1,
                    "视频3",
                    "https://lmg.jj20.com/up/allimg/1115/092621094155/210926094155-7-1200.jpg",
                    "http://vjs.zencdn.net/v/oceans.mp4"
                )
            )
            videolist.add(
                VideoData(
                    1,
                    "视频4",
                    "https://lmg.jj20.com/up/allimg/4k/s/02/210925000GaM1-0-lp.jpg",
                    "https://media.w3.org/2010/05/sintel/trailer.mp4"
                )
            )
        }
    }
    // https://lmg.jj20.com/up/allimg/4k/s/02/2109251S10BL0-0-lp.jpg
//
//    fun onImageClick(view: View) {
//        // 判断点击的是图片
//        //val position = recyclerView.getChildAdapterPosition(view)
//        // TODO：这边有问题
//        val videoData = videolist[1]
//        val intent = Intent(this,VideoLandingActivity::class.java)
//        intent.putExtra("videoUrl",videoData.videoUrl)
//        startActivity(intent)
//    }
}