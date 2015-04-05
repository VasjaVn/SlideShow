package com.home.slideshow.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.home.slideshow.services.SlideShowService;

public class AlarmStartReceiver extends BroadcastReceiver {

    public AlarmStartReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "AlarmStart", Toast.LENGTH_SHORT).show();
        context.startService( new Intent( context, SlideShowService.class ) );
    }
}
