package com.home.slideshow.activities;

import android.preference.PreferenceActivity;
import android.os.Bundle;

import com.home.slideshow.R;


public class PreferencesActivity extends PreferenceActivity {

    public static PreferencesActivity sInstance;

    public PreferencesActivity() {
        sInstance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
