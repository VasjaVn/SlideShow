package com.home.slideshow.receivers;

import android.content.Context;
import android.content.Intent;

import com.home.slideshow.activities.SlideShowActivity;
import com.home.slideshow.services.SlideShowService;
import com.home.slideshow.R;

public class DeviceBootReceiver extends DeviceBaseReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if ( intent.getAction().equals( Intent.ACTION_BOOT_COMPLETED ) ) {

            String key = context.getResources().getString( R.string.pr_key_reboot );

            if ( isChecked( context, key ) ) {

                Intent intentSSA = new Intent( context, SlideShowActivity.class );
                intentSSA.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                context.startActivity( intentSSA );

                try {
                    Thread.sleep( 1000 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                context.startService( new Intent( context, SlideShowService.class ) );
            }
        }
    }
}
