package com.example.homework;

public class WeatherForecast {
    private String date;
    private String temperature;
    private String weatherCondition;
    private String humidity;

    public WeatherForecast(String date, String temperature, String weatherCondition, String humidity) {
        this.date = date;
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
        this.humidity = humidity;
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public String getHumidity() {
        return humidity;
    }
}