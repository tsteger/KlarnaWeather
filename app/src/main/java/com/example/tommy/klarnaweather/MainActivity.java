package com.example.tommy.klarnaweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ProgressBar mLoadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            URL weatherUrl = UtilJson.buildUrl("59.251571,18.176707"); // Finserudsgränd 7 , Älta My Place :)
            new WeatherQueryTask(this).execute(weatherUrl);
        }
        catch (Exception e) {
            Log.d("error", e.getMessage());
        }
    }

}
