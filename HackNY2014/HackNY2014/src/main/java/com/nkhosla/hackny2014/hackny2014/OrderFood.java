package com.nkhosla.hackny2014.hackny2014;

import android.app.Activity;
import android.app.ListActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.HashMap;

public class Restaurant {
    String mTitle;
    String mPhoneNumber;
    String mRating;
    String mCuisine;
    HashMap<String, String> mMenu;
    String mDescription;
    String mId;

    public Restaurant(String title, String phoneNumber, String rating, String cuisine, HashMap<String, String> menu, String description, String id){
        mTitle = title;
        mPhoneNumber = phoneNumber;
        mRating = rating;
        mCuisine = cuisine;
        mMenu = menu;
        mDescription = description;
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public String getRating() {
        return mRating;
    }

    public String getCuisine() {
        return mCuisine;
    }

    public HashMap<String, String> getMenu() {
        return mMenu;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getId() {
        return mId;
    }
}

public class OrderFood extends ListActivity {
    String first_name;
    String last_name;
    String event_address_line_1;
    String event_address_line_2;
    String event_address_city;
    String event_state;
    String event_zip_code;
    String event_guest_number;
    String event_date;
    String event_time;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_order_food);
        Intent receiveInitialDictIntent = getIntent();
        HashMap<String, String> init_dict = (HashMap<String, String>) receiveInitialDictIntent.getSerializableExtra("initial_dictionary");

        first_name = init_dict.get("first_name");
        last_name = init_dict.get("last_name");
        event_address_line_1 = init_dict.get("event_address_line_1");
        event_address_line_2 = init_dict.get("event_address_line_2");
        event_address_city = init_dict.get("event_address_city");
        event_state = init_dict.get("event_state");
        event_zip_code = init_dict.get("event_zip_code");
        event_guest_number = init_dict.get("event_guest_number");
        event_date = init_dict.get("event_date");
        event_time = init_dict.get("event_time");

        Log.i("init_dict", event_address_line_1);
        Log.i("init_dict", event_address_line_2);
        Log.i("init_dict", event_address_city);
        Log.i("init_dict", event_state);
        Log.i("init_dict", event_zip_code);
        Log.i("init_dict", event_date);
        Log.i("init_dict", event_time);

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("HNY14", "onStart called by food selection");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d("HNY14","onPaus called by food");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("HNY14","onResume called by food");

    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("HNY14","onStop called by food");

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("HNY14","onDestroy called by food");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order_food, menu);
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
