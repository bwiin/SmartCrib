package com.example.wiinb.smartcrib;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;


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

        createEntry();
    }

    public void createEntry(){
        final BiometricsDO temp = new BiometricsDO();

        temp.setData(72.5);
        temp.setDatatype("temperature");
        temp.setFlag(false);
        temp.setTimestamp(null);
        new AWSOps().execute(temp);
    }
    private class AWSOps extends AsyncTask<BiometricsDO, Void, Void> {
        protected Void doInBackground(BiometricsDO... params) {
            dynamoDBMapper.save(params[0]);
            return null;

        }

        protected void onPostExecute() {
            System.out.println("done");
        }
    }
}

