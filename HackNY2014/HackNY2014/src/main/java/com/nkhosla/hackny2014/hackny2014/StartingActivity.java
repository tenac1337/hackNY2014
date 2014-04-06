package com.nkhosla.hackny2014.hackny2014;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import java.util.HashMap;


import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;



public class StartingActivity extends FragmentActivity implements OnDateSetListener, TimePickerDialog.OnTimeSetListener  {
    final Calendar calendar = Calendar.getInstance();

    final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), isVibrate());
    final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);

    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);



        Button startButton = (Button) findViewById(R.id.dStartButton);
        startButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                passInitialDictionaryActivity();

            }
        });
    }


    public void selectADate(View v){
        datePickerDialog.setVibrate(isVibrate());
        datePickerDialog.setYearRange(1985, 2028);
        //datePickerDialog.setCloseOnSingleTapDay(isCloseOnSingleTapDay());
        datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
    }


    public void selectATime(View v){
        timePickerDialog.setVibrate(isVibrate());
        //timePickerDialog.setCloseOnSingleTapMinute(isCloseOnSingleTapMinute());
        timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
    }

    private boolean isVibrate() {
        return true;
    }

    private boolean isCloseOnSingleTapDay() {
        return false;
    }

    private boolean isCloseOnSingleTapMinute() {
        return false;
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        Toast.makeText(StartingActivity.this, "new date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        Toast.makeText(StartingActivity.this, "new time:" + hourOfDay + "-" + minute, Toast.LENGTH_LONG).show();
    }

    private void passInitialDictionaryActivity(){
        Log.d("HNY14", "passInitialDictionaryActivity called");
        EditText firstName = (EditText) findViewById(R.id.dFirstName);
        EditText lastName = (EditText) findViewById(R.id.dLastName);
        EditText eventAddressLine1 = (EditText) findViewById(R.id.dEventAddressLine1);
        EditText eventAddressLine2 = (EditText) findViewById(R.id.dEventAddressLine2);
        EditText eventAddressCity = (EditText) findViewById(R.id.dEventAddressCity);
        EditText eventState = (EditText) findViewById(R.id.dEventState);
        EditText eventZipCode = (EditText) findViewById(R.id.dEventZipCode);
        EditText eventGuestNumber = (EditText) findViewById(R.id.dGuestNumber);
        //DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
        //TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
        //String selectedDate = DateFormat.getDateInstance().format(dp.getCalendarView().getDate());

        HashMap<String,String> initialDictionary = new HashMap<String,String>();
        initialDictionary.put("first_name", firstName.getText().toString());
        initialDictionary.put("last_name", lastName.getText().toString());
        initialDictionary.put("event_address_line_1", eventAddressLine1.getText().toString());
        initialDictionary.put("event_address_line_2", eventAddressLine2.getText().toString());
        initialDictionary.put("event_address_city", eventAddressCity.getText().toString());
        initialDictionary.put("event_state", eventState.getText().toString());
        initialDictionary.put("event_zip_code", eventZipCode.getText().toString());
        initialDictionary.put("event_guest_number", eventGuestNumber.getText().toString());

        Intent getFoodIntent = new Intent(StartingActivity.this, OrderFood.class);
        getFoodIntent.putExtra("initial_dictionary", initialDictionary);
        startActivity(getFoodIntent);
        //Toast.makeText(TimeDate.this, "User selected " + strDateTime + "Time", Toast.LENGTH_LONG).show(); //Generate a toast only if you want
        //finish();   // If you want to continue on that TimeDateActivity
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("HNY14", "onStart called by start selection");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d("HNY14","onPause called by start");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("HNY14","onResume called by start");

    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("HNY14","onStop called by start");

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("HNY14","onDestroy called by start");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.starting, menu);
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
