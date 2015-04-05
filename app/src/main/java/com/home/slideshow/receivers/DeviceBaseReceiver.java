package com.home.slideshow.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.preference.PreferenceManager;

public abstract class DeviceBaseReceiver extends BroadcastReceiver {

    protected boolean isChecked( Context context, String key ) {
        return PreferenceManager
                .getDefaultSharedPreferences( context ).getBoolean( key, false );
    }
}
