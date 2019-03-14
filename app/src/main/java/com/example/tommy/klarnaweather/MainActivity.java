package com.example.tommy.klarnaweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ProgressBar mLoadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoadingProgress = (ProgressBar) findViewById(R.id.progressBar);
        try {
            URL weatherUrl = UtilJson.buildUrl("59.251571,18.176707"); // Finserudsgränd 7 , Älta My Place :)
            new WeaterQueryTask().execute(weatherUrl);

        }
        catch (Exception e) {
            Log.d("error", e.getMessage());
        }
    }
    public class WeaterQueryTask extends AsyncTask<URL,Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String result = null;
            try {
                result = UtilJson.getJson(searchURL);
            }
            catch (IOException e) {
                Log.e("Error",e.getMessage());
            }
            return result;
        }

        @Override
        protected  void onPostExecute(String result){
            TextView tvResult = (TextView) findViewById(R.id.textView_test);
            TextView tvError = (TextView) findViewById(R.id.tv_error);
            mLoadingProgress.setVisibility(View.INVISIBLE);
            if(result == null){
                tvResult.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);
            }
            else {
                tvResult.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.INVISIBLE);
            }
            //tvResult.setText(result);
            ArrayList<Weater> weaters = UtilJson.getWeaterFromJson(result);
            String timezone     = weaters.get(0).timezone;
            String summary      = weaters.get(0).summary;
            int time;
            try{
                time = Integer.parseInt(weaters.get(0).time);
            }
            catch(NumberFormatException ex) {
                time = 0;
            }
            String strTemp;
            try {
                double temperature = Double.parseDouble(weaters.get(0).temperature.replace(',','.'));
                temperature = ((temperature-32) * 5)/9;
                strTemp = String.format("%.1f", temperature);
            }
            catch(NumberFormatException ex) {
                strTemp = "";
            }
            String dateAsText = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date(time * 1000L));
            String resultString = timezone +"\nDate: " + dateAsText +"\n" +summary+"\nTemp: "+strTemp +"\u00b0";

            tvResult.setText(resultString);
        }
        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
