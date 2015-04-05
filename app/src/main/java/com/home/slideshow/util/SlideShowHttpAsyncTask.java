package com.home.slideshow.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.home.slideshow.R;
import com.home.slideshow.activities.SlideShowActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SlideShowHttpAsyncTask extends SlideShowBaseAsyncTask<Void, Bitmap, Void> {

    private String[] mSlideUrls = new String[] {
            "http://lorempixel.com/1280/720/sports",
            "http://lorempixel.com/1280/720/nature",
            "http://lorempixel.com/1280/720/people",
            "http://lorempixel.com/1280/720/city"
    };

    private int mCurrentSlideIndex;
    private int mCountSlides;
    private int mPrefInterval;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mCurrentSlideIndex = 0;
        mCountSlides       = mSlideUrls.length - 1;
        mPrefInterval      = getIntervalFromSettings();
    }

    @Override
    protected void onProgressUpdate(Bitmap... bmp) {
        super.onProgressUpdate(bmp);

        Animation animationSliderShow =
                AnimationUtils.loadAnimation( SlideShowActivity.getInstance(), R.anim.anim_slider_show );
        ImageView imageSliderShow =
                (ImageView) SlideShowActivity.getInstance().findViewById( R.id.imageViewSliderShow );

        imageSliderShow.setImageBitmap( bmp[0] );
        imageSliderShow.startAnimation( animationSliderShow );
    }

    @Override
    protected Void doInBackground(Void... params) {

        URL    url;
        Bitmap bmp;

        boolean isUrlWasPublished,
                alwaysTRUE = true;

        while ( alwaysTRUE ) {

            if ( isCancelled() ) {
                break;
            }
            isUrlWasPublished = false;

            try {
                url = new URL( mSlideUrls[ mCurrentSlideIndex ] );

                try {
                    bmp = BitmapFactory.decodeStream( url.openConnection().getInputStream() );
                    publishProgress( bmp );
                    isUrlWasPublished = true;

                } catch ( IOException e ) {
                    // NOP
                }
            } catch ( MalformedURLException e ) {
                // NOP
            } finally {

                mCurrentSlideIndex++;
                if ( mCurrentSlideIndex > mCountSlides ) {
                    mCurrentSlideIndex = 0;
                }
            }

            if ( isUrlWasPublished ) {
                try {
                    //mPrefInterval = getIntervalFromSettings();
                    Thread.sleep( mPrefInterval * 1000 );
                } catch (InterruptedException e) {
                    // NOP
                }
            }
        }
        return null;
    }
}
