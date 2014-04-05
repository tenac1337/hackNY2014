package com.nkhosla.hackny2014.hackny2014;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class ContactSelection extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_selection);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("HNY14", "onStart called by contact selection");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d("HNY14","onPaus called by Contact");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("HNY14","onResume called by contact");

    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d("HNY14","onStop called by contact");

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("HNY14","onDestroy called by contact");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact_selection, menu);
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
