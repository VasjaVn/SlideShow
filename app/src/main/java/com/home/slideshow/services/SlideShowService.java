package com.home.slideshow.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.home.slideshow.R;
import com.home.slideshow.activities.SlideShowActivity;
import com.home.slideshow.util.DefPreferences;
import com.home.slideshow.util.SlideShowHttpAsyncTask;
import com.home.slideshow.util.SlideShowLocalAsyncTask;


public class SlideShowService extends Service {

    private SlideShowLocalAsyncTask mSlideShowLocalTask = null;
    private SlideShowHttpAsyncTask mSlideShowHttpTask  = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startSlideShow();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopSlideShow();
        super.onDestroy();
    }

    private void startSlideShow() {
        String currentTypeSourceOfImages = getTypeSourceOfImagesFromSettings();

        switch ( currentTypeSourceOfImages ) {

            case DefPreferences.TypeSourceOfImages.LOCAL:
                if ( mSlideShowHttpTask != null ) {
                    mSlideShowHttpTask.cancel( false );
                    mSlideShowHttpTask = null;
                }
                if ( mSlideShowLocalTask == null ) {
                    mSlideShowLocalTask = new SlideShowLocalAsyncTask();
                    mSlideShowLocalTask.execute();
                }
                break;

            case DefPreferences.TypeSourceOfImages.HTTP:
                if ( mSlideShowLocalTask != null ) {
                    mSlideShowLocalTask.cancel( false );
                    mSlideShowLocalTask = null;
                }
                if ( mSlideShowHttpTask == null ) {
                    mSlideShowHttpTask = new SlideShowHttpAsyncTask();
                    mSlideShowHttpTask.execute();
                }
                break;

            default:
                break;
        }
    }

    private void stopSlideShow() {
        if ( mSlideShowLocalTask != null ) {
            mSlideShowLocalTask.cancel( false );
            mSlideShowLocalTask = null;
        }

        if ( mSlideShowHttpTask != null ) {
            mSlideShowHttpTask.cancel( false );
            mSlideShowHttpTask = null;
        }
    }

    private String getTypeSourceOfImagesFromSettings() {
        Context context = SlideShowActivity.getInstance().getApplicationContext();

        String key      = context.getResources().getString( R.string.pr_key_type_source_of_images );
        String defValue = DefPreferences.DEFAULT_TYPE_SOURCE_OF_IMAGES;

        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getString(key, defValue);
    }
}
