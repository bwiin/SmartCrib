package com.example.wiinb.smartcrib;

import android.os.Bundle;
import android.view.View;

import com.example.wiinb.smartcrib.MainActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by wiinb on 2/16/2018.
 */

public class TemperatureScreen extends MainActivity implements MainActivity.GlobalStats {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperature);


        //pullTemperature();
        setTemperature();
    }

    public void pullTemperature()
    {

    }

    public void setTemperature()
    {
        String url = "http://ucfgroup7smartcrib.ddns.net/PW=group7.Control.php";
        try {
            URL myUrl = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) myUrl.openConnection();
            urlConnection.setDoOutput(true);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

