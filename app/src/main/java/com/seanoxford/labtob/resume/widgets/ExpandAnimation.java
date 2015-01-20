package com.seanoxford.labtob.resume.widgets;

import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

import com.seanoxford.labtob.resume.customviews.CustomChildLayout;
import com.seanoxford.labtob.resume.customviews.CustomImageView;
import com.seanoxford.labtob.resume.customviews.CustomRelativeLayout;

/**
 * Created by labtob on 1/5/2015.
 */
public class ExpandAnimation extends Animation {

    protected int[] mTopDimensions;

    protected CustomRelativeLayout mCustomRelativeLayout;
    protected CustomChildLayout mCustomChildLayout;
    protected CustomImageView mCustomImageView;
    protected TextView mSelectedLayoutTitle;

    protected int mTotalHeight;
    protected int mIndividualHeight;
    protected int mPreviousHeight = 0;

    protected float mIncrementedUpwardTransition;
    protected float mIncrementedDownwardTransition;
    protected float mUpwardDivisor = 0;
    protected float mDownwardDivisor = 0;

    protected int mTextTitlePosition;

    protected boolean mToggle = true;
    protected boolean mIsMiddleView = false;






    public ExpandAnimation(CustomRelativeLayout customRelativeLayout, CustomChildLayout customChildLayout){
        mCustomRelativeLayout = customRelativeLayout;
        mCustomChildLayout = customChildLayout;
        mCustomImageView = (CustomImageView) mCustomChildLayout.getChildAt(CustomChildLayout.CUSTOMIMAGEVIEW_POSITION);
        mSelectedLayoutTitle = (TextView) mCustomChildLayout.getChildAt(CustomChildLayout.TITLE_POSITION);

        mTotalHeight = mCustomRelativeLayout.getTotalHeight();
        mIndividualHeight = mCustomRelativeLayout.getIndividualHeight();

        if(mCustomRelativeLayout.getChildCount() % 2 == 1)
            if(mCustomChildLayout.getViewPosition() == mCustomRelativeLayout.getChildCount() / 2)
                mIsMiddleView = true;

        mTextTitlePosition = mSelectedLayoutTitle.getTop();

        if(mCustomChildLayout.getViewPosition() != 0)
            mUpwardDivisor =  ((float) (mCustomRelativeLayout.getChildCount() - 1) / (float) mCustomChildLayout.getViewPosition());

        if(mCustomChildLayout.getViewPosition() != mCustomRelativeLayout.getChildCount() - 1)
            mDownwardDivisor = ((float) (mCustomRelativeLayout.getChildCount() - 1) / (float) (mCustomRelativeLayout.getChildCount() - 1 - mCustomChildLayout.getViewPosition()));

        mTopDimensions = customRelativeLayout.mPositionsArray;
        mPreviousHeight = mCustomChildLayout.getMeasuredHeight();

        setDuration(600);
        setInterpolator(new AccelerateDecelerateInterpolator());
        setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mCustomRelativeLayout.deactivateButtons();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCustomChildLayout.setClickable(true);
                mPreviousHeight = mCustomChildLayout.getMeasuredHeight();
                mIncrementedDownwardTransition = 0;
                mIncrementedUpwardTransition = 0f;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int totalGrowth = mTotalHeight - mIndividualHeight;
        int currentHeight = mIndividualHeight + Math.round(totalGrowth * interpolatedTime);

        int heightDelta =  currentHeight - mPreviousHeight;

        float upwardTransitionIncrement = mCustomChildLayout.getViewPosition() != 0 ? (float) heightDelta / mUpwardDivisor : 0;
        float downwardTransitionIncrement = mCustomChildLayout.getViewPosition() != mCustomRelativeLayout.getChildCount() - 1 ? (float) heightDelta / mDownwardDivisor : 0;

        mIncrementedUpwardTransition -= upwardTransitionIncrement;
        mIncrementedDownwardTransition += downwardTransitionIncrement;

        Log.d("iii", String.format("up: %f, upIncrement: %f, down: %f, downIncrement: %f, time: %f", mIncrementedUpwardTransition, upwardTransitionIncrement, mIncrementedDownwardTransition, downwardTransitionIncrement, interpolatedTime));

        //TODO sometimes shit doesn't go to top all the way
        if(interpolatedTime == 1) {
            Log.d("jjj", "height remainder: " + mCustomRelativeLayout.getHeightRemainder());
            mCustomChildLayout.layout(0, 0, mCustomChildLayout.getMeasuredWidth(), mCustomRelativeLayout.getTotalHeight() + mCustomRelativeLayout.getHeightRemainder());
            mCustomImageView.layout(0, 0, mCustomChildLayout.getMeasuredWidth(), mCustomRelativeLayout.getTotalHeight() + mCustomRelativeLayout.getHeightRemainder());
        } else {
            int topIncrement;
            int bottomIncrement;

            if(!mIsMiddleView){
                Log.d("juj", "normal");
                topIncrement = Math.round(mIncrementedUpwardTransition);
                bottomIncrement = Math.round(mIncrementedDownwardTransition);
            } else if(!mToggle && mIsMiddleView) {
                Log.d("juj", "toggle 1");
                topIncrement = Math.round(mIncrementedUpwardTransition);
                bottomIncrement = (int) mIncrementedDownwardTransition;
                mToggle = true;
            } else {
                Log.d("juj", "toggle 2");
                topIncrement = (int) mIncrementedUpwardTransition;
                bottomIncrement = Math.round(mIncrementedDownwardTransition);
                mToggle = false;
            }

            mCustomChildLayout.layout(0, mTopDimensions[mCustomChildLayout.getViewPosition()] + topIncrement, mCustomChildLayout.getMeasuredWidth(), mTopDimensions[mCustomChildLayout.getViewPosition()] + mCustomChildLayout.getMeasuredHeight() + bottomIncrement);
            mCustomImageView.layout(0, 0, mCustomChildLayout.getMeasuredWidth(), mCustomChildLayout.getMeasuredHeight() - topIncrement + bottomIncrement);
        }

        mPreviousHeight = currentHeight;
    }
}
