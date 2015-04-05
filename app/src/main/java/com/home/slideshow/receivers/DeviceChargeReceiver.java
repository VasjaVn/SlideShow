package com.home.slideshow.receivers;

import android.content.Context;
import android.content.Intent;

import com.home.slideshow.activities.SlideShowActivity;
import com.home.slideshow.services.SlideShowService;
import com.home.slideshow.R;


public class DeviceChargeReceiver extends DeviceBaseReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if ( intent.getAction().equals( Intent.ACTION_POWER_CONNECTED ) ) {

            String key = context.getResources().getString( R.string.pr_key_charge );

            if ( isChecked( context, key ) ) {

                if ( SlideShowActivity.getInstance() == null ) {

                    Intent intentSSA = new Intent( context, SlideShowActivity.class );
                    intentSSA.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                    context.startActivity( intentSSA );

                    try {
                        Thread.sleep( 1000 );
                    } catch ( InterruptedException e ) {
                        // NOP
                    }

                    context.startService( new Intent( context, SlideShowService.class ) );
                } else {
                    context.startService( new Intent( context, SlideShowService.class ) );
                }
            }
        }
    }
}
