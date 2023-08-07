package com.example.homework

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class VideoAdapter(private val dataList: List<VideoData>) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        Glide.with(holder.itemView)
            .load(data.imageUrl)
            .placeholder(R.drawable.loading) // 加载中占位图
            .error(R.drawable.fial) // 加载失败占位图
            .override(300,150)
            .into(holder.imageView)
//        holder.imageView.setImageResource(data.imageResId)
        holder.textView.text = data.text
        holder.itemView.setOnClickListener { v ->
            // 点击事件处理逻辑
            val videoData = dataList[position]
            val intent = Intent(v.context, VideoLandingActivity::class.java)
            intent.putExtra("videoUrl", videoData.videoUrl)
            v.context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val textView: TextView = itemView.findViewById(R.id.text_view)
    }
}