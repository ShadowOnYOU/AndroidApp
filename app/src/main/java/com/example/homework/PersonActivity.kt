package com.example.homework

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class PersonActivity: AppCompatActivity() {

    val EDIT_PROFILE_REQUEST_CODE = 1001 // 定义请求代码为 1001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.homework.R.layout.activity_p_information)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val username = data?.getStringExtra("username")
            val signature = data?.getStringExtra("signature")
            // 在此处更新UI
            val mUsernameTextView = findViewById<TextView>(R.id.username)
            val mSignatureTextView = findViewById<TextView>(R.id.signature)

            Log.d("name",username + signature)
            mUsernameTextView.text = username
            mSignatureTextView.text = signature
        }
    }

    fun onBlogClicked(view: View){
        val url = "https://shadowonyou.github.io/"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    fun onGitClicked(view: View){
        val url = "https://github.com/ShadowOnYOU"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    fun onSupportClicked(view: View){
        val intent = Intent(this,SupportActivity::class.java)
        startActivity(intent)
    }

    fun onEditButtonClicked(view: View) {
        val intent = Intent(this, EditProfileActivity::class.java)
        val mUsernameTextView = findViewById<TextView>(R.id.username)
        val mSignatureTextView = findViewById<TextView>(R.id.signature)
        intent.putExtra("username", mUsernameTextView.text.toString())
        intent.putExtra("signature", mSignatureTextView.text.toString())
        startActivityForResult(intent,EDIT_PROFILE_REQUEST_CODE)
    }

    fun onShoucangClicked(view: View) {
        val intent = Intent(this,WebActivity::class.java)
        startActivity(intent)
    }

    fun onLikeClicked(view: View) {
        val intent = Intent(this,LikeActivity::class.java)
        startActivity(intent)
    }
}