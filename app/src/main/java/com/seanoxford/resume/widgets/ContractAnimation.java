package com.seanoxford.resume.widgets;

import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seanoxford.resume.customviews.CustomChildLayout;
import com.seanoxford.resume.customviews.CustomImageView;
import com.seanoxford.resume.customviews.CustomRelativeLayout;

public class ContractAnimation extends Animation {

    public interface Listener {
        abstract void onContractFinish();
    }

    protected CustomRelativeLayout mCustomRelativeLayout;
    protected CustomChildLayout mCustomChildLayout;
    protected CustomImageView mCustomImageView;
    protected TextView mSelectedLayoutTitle;

    protected int[] mTopDimensions;
    protected int mTotalHeight;
    protected int mIndividualHeight;
    protected int mPreviousHeight = 0;
    protected int mRemainderOffset;

    protected float mIncrementedDownwardTransition;
    protected float mIncrementedUpwardTransition;
    protected float mDownwardDivisor = 0;
    protected float mUpwardDivisor = 0;


    protected Listener mListener;

    public ContractAnimation(CustomRelativeLayout customRelativeLayout, CustomChildLayout customChildLayout, int remainderOffset, Listener listener) {
        mListener = listener;
        mRemainderOffset = remainderOffset;
        mCustomRelativeLayout = customRelativeLayout;
        mCustomChildLayout = customChildLayout;
        mCustomImageView = (CustomImageView) mCustomChildLayout.getChildAt(CustomChildLayout.CUSTOMIMAGEVIEW_POSITION);
        mSelectedLayoutTitle = (TextView) mCustomChildLayout.getChildAt(CustomChildLayout.TITLE_POSITION);
        mTotalHeight = mCustomRelativeLayout.getTotalHeight();
        mIndividualHeight = mCustomRelativeLayout.getIndividualHeight();

        if (mCustomChildLayout.getViewPosition() != 0)
            mDownwardDivisor = ((float) (mCustomRelativeLayout.getChildCount() - 1) / (float) mCustomChildLayout.getViewPosition());

        if (mCustomChildLayout.getViewPosition() != mCustomRelativeLayout.getChildCount() - 1)
            mUpwardDivisor = ((float) (mCustomRelativeLayout.getChildCount() - 1) / (float) (mCustomRelativeLayout.getChildCount() - 1 - mCustomChildLayout.getViewPosition()));

        mTopDimensions = customRelativeLayout.mPositionsArray;
        mPreviousHeight = mCustomChildLayout.getMeasuredHeight();

        setDuration(600);
        setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mCustomRelativeLayout.deactivateButtons();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mListener.onContractFinish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int totalGrowth = mTotalHeight - mIndividualHeight;
        int currentHeight = mTotalHeight - Math.round((totalGrowth * interpolatedTime));
        int heightDelta = mPreviousHeight - currentHeight;

        float downwardTransitionIncrement = mCustomChildLayout.getViewPosition() != 0 ? (float) heightDelta / mDownwardDivisor : 0;
        float upwardTransitionIncrement = mCustomChildLayout.getViewPosition() != mCustomRelativeLayout.getChildCount() - 1 ? (float) heightDelta / mUpwardDivisor : 0;

        mIncrementedDownwardTransition += downwardTransitionIncrement;
        mIncrementedUpwardTransition -= upwardTransitionIncrement;


        if (interpolatedTime == 1) {
            mCustomChildLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mIndividualHeight));
        } else {
            int topIncrement = Math.round(mIncrementedDownwardTransition);
            int bottomIncrement = Math.round(mIncrementedUpwardTransition);

            int layoutTop = topIncrement;
            int layoutBottom = mCustomChildLayout.getMeasuredHeight() + bottomIncrement;
            int imageBottom = mCustomChildLayout.getMeasuredHeight() - topIncrement + bottomIncrement;


            //TODO clean up logic
            if (mCustomRelativeLayout.hasPositiveHeightRemainder()) {
                layoutTop += mCustomChildLayout.getViewPosition();
                layoutBottom += mCustomChildLayout.getViewPosition();
            } else if( !mCustomRelativeLayout.hasPositiveHeightRemainder() && mCustomRelativeLayout.heightWasOdd()) {
                layoutTop += mCustomChildLayout.getViewPosition();
                layoutBottom += mCustomChildLayout.getViewPosition();
                layoutBottom -= mCustomRelativeLayout.getHeightRemainder();
                imageBottom -= mCustomRelativeLayout.getHeightRemainder();
            } else{
                layoutBottom -= mCustomRelativeLayout.getHeightRemainder();
                imageBottom -= mCustomRelativeLayout.getHeightRemainder();
            }

            mCustomChildLayout.layout(0, layoutTop, mCustomChildLayout.getMeasuredWidth(), layoutBottom);
            mCustomImageView.layout(0, 0, mCustomChildLayout.getMeasuredWidth(), imageBottom);
        }

        mPreviousHeight = currentHeight;
    }


}
