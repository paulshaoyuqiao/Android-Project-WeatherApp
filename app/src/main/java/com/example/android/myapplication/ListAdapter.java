package com.example.android.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by paulshao on 3/7/18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CustomViewHolder> {


    private Context context;
    public JSONObject data;

    public ListAdapter(Context context, JSONObject data) {
        this.context = context;
        this.data = data;
        //Log.d("m",""+ data.size());
    }


    //inflate cardview and initiate the display
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false);
        return new CustomViewHolder(view);
    }

    //
    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        final JSONObject m;
        try {
            m = data.getJSONArray("list").getJSONObject(position);
            int celsius = m.getJSONObject("main").getInt("temp")-260;
            holder.listTemperature.setText("Temp:"+celsius+"ºC");
            holder.listHumidity.setText("Humidity:"+m.getJSONObject("main").getInt("humidity"));
            holder.listWeatherDescription.setText(m.getJSONArray("weather").getJSONObject(0).getString("description"));
            int Id = (int) m.getJSONArray("weather").getJSONObject(0).getInt("id")%6;
            int id = celsius % 6;
            switch(id) {
                case 0 : holder.listWeatherIcon.setImageResource(R.drawable.sunny);
                    break;
                case 1 : holder.listWeatherIcon.setImageResource(R.drawable.drizzle);
                    break;
                case 2 : holder.listWeatherIcon.setImageResource(R.drawable.foggy);
                    break;
                case 3 : holder.listWeatherIcon.setImageResource(R.drawable.cloudy);
                    break;
                case 4 : holder.listWeatherIcon.setImageResource(R.drawable.snowy);
                    break;
                case 5 : holder.listWeatherIcon.setImageResource(R.drawable.rainy);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public int getItemCount() {
        try {
            return data.getJSONArray("list").length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }



    /**
     * A card displayed in the RecyclerView
     */
    class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView listWeatherIcon;
        TextView listWeatherDescription;
        TextView listTemperature;
        TextView listHumidity;
        View listParentView;


        public CustomViewHolder (View itemView) {
            super(itemView);
            this.listWeatherIcon = itemView.findViewById(R.id.weatherIcon);
            this.listWeatherDescription = itemView.findViewById(R.id.description);
            this.listTemperature = itemView.findViewById(R.id.temperature);
            this.listHumidity = itemView.findViewById(R.id.Humidity);
            this.listParentView = itemView.findViewById(R.id.listParentView);

        }


    }
    }

