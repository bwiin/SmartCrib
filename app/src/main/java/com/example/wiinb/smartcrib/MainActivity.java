package com.example.wiinb.smartcrib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;
import java.net.URL;
import java.util.*;
import android.os.AsyncTask;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import java.lang.String;
public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView txt = (TextView) findViewById(R.id.txtView);
        txt.setMovementMethod(new ScrollingMovementMethod());

        runAsyncTask();
    }

    private void runAsyncTask() {
        new fileOps().execute("http://ucfgroup7smartcrib.ddns.net/myFile.txt");
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
        protected void onPreExecute()
        {
            TextView txt1 = findViewById(R.id.myText);
            txt1.setText("Testing onPreExecute");
        }

        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            String myString = "";
            for (String data : result) {
                myString += data + "\n";
            }
            TextView txt = (TextView) findViewById(R.id.txtView);
            txt.setText(myString);

        }
    }
}
