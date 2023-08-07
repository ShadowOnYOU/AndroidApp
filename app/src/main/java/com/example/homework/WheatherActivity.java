package com.example.homework;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WheatherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whether);

        // 在这里添加您的Activity逻辑代码
        addRecycleView_whether();
    }

    private void addRecycleView_whether() {
        // 从布局中进行获取
        RecyclerView recyclerView = findViewById(R.id.weather_forecast_list);
        WeatherForecastAdapter adapter = new WeatherForecastAdapter(createDemoWeatherForecast());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private ArrayList<WeatherForecast> createDemoWeatherForecast() {
        ArrayList<WeatherForecast> list = new ArrayList<>();
        list.add(new WeatherForecast("2023-07-23", "31°C", "晴", "45%"));
        list.add(new WeatherForecast("2023-07-24", "29°C", "多云", "60%"));
        list.add(new WeatherForecast("2023-07-25", "28°C", "小雨", "75%"));
        list.add(new WeatherForecast("2023-07-26", "32°C", "晴", "50%"));
        list.add(new WeatherForecast("2023-07-27", "31°C", "晴", "45%"));
        list.add(new WeatherForecast("2023-07-28", "29°C", "多云", "60%"));
        list.add(new WeatherForecast("2023-07-29", "28°C", "小雨", "75%"));
        list.add(new WeatherForecast("2023-07-30", "32°C", "晴", "50%"));
        list.add(new WeatherForecast("2023-07-31", "31°C", "晴", "45%"));
        list.add(new WeatherForecast("2023-08-01", "29°C", "多云", "60%"));
        list.add(new WeatherForecast("2023-08-02", "28°C", "小雨", "75%"));
        list.add(new WeatherForecast("2023-08-03", "32°C", "晴", "50%"));
        // 添加更多的天气预报数据项...
        return list;
    }

    public void OnWhetherDetailClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tianqi.2345.com/"));
        startActivity(intent);
    }
}
