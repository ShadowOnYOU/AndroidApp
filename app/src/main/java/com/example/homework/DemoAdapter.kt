package com.example.homework

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class DemoAdapter(private var demoList: List<ItemBean>) :
    RecyclerView.Adapter<DemoViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycleview_item, parent, false)
        return DemoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        val itemBean = demoList[position]
        itemBean.imageUrl?.let { Log.d("imageUrl", it) }
//        Glide.with(holder.itemView)
//            .load(itemBean.imageUrl)
//            .placeholder(R.drawable.role) // 加载中占位图
//            .error(R.drawable.role) // 加载失败占位图
//            .into(holder.ivCover)
        if (itemBean.hasImage == true) {
            holder.ivCover.visibility = View.VISIBLE
            itemBean.coverUrl?.let {
                if(it != 0){
                    holder.ivCover.setImageResource(it)
                }else{
                    Glide.with(holder.itemView)
                        .load(itemBean.imageUrl)
                        .override(300,150)
                        .placeholder(R.drawable.loading) // 加载中占位图
                        .error(R.drawable.fial) // 加载失败占位图
                        .into(holder.ivCover)
                }
            }
            // 更新约束条件
            val layoutParams = holder.tvTitle.layoutParams as ConstraintLayout.LayoutParams
//            layoutParams.startToStart = holder.ivCover.id
            layoutParams.topToTop = holder.ivCover.id
            layoutParams.topMargin = 0
            holder.tvTitle.layoutParams = layoutParams
        } else {
            holder.ivCover.visibility = View.GONE
            // 更新约束条件
            val layoutParams = holder.tvTitle.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.startToStart = holder.itemView.id
            //layoutParams.topToTop = holder.itemView.id
            layoutParams.topMargin = -150
            holder.tvTitle.layoutParams = layoutParams
//            val contentLayoutParams = holder.tvContent.layoutParams as ConstraintLayout.LayoutParams
//            contentLayoutParams.startToStart = holder.itemView.id
//            contentLayoutParams.endToEnd = holder.itemView.id
//            contentLayoutParams.topToBottom = holder.tvTitle.id
//            holder.tvContent.layoutParams = contentLayoutParams
        }

        itemBean.title?.let {
            holder.tvTitle.text = it
        }
        holder.itemView.setOnClickListener { v ->
            // 点击事件处理逻辑
            val intent = Intent(v.context, DetailActivity::class.java)
            intent.putExtra("title", itemBean.title)
            intent.putExtra("content", itemBean.content)
            v.context.startActivity(intent)
        }

        itemBean.content?.let {
            holder.tvContent.text = it
        }
    }

    override fun getItemCount(): Int {
        return demoList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: ArrayList<ItemBean>) {
        demoList = newList
        notifyDataSetChanged()
    }
}