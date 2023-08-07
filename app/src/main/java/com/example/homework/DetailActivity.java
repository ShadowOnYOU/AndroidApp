package com.example.homework;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_CONTENT = "content";

    private TextView tvTitle;
    private TextView tvContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 获取从主页面传递过来的标题和内容信息
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String content = getIntent().getStringExtra(EXTRA_CONTENT);

        // 将标题和内容信息显示在界面上
        TextView tvTitle = findViewById(R.id.tv_title_detail);
        TextView tvContent = findViewById(R.id.tv_content_detail);
        tvTitle.setText(title);
        tvContent.setText(content);
    }

}