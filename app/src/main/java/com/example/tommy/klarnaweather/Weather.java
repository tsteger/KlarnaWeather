package com.example.tommy.klarnaweather;

public class Weather {

    protected String timezone;
    protected String time;
    protected String summary;
    protected String temperature;



    protected Weather(String timezone, String time, String temperature, String summary) {

        this.timezone = timezone;
        this.time = time;
        this.temperature = temperature;
        this.summary = summary;

    }
}
