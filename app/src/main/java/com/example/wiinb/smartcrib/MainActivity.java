package com.example.wiinb.smartcrib;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();


        Button butt1 = (Button)findViewById(R.id.temp_butt);
        Button butt2 = (Button)findViewById(R.id.heartrate_butt);
        Button butt3 = (Button)findViewById(R.id.weight_butt);

        butt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent int1 = new Intent(MainActivity.this, Temperature.class);
                startActivity(int1);
            }
        });
        butt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent int2 = new Intent(MainActivity.this, HeartRate.class);
                startActivity(int2);
            }
        });

        butt3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent int3 = new Intent(MainActivity.this, Weight.class);
                startActivity(int3);
            }
        });

        //AWSMobileClient.getInstance().initialize(this).execute();

        runAsyncTask(butt1, butt2, butt3);
}

    private void runAsyncTask(Button butt1, Button butt2, Button butt3) {
        new fileOps(butt1, butt2, butt3).execute("http://phuocandlilianfamily.com/MFile.txt", "http://phuocandlilianfamily.com/WFile.txt");
    }

    private class fileOps extends AsyncTask<String, Void, String[]> {

        Button butt1;
        Button butt2;
        Button butt3;

        ArrayList<String> myList = new ArrayList<>();

        private fileOps(Button butt1, Button butt2, Button butt3){
            this.butt1 = butt1;
            this.butt2 = butt2;
            this.butt3 = butt3;
        }
        protected String[] doInBackground(String... params) {
            try {

                System.out.println(params[0]);
                URL url1 = new URL(params[0]);
                BufferedReader in = new BufferedReader(new InputStreamReader(url1.openStream()));
                String myString;
                String[] myValues = new String[3];
                String[] tokens;
                int i = 0;

                while ((myString = in.readLine()) != null) {
                    if(myString.length()> 3){
                    tokens = myString.split("\\s+");
                    myValues[i] = tokens[2];
                    i++;
                    }
                }
                in.close();

                return myValues;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void publishProgress(String...strings)
        {
                //System.out.println(strings[0]);
        }
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            String myString = "";
            for (String data : result) {
                System.out.println(data+"\n");
            }
            butt1.setText(result[0]);


        }
    }
}
