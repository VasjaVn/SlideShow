package com.home.slideshow.util;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.home.slideshow.R;
import com.home.slideshow.activities.SlideShowActivity;

public class SlideShowLocalAsyncTask extends SlideShowBaseAsyncTask<Void, Void, Void> {

    private int[] mSlideLocalIDS = {
            R.drawable.img_1, R.drawable.img_2,
            R.drawable.img_3, R.drawable.img_4,
            R.drawable.img_5, R.drawable.img_6,
            R.drawable.img_7, R.drawable.img_8,
            R.drawable.img_9, R.drawable.img_10
    };

    private int mCurrentSlideIndex;
    private int mCountSlides;
    private int mPrefInterval;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mCurrentSlideIndex = 0;
        mCountSlides       = mSlideLocalIDS.length - 1;
        mPrefInterval      = getIntervalFromSettings();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

        Animation animationSliderShow =
                AnimationUtils.loadAnimation( SlideShowActivity.getInstance(), R.anim.anim_slider_show);
        ImageView imageSliderShow =
                (ImageView) SlideShowActivity.getInstance().findViewById( R.id.imageViewSliderShow );

        imageSliderShow.setImageResource( mSlideLocalIDS[mCurrentSlideIndex] );
        imageSliderShow.startAnimation( animationSliderShow );

        mCurrentSlideIndex++;
        if ( mCurrentSlideIndex > mCountSlides ) {
            mCurrentSlideIndex = 0;
        }
    }

    @Override
    protected Void doInBackground(Void... params) {

        boolean alwaysTRUE = true;

        while ( alwaysTRUE ) {

            if ( isCancelled() ) {
                break;
            }
            publishProgress();

            try {
                //mPrefInterval = getIntervalFromSettings();
                Thread.sleep( mPrefInterval * 1000 );
            } catch (InterruptedException e) {
                // NOP
            }
        }
        return null;
    }
}
