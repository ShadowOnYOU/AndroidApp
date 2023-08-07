package com.example.homework

/**
 * 列表每一项的数据
 *
 * @author: xiejikun
 * @since: 2023/7/7
 */
data class ItemBean(
    /** 图片地址 */
    val coverUrl: Int?,
    val imageUrl:String?,
    /** 标题 */
    val title: String?,
    /** 摘要 */
    val hasImage: Boolean?,
    val content: String?
)