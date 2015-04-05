package com.home.slideshow.preferences;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import com.home.slideshow.R;
import com.home.slideshow.util.DefPreferences;

public class TimePreference extends DialogPreference {

    private static final String KEY_TIME_START = "keyTimeStart";

    private TimePicker mTimePicker;

    public TimePreference( Context context, AttributeSet attributes ) {
        super(context, attributes);
    }

    @Override
    public void onBindDialogView( View view ) {
        super.onBindDialogView(view);

        int defHours,
            defMinutes;

        if ( getKey().equals( KEY_TIME_START ) ) {
            defHours   = DefPreferences.DEFAULT_TIME_START_HOUR;
            defMinutes = DefPreferences.DEFAULT_TIME_START_MINUTE;
        } else {
            defHours   = DefPreferences.DEFAULT_TIME_STOP_HOUR;
            defMinutes = DefPreferences.DEFAULT_TIME_STOP_MINUTE;
        }

        mTimePicker = (TimePicker) view.findViewById( R.id.prefTimePicker );
        mTimePicker.setCurrentHour(
                getSharedPreferences()
                        .getInt( getKey() + DefPreferences.Postfix.DOT_HOURS, defHours ) );
        mTimePicker.setCurrentMinute(
                getSharedPreferences()
                        .getInt( getKey() + DefPreferences.Postfix.DOT_MINUTES, defMinutes ) );
    }

    @Override
    public void onClick( DialogInterface dialog, int button ) {

        if ( button == Dialog.BUTTON_POSITIVE ) {
            SharedPreferences.Editor editor = getEditor();
            editor.putInt( getKey() + DefPreferences.Postfix.DOT_HOURS, mTimePicker.getCurrentHour() );
            editor.putInt( getKey() + DefPreferences.Postfix.DOT_MINUTES, mTimePicker.getCurrentMinute() );
            editor.commit();
        }
    }
}