package com.home.slideshow.util;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.home.slideshow.R;
import com.home.slideshow.activities.SlideShowActivity;

public abstract class SlideShowBaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    protected int getIntervalFromSettings() {
        Context context = SlideShowActivity.getInstance().getApplicationContext();

        String key      = context.getResources().getString( R.string.pr_key_interval );
        int    defValue = DefPreferences.DEFAULT_INTERVAL_IN_SECONDS;

        return PreferenceManager
                .getDefaultSharedPreferences( context )
                .getInt( key, defValue );
    }

}
