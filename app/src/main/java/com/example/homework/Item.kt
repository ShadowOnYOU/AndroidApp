package com.example.homework

data class Item(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val videoUrl: String? // 添加一个 videoUrl 属性
)