package com.example.wiinb.smartcrib;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.google.gson.Gson;


import java.lang.ref.WeakReference;
import java.util.Date;


public class Temperature extends AppCompatActivity {

    // create a dynamodb mapper;
    DynamoDBMapper dynamoDBMapper;
    String endStamp;
    String startStamp;
    int periodType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();

        Button butt1 = (Button)findViewById(R.id.hourButt);
        Button butt2 = (Button)findViewById(R.id.dayButt);
        Button butt3 = (Button)findViewById(R.id.monthButt);

        butt1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                periodType = 3;
                endStamp = buildTimestamp(null);
                startStamp = buildTimestamp("hour");

                new getData(Temperature.this , dynamoDBMapper, endStamp, startStamp).execute();
            }
        });
        butt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                periodType = 2;
                endStamp = buildTimestamp(null);
                startStamp = buildTimestamp("day");

                new getData(Temperature.this , dynamoDBMapper, endStamp, startStamp).execute();
            }
        });
        butt3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                periodType = 1;
                endStamp = buildTimestamp(null);
                startStamp = buildTimestamp("month");

                new getData(Temperature.this, dynamoDBMapper, endStamp, startStamp).execute();
            }
        });
        //createEntry();,
    }
    //creates the timestamp and also processes the timestampRange.
    private String buildTimestamp(String timestampRange){
        String now;

        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("yyyy:MM:dd:HH:mm:s");
        now = formatter.format(date);
        now = subtractDate(now, timestampRange);

        return now;
    }
    //this function modifies the date in however you want. This is used to convert timezones, and
    //get a start date and end date.
    private String subtractDate(String date, String timestampRange){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd:HH:mm:s");
        String returnValue = "";
        if (timestampRange == "hour") {
            try {
                Date newDate = formatter.parse(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(newDate);
                cal.add(Calendar.HOUR, +4);
                Date minusOne = cal.getTime();
                returnValue = formatter.format(minusOne);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(timestampRange == "day")
        {
            try {
                Date newDate = formatter.parse(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(newDate);
                cal.add(Calendar.DAY_OF_MONTH, -1);
                Date minusOne = cal.getTime();
                returnValue = formatter.format(minusOne);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(timestampRange == "month"){
            try {
                Date newDate = formatter.parse(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(newDate);
                cal.add(Calendar.MONTH, -1);
                Date minusOne = cal.getTime();
                returnValue = formatter.format(minusOne);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                Date newDate = formatter.parse(date);
                Calendar cal = Calendar.getInstance();
                cal.setTime(newDate);
                cal.add(Calendar.HOUR, +5);
                Date minusOne = cal.getTime();
                returnValue = formatter.format(minusOne);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return returnValue;
    }
    public void createEntry(){
        final BiometricsDO temp = new BiometricsDO();

        //temp.setData(72.5);
        //temp.setDatatype("temperature");
        //temp.setFlag(false);
        //temp.setTimestamp("2018:06:08:22:30:30");
        //new AWSOps(this).execute();
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
        String endStamp;
        String startStamp;

        getData(Temperature context, DynamoDBMapper dynamoDBMapper, String endStamp, String startStamp){
            activityReference = new WeakReference<>(context);
            this.dynamoDBMapper = dynamoDBMapper;
            this.endStamp = endStamp;
            this.startStamp = startStamp;
        }
        @Override
        protected BiometricsDO doInBackground(BiometricsDO... biometricsDOS) {

            BiometricsDO bio = new BiometricsDO();
            bio.setDatatype("temperature");

            Condition rangeKeyCondition = new Condition()
                    .withComparisonOperator(ComparisonOperator.BETWEEN)
                    .withAttributeValueList(
                            new AttributeValue().withS(startStamp),
                            new AttributeValue().withS(endStamp));

            DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                    .withHashKeyValues(bio)
                    .withRangeKeyCondition("timestamp", rangeKeyCondition)
                    .withConsistentRead(false);

            PaginatedList<BiometricsDO> resultQuery = dynamoDBMapper.query(BiometricsDO.class, queryExpression);

            Gson gson = new Gson();
            StringBuilder stringBuilder = new StringBuilder();

            // Loop through query results
            for (int i = 0; i < resultQuery.size(); i++) {
                String jsonFormOfItem = gson.toJson(resultQuery.get(i));
                stringBuilder.append(jsonFormOfItem + "\n\n");
            }

            // Add your code here to deal with the data result
            Log.d("Query result: ", stringBuilder.toString());

            if (resultQuery.isEmpty()) {
                System.out.println("There were no items matching your query.");
            }

            return dynamoDBMapper.load(BiometricsDO.class, "weight", "2018:07:06:23:45:04");
        }

        //use onPostExecute to send data from queries to be used in another method.
        @Override
        protected void onPostExecute(BiometricsDO result) {
            System.out.println(result.getData());
            System.out.println("OPE");
        }
    }

}

