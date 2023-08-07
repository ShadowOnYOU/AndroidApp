package com.example.homework

import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException

/**
 * 用于复用的ViewHolder
 *
 * @author: xiejikun
 * @since: 2023/7/7
 */
class DemoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    /** 封面图 */
    var ivCover: ImageView
       private set
    /** 标题 */
    var tvTitle:TextView
        private set
    /** 内容 */
    var tvContent: TextView
        private set


    init {
        ivCover = view.findViewById(R.id.cover)
        tvTitle = view.findViewById(R.id.tv_title)
        tvContent = view.findViewById(R.id.tv_content)
    }

    /**
     * 将选中的图片显示到封面图中
     */
    fun setCoverImage(uri: Uri) {
        // 将选中的图片显示到 ImageView 中
        try {
            val bitmap = MediaStore.Images.Media.getBitmap(itemView.context.contentResolver, uri)
            ivCover.setImageBitmap(bitmap)
            ivCover.tag = uri // 将选中的图片的 Uri 存储在 ImageView 的 tag 中
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}