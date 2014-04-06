package com.nkhosla.hackny2014.hackny2014;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.Calendar;
import java.util.HashMap;


public class OrderHouseCleaning extends FragmentActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


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

    String cleaning_date;


    // Make a handler to handle ther unnables
    Handler handler = new Handler();
    HttpClient httpclient;
    HttpResponse response;
    HttpUriRequest request;
    String urlString;
    String price;
    boolean quoteWasIssued =false;
    String numBeds;
    String numBaths;

    final Calendar calendar = Calendar.getInstance();

    final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), isVibrate());
    final TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);

    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";

    // Make a runnable to get the info from api
    Runnable getAQuote = new Runnable() {
        @Override
        public void run() {
            //. .....

            request = new HttpGet(urlString); // Or HttpPost(), depends on your needs
            String credentials = handybookUsername + ":" + handybookPassword;
            String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            request.addHeader("Authorization", "Basic " + base64EncodedCredentials);

            //httpclient = new DefaultHttpClient();
            httpclient = getNewHttpClient();

            try {

                response = httpclient.execute(request);


                Log.d("HNY14",""+response);




                // Extract the quote from the response


                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                StringBuilder builder = new StringBuilder();
                for (String line = null; (line = reader.readLine()) != null;) {
                    builder.append(line).append("\n");
                }
                JSONTokener tokener = new JSONTokener(builder.toString());

                JSONObject someObject = new JSONObject(tokener);
                Log.d("","This is the JSONOBJct"+someObject);
                // JSONArray finalJSONResult = someObject.getJSONArray("price");

                /**
                 ArrayList<String> finalArray = new ArrayList<String>();


                 if (finalJSONResult != null) {
                 int len = finalJSONResult.length();
                 for (int i=0;i<len;i++){
                 finalArray.add(finalJSONResult.get(i).toString());
                 }
                 }
                 **/



                Log.d("",someObject.getString("price"));

                price = someObject.getString("price");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateButtonText(price);
                    }
                });

            }
            catch(Exception e) {
                Log.d("HNY14","error in ordering get quote");
                Log.d("HNY14", ""+e);
            }

        }
    };


    // Runnable to book the appointment
    Runnable bookAPPT = new Runnable() {

        @Override
        public void run() {
            //. .....


            urlString = "http://www.clothestwin.com/api/v2/partners/bookings?"+
                    "bookings[user_id]=1"+
                    "&bookings[listing_id]=1"+
                    "&bookings[first_name]="+sProcess(first_name)+
                    "&bookings[last_name]="+sProcess(last_name)+
                    "&bookings[email]=nathan@nkhosla.com"+
                    "&bookings[phone]=4125599354"+
                    "&bookings[zipcode]="+sProcess(event_zip_code)+
                    "&bookings[address_line_one]="+sProcess(event_address_line_1)+
                    "&bookings[address_line_two]="+sProcess(event_address_line_2)+
                    "&bookings[address_city]="+sProcess(event_address_city)+
                    "&bookings[address_state]="+sProcess(event_state)+
                    "&bookings[country]=US"+
                    "&bookings[bedrooms]=" +sProcess(numBeds)+
                    "&bookings[bathrooms]="+sProcess(numBaths)+
                    "&bookings[service]=home_cleaning"+
                    "&bookings[time]="+calendar.get(Calendar.YEAR) + ":" + formatSingleDigit(calendar.get(Calendar.MONTH)) + ":" + formatSingleDigit(calendar.get(Calendar.YEAR)) +"+"+"08:30"+
                    "&bookings[full_beds]=0"+
                    "&bookings[queen_beds]=0"+
                    "&bookings[king_beds]=0";


            Log.d("HNY14_BOOK",urlString);
            request = new HttpPost(urlString); // Or HttpPost(), depends on your needs
            String credentials = handybookUsername + ":" + handybookPassword;
            String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            request.addHeader("Authorization", "Basic " + base64EncodedCredentials);
            Log.d("HNY14_BOOK","added header");

            //httpclient = new DefaultHttpClient();
            httpclient = getNewHttpClient();

            try {

                response = httpclient.execute(request);

                //System.out.println(response);
                Log.d("HNY14_BOOK",""+response);


                // Extract the quote from the response


                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                StringBuilder builder = new StringBuilder();
                for (String line = null; (line = reader.readLine()) != null;) {
                    builder.append(line).append("\n");
                }
                JSONTokener tokener = new JSONTokener(builder.toString());
                Log.d("hhhh",""+tokener);

                JSONObject someObject = new JSONObject(tokener);
                Log.d("","This is the JSONOBJct"+someObject);
                // JSONArray finalJSONResult = someObject.getJSONArray("price");

                /**
                 ArrayList<String> finalArray = new ArrayList<String>();


                 if (finalJSONResult != null) {
                 int len = finalJSONResult.length();
                 for (int i=0;i<len;i++){
                 finalArray.add(finalJSONResult.get(i).toString());
                 }
                 }
                 **/




                Log.d("", someObject.getString("price"));

                price = someObject.getString("price");

            }
            catch(Exception e) {
                Log.d("HNY14","error in ordering get quote");
                Log.d("HNY14", ""+e);
            }

            runOnUiThread( new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(OrderHouseCleaning.this, "Your cleaning was booked for "+calendar.get(Calendar.YEAR) + ":" + formatSingleDigit(calendar.get(Calendar.MONTH)) + ":" + formatSingleDigit(calendar.get(Calendar.YEAR)) +" "+"08:30", Toast.LENGTH_LONG).show();

                }
            });


        }


    };

    public String formatSingleDigit(int i){
        String ret = Integer.toString(i);
        if(i<11){
            ret = "0"+Integer.toString(i);
        }

        return ret;

    }



    public HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }


    private String handybookUsername = "f6d322818cd888d8ced915b43e87e83d";
    private String handybookPassword = "b81e8959593c4391de9dd344d9813c32";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_house_cleaning);

        Intent receiveInitialDictIntent = getIntent();
        HashMap<String, String> init_dict = (HashMap<String, String>) receiveInitialDictIntent.getSerializableExtra("initial_dictionary");

        first_name = init_dict.get("first_name");
        last_name = init_dict.get("last_name");
        event_address_line_1 = init_dict.get("event_address_line_1");
        event_address_line_2 = init_dict.get("event_address_line_2");
        event_address_city = init_dict.get("event_address_city");
        event_state = init_dict.get("event_state");
        event_zip_code = init_dict.get("event_zip_code");
        Log.d("cat worked damnit", event_zip_code);
        event_guest_number = init_dict.get("event_guest_number");
        event_date = init_dict.get("event_date");
        event_time = init_dict.get("event_time");
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

