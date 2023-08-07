package com.example.homework;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    private ImageView ivImage;
    private Button btnUploadImage;
    private EditText et_title;
    private EditText etSummary;
    private EditText etContent;
    private Button btn_upload;
    private DemoAdapter adapter;

    private static final int REQUEST_CODE_PICK_IMAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        ivImage = findViewById(R.id.iv_image);
        btnUploadImage = findViewById(R.id.btn_upload_image);
        etSummary = findViewById(R.id.et_summary);
        et_title = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        btn_upload = findViewById(R.id.btn_upload);

        ivImage.setImageDrawable(null);
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开相册
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
                String uriString = etSummary.getText().toString();
                Log.d("String",uriString);
                if (!TextUtils.isEmpty(uriString)) {
                    Log.d("String",uriString);
                    Uri uri = Uri.parse(uriString);
                    Glide.with(AddActivity.this)
                            .load(uri)
                            .into(ivImage);
                }
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DiscouragedPrivateApi")
            @Override
            public void onClick(View view) {
                // ItemBean firstItem = new ItemBean(R.drawable.ic_launcher_background, "我是新闻标题1", true, "11111");
                Log.d("Add", "onClick: .");
                String uri = etSummary.getText().toString();
                String title = et_title.getText().toString();
                if(title.endsWith("[pinned]")){
                    title = title.replace("[pinned]", "");
                    title += "【置顶】";
                }
                String content = etContent.getText().toString();
                // todo
                Log.d("URI",uri);
                ItemBean itemBean;
                if(TextUtils.isEmpty(uri)){
                    itemBean = new ItemBean(0, uri,title, false, content); // 创建一个新的 ItemBean 对象
                }else{
                    itemBean = new ItemBean(0, uri,title, true, content); // 创建一个新的 ItemBean 对象
                }
                SharedPreferences sharedPreferences = getSharedPreferences("news_list_test", Context.MODE_PRIVATE);
                Gson gson = new Gson();
                String json = sharedPreferences.getString("news_list_test", null);
                Type type = new TypeToken<ArrayList<ItemBean>>() {}.getType();
                ArrayList<ItemBean> list = gson.fromJson(json, type);
                if (list == null) {
                    list = new ArrayList<>();
                }
                if(title.endsWith("【置顶】")){
                    list.add(0, itemBean);
                }else{
                    list.add(itemBean);
                }
                String newJson = gson.toJson(list);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("news_list_test", newJson);
                editor.apply();
                Log.d("DemoActivity", "SharedPreferences updated: " + newJson);
                Log.d("DemoActivity", "RecyclerView updated");

                // 弹出提示框
                AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                builder.setTitle("提示");
                builder.setMessage("添加成功");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 返回上一个界面
                        finish();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (data != null) {
                // 获取选中图片的Uri
                Uri uri = data.getData();
                // 显示选中的图片
                ivImage.setImageURI(uri);
                Log.d("addd", uri.toString());
                ivImage.setTag(uri);
            }
        }
    }

    // 将Uri转换成实际路径
    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return null;
    }
}