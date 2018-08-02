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
import android.widget.TextView;
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

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {

    Button butt1, butt2, butt3, butt4, butt5;
    boolean camera = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("MainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();

        butt1 = (Button)findViewById(R.id.temp_butt);
        butt2 = (Button)findViewById(R.id.heartrate_butt);
        butt3 = (Button)findViewById(R.id.weight_butt);
        butt4 = (Button)findViewById(R.id.options_butt);
        butt5 = (Button)findViewById(R.id.camera);
        TextView txt = (TextView)findViewById(R.id.textView3);
        
        WebView wbb = (WebView) findViewById(R.id.webviewer);
        WebSettings wbset=wbb.getSettings();
        wbset.setJavaScriptEnabled(true);
        wbb.setWebViewClient(new WebViewClient());
        String url="http://192.168.0.112:8081/";
        wbb.loadUrl(url);

        butt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (wbb.getVisibility() == View.GONE){
                    runAsyncTask(txt, 0);
                }
                else{
                    wbb.setVisibility(View.GONE);
                    txt.setVisibility(View.VISIBLE);
                    runAsyncTask(txt, 0);
                }
            }
        });
        butt1.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                Intent int1 = new Intent(MainActivity.this, Temperature.class);
                startActivity(int1);
                return false;
            }
        });
        butt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (wbb.getVisibility() == View.GONE){
                    runAsyncTask(txt, 3);
                }
                else{
                    wbb.setVisibility(View.GONE);
                    txt.setVisibility(View.VISIBLE);
                    runAsyncTask(txt, 3);
                }
            }
        });
        butt2.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                Intent int2 = new Intent(MainActivity.this, HeartRate.class);
                startActivity(int2);
                return false;
            }
        });

        butt3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (wbb.getVisibility() == View.GONE){
                    runAsyncTask(txt, 1);
                }
                else{
                    wbb.setVisibility(View.GONE);
                    txt.setVisibility(View.VISIBLE);
                    runAsyncTask(txt, 1);
                }
            }
        });

        butt3.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                Intent int3 = new Intent(MainActivity.this, Weight.class);
                startActivity(int3);
                return false;
            }
        });

        butt4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                    Intent int4 = new Intent(MainActivity.this, Options.class);
                    startActivity(int4);
            }
        });

        butt5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                    if (txt.getVisibility() == View.VISIBLE){
                        txt.setVisibility(View.GONE);
                        wbb.setVisibility(View.VISIBLE);
                    }
                    else {
                        wbb.setVisibility(View.VISIBLE);
                    }
            }

        });
    }
    private void runAsyncTask(TextView txt, int datatype) {
        new fileOps(txt, datatype).execute("http://phuocandlilianfamily.com/MFile.txt", "http://phuocandlilianfamily.com/WFile.txt");
    }

    private class fileOps extends AsyncTask<String, Void, ArrayList<Float>> {

        TextView txt;
        int datatype;
        ArrayList<Float> myValueList = new ArrayList<>();
        String[] units = {"C" , "LB" , "CRY", "BPM"};


        private fileOps(TextView txt, int datatype){
            this.txt = txt;
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
                    myValueList.add(Float.parseFloat(tokens[2])/100);
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
            txt.setText(Float.toString(result.get(datatype)) + units[datatype]);


        }
    }

}