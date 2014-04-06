package com.nkhosla.hackny2014.hackny2014;

import android.app.Activity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class OrderHouseCleaning extends Activity {


    private String handybookUsername = "f6d322818cd888d8ced915b43e87e83d";
    private String handybookPassword = "b81e8959593c4391de9dd344d9813c32";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_house_cleaning);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("HNY14", "onStart called by cleaning selection");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d("HNY14","onPause called by cleaning");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("HNY14","onResume called by cleaning");

    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("HNY14","onStop called by cleaning");

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("HNY14","onDestroy called by cleaning");
    }


    public void onDialogDateSet(int year, int monthOfYear, int dayOfMonth) {
       Log.d("","cat");
    }

   public float getACleaningQuote(){
       // declare yo' return variables upfront, beyotch!
       float quoteValue = 0;


       HttpUriRequest request = new HttpGet(); // Or HttpPost(), depends on your needs
       String credentials = handybookUsername + ":" + handybookPassword;
       String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
       request.addHeader("Authorization", "Basic " + base64EncodedCredentials);

       HttpClient httpclient = new DefaultHttpClient();

       try {
           HttpResponse response = httpclient.execute(request);

           System.out.println(response);
           Log.d("HNY14",""+response);
       }
       catch(Exception e) {
           Log.d("HNY14","error in ordering get quote");
           Log.d("HNY14", ""+e);
       }
// You'll need to handle the exceptions thrown by execute()

       return quoteValue;


   }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order_house_cleaning, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
