package com.home.slideshow.preferences;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.home.slideshow.R;
import com.home.slideshow.util.DefPreferences;

public class SeekBarPreference extends DialogPreference implements SeekBar.OnSeekBarChangeListener {

    private static final int MIN_VALUE =  1;
    private static final int MAX_VALUE = 60;

    private SeekBar  mSeekBar;
    private TextView mTextView;
    private int      mCurrentValueInterval;

    public SeekBarPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        mCurrentValueInterval =
                getSharedPreferences()
                        .getInt( getKey(), DefPreferences.DEFAULT_INTERVAL_IN_SECONDS );

        mSeekBar  = (SeekBar) view.findViewById( R.id.prefSeekBar );
        mSeekBar.setMax( MAX_VALUE );
        mSeekBar.setProgress( mCurrentValueInterval );
        mSeekBar.setOnSeekBarChangeListener( this );

        mTextView = (TextView) view.findViewById( R.id.prefTextView );
        mTextView.setText( "" + DefPreferences.DEFAULT_INTERVAL_IN_SECONDS + " second(s)"  );

    }

    @Override
    public void onClick(DialogInterface dialog, int button) {
        super.onClick(dialog, button);

        if ( button == Dialog.BUTTON_POSITIVE ) {
            SharedPreferences.Editor editor = getEditor();
            editor.putInt( getKey(), mCurrentValueInterval );
            editor.commit();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if ( progress < MIN_VALUE ) {
            mTextView.setText( String.valueOf(MIN_VALUE + " second(s)") );
            mCurrentValueInterval = MIN_VALUE;
        } else {
            mTextView.setText( String.valueOf(progress + " second(s)") );
            mCurrentValueInterval = progress;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}
