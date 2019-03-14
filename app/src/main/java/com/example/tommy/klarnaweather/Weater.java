package com.example.tommy.klarnaweather;

public class Weater {

    public String timezone;

    public String time;
    public String summary;
    public String icon;
    public String temperature;
    public String apparentTemperature;
    public String pressure;
    public String windSpeed;


    public Weater(String timezone, String time, String temperature, String summary) {

        this.timezone = timezone;
        this.time = time;
        this.temperature = temperature;
        this.summary = summary;

    }
}
