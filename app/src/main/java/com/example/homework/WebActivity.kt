package com.example.homework

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class WebActivity : AppCompatActivity() {

    private lateinit var websiteAdapter: WebsiteAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoucang)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val addButton = findViewById<Button>(R.id.addButton)

        // 初始化 RecyclerView 和适配器
        websiteAdapter = WebsiteAdapter(emptyList())
        recyclerView.adapter = websiteAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 初始化 SharedPreferences
        sharedPreferences = getSharedPreferences("website_preferences", Context.MODE_PRIVATE)

        // 从 SharedPreferences 中读取数据，并更新 RecyclerView
        updateWebsiteList()

        // 添加按钮点击事件
        addButton.setOnClickListener {
            showAddWebsiteDialog()
        }
    }

    // 更新 RecyclerView 中的数据
    @SuppressLint("NotifyDataSetChanged")
    private fun updateWebsiteList() {
        val websites = getWebsitesFromSharedPreferences()
        websiteAdapter.websites = websites
        websiteAdapter.notifyDataSetChanged()
    }

    // 从 SharedPreferences 中获取网站列表
    private fun getWebsitesFromSharedPreferences(): List<Website> {
        val websitesJsonString = sharedPreferences.getString("websites", "[]")
        Log.d("WebActivity", "websitesJsonString: $websitesJsonString")
        return try {
            val jsonArray = JSONArray(websitesJsonString)
            (0 until jsonArray.length()).map { index ->
                val jsonObject = jsonArray.getJSONObject(index)
                Website(jsonObject.getString("name"), jsonObject.getString("url"))
            }
        } catch (e: JSONException) {
            emptyList()
        }
    }

    // 将网站列表保存到 SharedPreferences 中
    private fun saveWebsitesToSharedPreferences(websites: List<Website>) {
        val jsonArray = JSONArray()
        websites.forEach { website ->
            val jsonObject = JSONObject()
            jsonObject.put("name", website.name)
            jsonObject.put("url", website.url)
            jsonArray.put(jsonObject)
        }
        try {
            val editor = sharedPreferences.edit()
            editor.putString("websites", jsonArray.toString())
            editor.apply()
        } catch (e: Exception) {
            Log.e("WebActivity", "Error saving websites to SharedPreferences", e)
        }
    }

    // 显示添加网站的对话框
    private fun showAddWebsiteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("添加网站")

        // 创建 LinearLayout
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(64, 48, 64, 48)

        // 添加网站名称输入框
        val nameEditText = EditText(this)
        nameEditText.hint = "网站名称"
        layout.addView(nameEditText)

        // 添加网站 URL 输入框
        val urlEditText = EditText(this)
        urlEditText.hint = "网站 URL"
        layout.addView(urlEditText)

        // 将 LinearLayout 添加到 AlertDialog
        builder.setView(layout)

        builder.setPositiveButton("确定") { dialog, which ->
            val name = nameEditText.text.toString()
            val url = urlEditText.text.toString()
            val website = Website(name, url)
            addWebsite(website)
        }

        builder.setNegativeButton("取消") { dialog, which ->
            dialog.cancel()
        }

        builder.show()
    }

    // 添加网站到列表中
    @SuppressLint("NotifyDataSetChanged")
    private fun addWebsite(website: Website) {
        val websites = websiteAdapter.websites.toMutableList()
        websites.add(website)
        websiteAdapter.websites = websites.toList()
        websiteAdapter.notifyDataSetChanged()

        // 保存网站列表到 SharedPreferences 中
        saveWebsitesToSharedPreferences(websites)
    }

    // Website 数据类
    data class Website(val name: String, val url: String)

    // WebsiteAdapter 适配器类
    class WebsiteAdapter(var websites: List<Website>) :
        RecyclerView.Adapter<WebsiteAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val nameTextView: TextView = view.findViewById(R.id.nameTextView)
            val urlTextView: TextView = view.findViewById(R.id.urlTextView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_website, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val website = websites[position]
            holder.nameTextView.text = website.name
            holder.urlTextView.text = website.url
            holder.itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(website.url))
                it.context.startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return websites.size
        }
    }
}