public String sProcess(String s){
    String ss = s.replace(' ','+');
    return ss;
}

    public void quoteButtonWasClicked(View v){

        TextView tv = (TextView) findViewById(R.id.numBedRoomsText);

        numBeds = tv.getText().toString();

        tv=(TextView) findViewById(R.id.numBathroomsText);
        numBaths = tv.getText().toString();


        if(quoteWasIssued){
            // If quote was issued, book the damn cleaning
            Log.d("HNY14","started get a quote book method");

            Thread thread = new Thread(bookAPPT);

            thread.start();


        }

        else {
            // else, issue the quote

            Log.d("HNY14","started get a quote get method");
            // declare yo' return variables upfront, beyotch!
            float quoteValue = 0;

            Log.d("HNY14",numBaths);
            Log.d("HNY14",numBeds);
            Log.d("HNY14",event_zip_code);

            urlString = "http://www.clothestwin.com/api/v2/partners/bookings/quote?quote[bathrooms]="+numBaths
                    +"&quote[bedrooms]="+numBeds
                    +"&quote[country]=US&quote[zipcode]="+event_zip_code;

            //URL url = new URL(urlString);




            //Run the runnable usig the handler
            // handler.post(getAQuote);

            Thread thread = new Thread(getAQuote);

            thread.start();

            Button quoteButton = (Button) findViewById(R.id.quoteButton);
            //Log.d("HNY14PRICE",price);
            //quoteButton.setText(price);
            //Log.d("HNY14PRICE2",price);
            //quoteButton.setTextColor(Color.parseColor("#FF3D4D"));
            //Log.d("HNY14PRICE3",price);
            quoteWasIssued = true;
        }
    }

    public void updateButtonText(String pricee) {
        Button quoteButton = (Button) findViewById(R.id.quoteButton);
        //quoteButton.setText(price);
        quoteButton.setText("$"+pricee);
        quoteButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 90);
        //Log.d("HNY14PRICE2",price);
        quoteButton.setTextColor(Color.parseColor("#FF3D4D"));
    }

    public void setADate(View v){
        selectADate(v);
        cleaning_date = calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.YEAR);

        TextView tv = (TextView) findViewById(R.id.cleaningDate);
        tv.setText(event_date);
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
        Toast.makeText(OrderHouseCleaning.this, "Date Selected:" + " " + month + "-" + day + "-" + year, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        Toast.makeText(OrderHouseCleaning.this, "Time Selected:" + " " + hourOfDay + ":" + minute, Toast.LENGTH_LONG).show();
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
