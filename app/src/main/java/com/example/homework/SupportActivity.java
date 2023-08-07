package com.example.homework;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SupportActivity extends AppCompatActivity {

    private EditText feedbackEditText;
    private Button submitButton;
    private RatingBar ratingBar;
    private Button submitRatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feedbackEditText = findViewById(R.id.feedback_edittext);
        submitButton = findViewById(R.id.submit_button);
        ratingBar = findViewById(R.id.rating_bar);
        submitRatingButton = findViewById(R.id.submit_rating_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = feedbackEditText.getText().toString();
                // 将反馈内容提交给开发者
                writeToFile("Feedback: " + feedback);
            }
        });

        submitRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                // 将用户评分提交给开发者
                writeToFile("Rating: " + rating);
            }
        });
    }

    private void writeToFile(String data) {
        // 获取文件路径
        String filePath = getApplicationContext().getExternalFilesDir(null).getAbsolutePath() + "/support.txt";

        try {
            // 打开文件
            FileOutputStream fos = new FileOutputStream(filePath, true);
            // 在文件末尾追加数据
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(data + "\n");
            osw.close();
            fos.close();
            Toast.makeText(this, "Data written to file", Toast.LENGTH_SHORT).show();
            // 打印一下结果
            printFileContent(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printFileContent(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            br.close();
            isr.close();
            fis.close();
            String fileContent = stringBuilder.toString();
            Log.d("File Content", fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}