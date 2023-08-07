package com.example.homework;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder> {

    private List<WeatherForecast> mWeatherForecastList;

    public WeatherForecastAdapter(List<WeatherForecast> weatherForecastList) {
        mWeatherForecastList = weatherForecastList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_forecast, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherForecast weatherForecast = mWeatherForecastList.get(position);

        holder.dateTextView.setText(weatherForecast.getDate());
        holder.temperatureTextView.setText(weatherForecast.getTemperature());
        holder.weatherConditionTextView.setText(weatherForecast.getWeatherCondition());
        holder.humidityTextView.setText(weatherForecast.getHumidity());
    }

    @Override
    public int getItemCount() {
        return mWeatherForecastList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView temperatureTextView;
        TextView weatherConditionTextView;
        TextView humidityTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTextView = itemView.findViewById(R.id.date_text_view);
            temperatureTextView = itemView.findViewById(R.id.temperature_text_view);
            weatherConditionTextView = itemView.findViewById(R.id.weather_condition_text_view);
            humidityTextView = itemView.findViewById(R.id.humidity_text_view);
        }
    }
}
