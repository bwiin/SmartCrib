package com.example.wiinb.smartcrib;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;


import java.lang.ref.WeakReference;
import java.util.Date;


public class Temperature extends AppCompatActivity {

    // create a dynamodb mapper;
    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();

        //createEntry();
        new getData(this,dynamoDBMapper).execute();
    }

    public void createEntry(){
        final BiometricsDO temp = new BiometricsDO();

        //temp.setData(72.5);
        //temp.setDatatype("temperature");
        //temp.setFlag(false);
        //temp.setTimestamp("2018:06:08:22:30:30");
        //new AWSOps(this).execute();
    }
    public void pullEntry(){
        BiometricsDO temp;

    }
    private static class AWSOps extends AsyncTask<BiometricsDO, Void, Void> {

        private WeakReference<Temperature> activityReference;

        AWSOps(Temperature context){
            activityReference = new WeakReference<>(context);
        }

        protected Void doInBackground(BiometricsDO... params) {

            DynamoDBMapper dynamoDBMapper = null;

            //dynamoDBMapper.save(params[0]);
            return null;

        }

        protected void onPostExecute() {
            System.out.println("done");
        }
    }
    private static class getData extends AsyncTask<BiometricsDO, Void, BiometricsDO> {

        private WeakReference<Temperature> activityReference;
        DynamoDBMapper dynamoDBMapper;

        getData(Temperature context, DynamoDBMapper dynamoDBMapper){
            activityReference = new WeakReference<>(context);
            this.dynamoDBMapper = dynamoDBMapper;
        }


        @Override
        protected BiometricsDO doInBackground(BiometricsDO... biometricsDOS) {

            return dynamoDBMapper.load(BiometricsDO.class, "weight", "2018:07:03:05:46:06");
        }

        //use onPostExecute to send data from queries to be used in another method.
        @Override
        protected void onPostExecute(BiometricsDO result) {
            System.out.println(result.getData());
        }
    }

}

