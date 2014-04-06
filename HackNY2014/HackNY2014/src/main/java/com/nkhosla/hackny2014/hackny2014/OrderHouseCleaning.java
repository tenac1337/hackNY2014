package com.nkhosla.hackny2014.hackny2014;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.X509TrustManager;

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;


public class OrderHouseCleaning extends Activity {

    // Make a handler to handle ther unnables
    Handler handler = new Handler();
    HttpClient httpclient;
    HttpResponse response;
    HttpUriRequest request;
    String urlString;
    String price;

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

                System.out.println(response);
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

            }
            catch(Exception e) {
                Log.d("HNY14","error in ordering get quote");
                Log.d("HNY14", ""+e);
            }

        }
    };





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

   public void getACleaningQuote(View v){
       Log.d("HNY14","started get a quote method");
       // declare yo' return variables upfront, beyotch!
       float quoteValue = 0;



       String zipCode = "10007";
       String numBedrooms = "1";
       String numBathrooms =  "1";


    urlString = "http://www.clothestwin.com/api/v2/partners/bookings/quote?quote[bathrooms]="+numBathrooms
            +"&quote[bedrooms]="+numBedrooms
            +"&quote[country]=US&quote[zipcode]="+zipCode;

    //URL url = new URL(urlString);




       //Run the runnable usig the handler
      // handler.post(getAQuote);

       Thread thread = new Thread(getAQuote);

       thread.start();

       TextView quoteTV = (TextView) findViewById(R.id.quoteText_cleaning);
       quoteTV.setText(price);



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
