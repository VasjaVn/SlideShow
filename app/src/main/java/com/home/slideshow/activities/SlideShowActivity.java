package com.home.slideshow.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.home.slideshow.R;
import com.home.slideshow.receivers.AlarmStartReceiver;
import com.home.slideshow.receivers.AlarmStopReceiver;
import com.home.slideshow.services.SlideShowService;
import com.home.slideshow.util.DefPreferences;

import java.util.Calendar;


public class SlideShowActivity extends ActionBarActivity {

    private static final String FULL_SCREEN = "full_screen";

    private static final int GET_TIME_START = 1;
    private static final int GET_TIME_STOP  = 2;

    private static SlideShowActivity sInstance = null;

    public static SlideShowActivity getInstance() { return sInstance; }

    private SharedPreferences.OnSharedPreferenceChangeListener mPrefChangeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                              WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView( R.layout.activity_slider_show );

        SlideShowActivity.sInstance = this;
        mPrefChangeListener         = new OnPreferenceChangeListener();

        registerForContextMenu( findViewById( R.id.rootLinearLayout) );

        //ImageView imageViewSliderShow = (ImageView) findViewById( R.id.imageViewSliderShow );
        //imageViewSliderShow.setOnClickListener( new OnTriplexClickListener( this ) );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager
                .getDefaultSharedPreferences( this )
                .unregisterOnSharedPreferenceChangeListener( mPrefChangeListener );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean( FULL_SCREEN, getSupportActionBar().isShowing() );
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if ( !savedInstanceState.getBoolean( FULL_SCREEN, false ) ) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_slider_show, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionMenuItemSelected( item );
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate( R.menu.menu_slider_show, menu );
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return actionMenuItemSelected( item );
    }


    private boolean actionMenuItemSelected( MenuItem item ) {

        switch ( item.getItemId() ) {

            case R.id.action_settings:
                startActivity(new Intent(this, PreferencesActivity.class));
                PreferenceManager
                        .getDefaultSharedPreferences( this )
                        .registerOnSharedPreferenceChangeListener(mPrefChangeListener);
                break;

            case R.id.action_start_slide_show:
                startService( new Intent( this, SlideShowService.class) );
                break;

            case R.id.action_stop_slide_show:
                stopService( new Intent( this, SlideShowService.class) );
                if ( !getSupportActionBar().isShowing() ) {
                    getSupportActionBar().show();
                }
                break;

            case R.id.action_full_screen_on_off:
                if ( getSupportActionBar().isShowing() ) {
                    getSupportActionBar().hide();
                } else {
                    getSupportActionBar().show();
                }
                break;

            case R.id.action_exit:
                stopService( new Intent( SlideShowActivity.this, SlideShowService.class ) );
                finish();
                break;

            default:
                return false;
        }
        return true;
    }



    private long getTimeInMilliseconds( int typeTime ) {
        String strKeyTime;
        int    defaultHours,
               defaultMinutes;

        switch ( typeTime ) {

            case GET_TIME_START:
                strKeyTime     = getResources().getString( R.string.pr_key_time_start );
                defaultHours   = DefPreferences.DEFAULT_TIME_START_HOUR;
                defaultMinutes = DefPreferences.DEFAULT_TIME_START_MINUTE;
                break;

            case GET_TIME_STOP:
                strKeyTime     = getResources().getString( R.string.pr_key_time_stop );
                defaultHours   = DefPreferences.DEFAULT_TIME_STOP_HOUR;
                defaultMinutes = DefPreferences.DEFAULT_TIME_STOP_MINUTE;
                break;

            default:
                return 0;
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( this );
        int hours   = sharedPreferences.getInt( strKeyTime + DefPreferences.Postfix.DOT_HOURS, defaultHours );
        int minutes = sharedPreferences.getInt( strKeyTime + DefPreferences.Postfix.DOT_MINUTES, defaultMinutes );

        Calendar calendar = Calendar.getInstance();
        calendar.set( Calendar.HOUR_OF_DAY, hours );
        calendar.set( Calendar.MINUTE,      minutes );
        calendar.set( Calendar.SECOND,      0 );
        calendar.set( Calendar.MILLISECOND, 0 );

        return calendar.getTimeInMillis();
    }



    private class OnPreferenceChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            AlarmManager alarmManager = (AlarmManager) getSystemService( Context.ALARM_SERVICE );

            if ( key.equals( getResources().getString( R.string.pr_key_time_start ) + DefPreferences.Postfix.DOT_HOURS ) ||
                 key.equals( getResources().getString( R.string.pr_key_time_start ) + DefPreferences.Postfix.DOT_MINUTES ) ) {

                Intent        intent               = new Intent( getApplicationContext(), AlarmStartReceiver.class );
                PendingIntent pendingIntent        = PendingIntent.getBroadcast( getApplicationContext(), 0, intent, 0 );
                long          triggerAtMillisStart = getTimeInMilliseconds( GET_TIME_START );

                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillisStart, pendingIntent);
            }

            if ( key.equals( getResources().getString( R.string.pr_key_time_stop ) + DefPreferences.Postfix.DOT_HOURS ) ||
                 key.equals( getResources().getString( R.string.pr_key_time_stop ) + DefPreferences.Postfix.DOT_MINUTES ) ) {

                Intent        intent              = new Intent( getApplicationContext(), AlarmStopReceiver.class );
                PendingIntent pendingIntent       = PendingIntent.getBroadcast( getApplicationContext(), 0, intent, 0 );
                long          triggerAtMillisStop = getTimeInMilliseconds( GET_TIME_STOP );

                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillisStop, pendingIntent);
            }
        }
    }
}



class OnTriplexClickListener implements View.OnClickListener {

    private static final int DELAYED_FOR_TRIPLEX_CLICK_IN_MILLISECONDS = 500;

    private Context  mContext;
    private int      mCountClick = 0;
    private Handler  mHandler    = new Handler();
    private Runnable mRunnable   = new Runnable() {
        @Override
        public void run() {
            mCountClick = 0;
        }
    };

    public OnTriplexClickListener( Context context ) {
        this.mContext = context;
    }

    @Override
    public void onClick( View view ) {
        mCountClick++;

        if ( mCountClick == 1 ) {
            mHandler.postDelayed( mRunnable, DELAYED_FOR_TRIPLEX_CLICK_IN_MILLISECONDS);
        } else if ( mCountClick == 3 ) {
            Toast.makeText(mContext, "Triplex Clicked", Toast.LENGTH_SHORT).show();
            WindowManager wm = ( WindowManager ) SlideShowActivity.getInstance().getSystemService( Context.WINDOW_SERVICE );
            Window window = SlideShowActivity.getInstance().getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        }
    }
}