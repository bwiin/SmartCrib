package com.example.wiinb.smartcrib;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        //runAsyncTask();
}

    private void runAsyncTask() {
        //new fileOps().execute("http://ucfgroup7smartcrib.ddns.net/myFile.txt");
    }

    private class fileOps extends AsyncTask<String, Void, ArrayList<String>> {
        ArrayList<String> myList = new ArrayList<>();

        protected ArrayList<String> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String myString;

                while ((myString = in.readLine()) != null) {
                    //publishProgress(myString);
                    myList.add(myString);
                }
                in.close();
                return myList;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void publishProgress(String...strings)
        {
                //System.out.println(strings[0]);
        }


        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            String myString = "";
            for (String data : result) {
                myString += data + "\n";
            }
            //txt.setText(myString);

        }
    }
}
