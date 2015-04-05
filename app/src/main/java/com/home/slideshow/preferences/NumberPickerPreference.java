package com.home.slideshow.preferences;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;

import com.home.slideshow.R;

public class NumberPickerPreference extends DialogPreference {

    private static final int MAX_VALUE_INTERVAL_IN_SECONDS     = 60;
    private static final int MIN_VALUE_INTERVAL_IN_SECONDS     =  1;
    private static final int DEFAULT_VALUE_INTERVAL_IN_SECONDS =  5;

    private NumberPicker mNumberPicker;
    private int          mCurrentValueInterval;

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        SharedPreferences sharedPreferences = getSharedPreferences();
        mCurrentValueInterval = sharedPreferences.getInt( getKey(), DEFAULT_VALUE_INTERVAL_IN_SECONDS );

        mNumberPicker = (NumberPicker) view.findViewById( R.id.prefNumberPicker );
        mNumberPicker.setMaxValue( MAX_VALUE_INTERVAL_IN_SECONDS );
        mNumberPicker.setMinValue( MIN_VALUE_INTERVAL_IN_SECONDS );
        mNumberPicker.setValue(mCurrentValueInterval);
    }

    @Override
    public void onClick( DialogInterface dialog, int button ) {
        super.onClick(dialog, button);

        if ( button == Dialog.BUTTON_POSITIVE ) {
            SharedPreferences.Editor editor = getEditor();
            editor.putInt( getKey(), mCurrentValueInterval );
            editor.commit();
        }
    }
}
