package com.example.tommy.klarnaweather;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeatherQueryTask extends AsyncTask<URL,Void, String> {


    private MainActivity mainActivity;


    public WeatherQueryTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


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
        TextView tvResult = (TextView) mainActivity.findViewById(R.id.textView_test);
        TextView tvError = (TextView) mainActivity.findViewById(R.id.tv_error);
        ProgressBar mLoadingProgress = (ProgressBar) mainActivity.findViewById(R.id.progressBar);
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
        ArrayList<Weather> weaters = UtilJson.getWeaterFromJson(result);
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
        ProgressBar mLoadingProgress = (ProgressBar) mainActivity.findViewById(R.id.progressBar);
        mLoadingProgress.setVisibility(View.VISIBLE);

    }
}
