package com.example.homework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText mUsernameEditText;
    private EditText mSignatureEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // 获取视图
        mUsernameEditText = findViewById(R.id.username_edit_text);
        mSignatureEditText = findViewById(R.id.signature_edit_text);

        // 从 Intent 中获取当前用户信息，并将其显示在 EditText 中
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String signature = intent.getStringExtra("signature");
        mUsernameEditText.setText(username);
        mSignatureEditText.setText(signature);

        // 创建保存按钮的点击事件
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户编辑后的信息
                String newUsername = mUsernameEditText.getText().toString();
                String newSignature = mSignatureEditText.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("username", newUsername);
                intent.putExtra("signature", newSignature);
                setResult(Activity.RESULT_OK, intent);
                Log.d("name",newUsername + newSignature);
                finish();
            }
        });

        // 创建取消按钮的点击事件
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 返回上一个页面
                finish();
            }
        });
    }
}