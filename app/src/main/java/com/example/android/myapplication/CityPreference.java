package com.example.android.myapplication;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by paulshao on 3/5/18.
 */

public class CityPreference {
    SharedPreferences prefs;

    public CityPreference(Activity activity){
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    // If the user has not chosen a city yet, return
    // Sydney as the default city
    String getCity(){
        return prefs.getString("city", "Atlanta, US");
    }

    void setCity(String city){
        prefs.edit().putString("city", city).commit();
    }
}
