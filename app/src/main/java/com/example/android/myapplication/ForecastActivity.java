package com.example.android.myapplication;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ForecastActivity extends AppCompatActivity {

    JSONObject data;
    ListAdapter adapter;
    Handler handler;
    String[] cities;
    public final String BASEURL = "https://api.openweathermap.org/data/2.5/forecast?q=";
    public final String APIKEY = "688cf4f48e0598da59a9043bcc14e35e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        adapter = new ListAdapter(this, new JSONObject());


        final String city = new CityPreference(ForecastActivity.this).getCity();
        Log.d("City is: ",city);
        cities = city.split(" ");
        Log.d("Processed City is: ",cities[0]+cities[1].toLowerCase());
        RecyclerView recyclerAdapter = findViewById(R.id.recyclerView);
        recyclerAdapter.setHasFixedSize(true);
        recyclerAdapter.setLayoutManager(new LinearLayoutManager(this));

        //set the new adapter with the posts
        adapter = new ListAdapter(getApplicationContext(),data);
        recyclerAdapter.setAdapter(adapter);

        (new WeatherFetchTask()).execute();
        //Log.d("Data: ",data.toString());

    }

    class WeatherFetchTask extends AsyncTask<Void, Void, JSONObject>{

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                URL url = new URL(BASEURL+cities[0]+cities[1].toLowerCase()+"&appid="+APIKEY);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String response = convertStreamToString(in);
                return new JSONObject(response);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null){
                adapter.data = jsonObject;
                adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(ForecastActivity.this,"Weather data don't exist",Toast.LENGTH_LONG).show();
            }
        }
    }



        //create recyclerview

        static String convertStreamToString(java.io.InputStream is){
        java.util.Scanner s= new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    }

