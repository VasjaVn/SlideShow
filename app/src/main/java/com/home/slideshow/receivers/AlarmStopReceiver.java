package com.home.slideshow.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.home.slideshow.services.SlideShowService;

public class AlarmStopReceiver extends BroadcastReceiver {
    public AlarmStopReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "AlarmStop", Toast.LENGTH_SHORT).show();
        context.stopService( new Intent( context, SlideShowService.class ) );
    }
}
