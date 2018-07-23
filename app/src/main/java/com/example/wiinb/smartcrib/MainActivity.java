package com.example.wiinb.smartcrib;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {

    private static final int TIMEOUT = 0;
    Button butt1, butt2, butt3;
    boolean one, two, three;



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

        butt1 = (Button)findViewById(R.id.temp_butt);
        butt2 = (Button)findViewById(R.id.heartrate_butt);
        butt3 = (Button)findViewById(R.id.weight_butt);
        
        WebView wbb = (WebView) findViewById(R.id.webviewer);
        WebSettings wbset=wbb.getSettings();
        wbset.setJavaScriptEnabled(true);
        wbb.setWebViewClient(new WebViewClient());
        String url="http://192.168.0.102:8081/";
        wbb.loadUrl(url);


        butt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (one == false){
                    runAsyncTask(butt1, 0);
                    one = true;

                }else {
                    Intent int1 = new Intent(MainActivity.this, Temperature.class);
                    one = false;
                    butt1.setText("Temperature");
                    startActivity(int1);

                }
            }
        });
        butt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (two == false)
                {
                    runAsyncTask(butt2, 3);
                    two = true;

                }else {
                    Intent int2 = new Intent(MainActivity.this, HeartRate.class);
                    two = false;
                    butt2.setText("HR");
                    startActivity(int2);
                }
            }
        });

        butt3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (three == false)
                {
                    runAsyncTask(butt3, 1);
                    three = true;
                }else {
                    Intent int3 = new Intent(MainActivity.this, Weight.class);
                    three = false;
                    butt3.setText("Weight");
                    startActivity(int3);
                }
            }
        });

        //AWSMobileClient.getInstance().initialize(this).execute();
}
    private void runAsyncTask(Button myButt, int datatype) {
        new fileOps(myButt, datatype).execute("http://phuocandlilianfamily.com/MFile.txt", "http://phuocandlilianfamily.com/WFile.txt");
    }

    private class fileOps extends AsyncTask<String, Void, ArrayList<Float>> {

        Button myButt;
        int datatype;
        ArrayList<Float> myValueList = new ArrayList<>();
        String[] units = {"C" , "LB" , "CRY", "BPM"};


        private fileOps(Button myButt, int datatype){
            this.myButt = myButt;
            this.datatype = datatype;
        }

        protected ArrayList<Float> doInBackground(String... params) {
            try {
                URL url1 = new URL(params[0]);
                URL url2 = new URL(params[1]);
                BufferedReader in1 = new BufferedReader(new InputStreamReader(url1.openStream()));
                BufferedReader in2 = new BufferedReader(new InputStreamReader(url2.openStream()));
                String myString;
                String[] tokens;
                int i = 0;
                int j = 0;

                while ((myString = in1.readLine()) != null) {

                    tokens = myString.split("\\s+");
                    if(i <= 1) {
                        myValueList.add(Float.parseFloat(tokens[2]) / 100);
                        i++;
                    }else{
                        myValueList.add(Float.parseFloat(tokens[2]));
                        i++;}
                }
                while((myString = in2.readLine()) != null){
                    tokens = myString.split("\\s+");
                    myValueList.add(Float.parseFloat(tokens[2]));
                    j++;
                }
                in1.close();
                in2.close();

                return myValueList;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void publishProgress(String...strings)
        {
        }
        protected void onPostExecute(ArrayList<Float> result) {
            super.onPostExecute(result);
            myButt.setText(Float.toString(result.get(datatype)) + units[datatype]);


        }
    }

}
