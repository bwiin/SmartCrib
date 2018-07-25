package com.example.wiinb.smartcrib;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.goebl.david.Webb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class Options extends AppCompatActivity {

    Button butt1, butt2, butt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        butt1 = (Button)findViewById(R.id.rweight);
        butt2 = (Button)findViewById(R.id.motionX);
        butt3 = (Button)findViewById(R.id.motionY);

        butt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String urlString = "http://phuocandlilianfamily.com/PW=group7.CTRL.php?w=123455";
                /*
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String urlString = "http://phuocandlilianfamily.com/PW=group7.CTRL.php?w=123455";
                            URL url = new URL("http://phuocandlilianfamily.com/PW=group7.CTRL.php");
                            Map<String, Object> params = new LinkedHashMap<>();
                            params.put("w", "1");

                            StringBuilder postData = new StringBuilder();
                            for (Map.Entry<String, Object> param : params.entrySet()) {
                                if (postData.length() != 0) postData.append('&');
                                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                                postData.append('=');
                                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                            }
                            byte[] postDataBytes = postData.toString().getBytes("ISO-8859-1");

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                            conn.setDoOutput(true);
                            conn.getOutputStream().write(postDataBytes);

                            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                            for (int c; (c = in.read()) >= 0; )
                                System.out.print((char) c);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                AsyncTask.execute(runnable);
                */

                /*Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Webb webb = Webb.create();
                        webb.post("http://phuocandlilianfamily.com/PW=group7.CTRL.php")
                                .param("w", "b")
                                .ensureSuccess()
                                .asVoid();
                    }
                };
                AsyncTask.execute(runnable);


                */

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Webb webb = Webb.create();
                        webb.post("http://phuocandlilianfamily.com/PW=group7.CTRL.php")
                                .param("W", "1")
                                .ensureSuccess()
                                .asVoid();
                    }
                };
                AsyncTask.execute(runnable);
                butt1.setText("Weight Reset!");

            }
        });
        butt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                runAsyncTask(butt2, butt3);

            }
            });
        butt3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                runAsyncTask(butt2, butt3);

            }
        });
    }




    private void runAsyncTask(Button myButt1, Button myButt2) {
        new fileOps(myButt1, myButt2).execute("http://phuocandlilianfamily.com/WFile.txt");
    }

    private class fileOps extends AsyncTask<String, Void, ArrayList<Integer>> {

        Button myButt1, myButt2;
        ArrayList<Integer> myValueList = new ArrayList<>();

        private fileOps(Button myButt1, Button myButt2){
            this.myButt1 = myButt1;
            this.myButt2 = myButt2;
        }

        protected ArrayList<Integer> doInBackground(String... params) {
            try {
                URL url1 = new URL(params[0]);
                BufferedReader in1 = new BufferedReader(new InputStreamReader(url1.openStream()));
                String myString;
                String[] tokens;
                int i = 0;

                while ((myString = in1.readLine()) != null) {

                    tokens = myString.split("\\s+");
                    myValueList.add(parseInt(tokens[2]));

                }
                in1.close();

                return myValueList;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void publishProgress(String...strings)
        {
        }
        protected void onPostExecute(ArrayList<Integer> result) {
            super.onPostExecute(result);
            myButt1.setText("MotionX = " +  result.get(1));
            myButt2.setText(("MotionY = " + result.get(2)));
            System.out.println("got");


        }
    }

}
