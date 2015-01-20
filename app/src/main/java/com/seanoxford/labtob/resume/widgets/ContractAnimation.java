package com.seanoxford.labtob.resume.widgets;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

import com.seanoxford.labtob.resume.customviews.CustomChildLayout;
import com.seanoxford.labtob.resume.customviews.CustomImageView;
import com.seanoxford.labtob.resume.customviews.CustomRelativeLayout;

/**
 * Created by labtob on 1/14/2015.
 */
public class ContractAnimation extends Animation {

    public interface Listener{
      abstract void onContractFinish();
    }

    protected int[] mTopDimensions;

    protected CustomRelativeLayout mCustomRelativeLayout;
    protected CustomChildLayout mCustomChildLayout;
    protected CustomImageView mCustomImageView;
    protected TextView mSelectedLayoutTitle;

    protected int mTotalHeight;
    protected int mIndividualHeight;
    protected int mPreviousHeight = 0;

    protected float mIncrementedDownwardTransition;
    protected float mIncrementedUpwardTransition;
    protected float mDownwardDivisor = 0;
    protected float mUpwardDivisor = 0;

    protected int mTextTitlePosition;

    protected Listener mListener;

    public ContractAnimation(CustomRelativeLayout customRelativeLayout, CustomChildLayout customChildLayout, Listener listener) {
        mListener = listener;
        mCustomRelativeLayout = customRelativeLayout;
        mCustomChildLayout = customChildLayout;
        mCustomImageView = (CustomImageView) mCustomChildLayout.getChildAt(CustomChildLayout.CUSTOMIMAGEVIEW_POSITION);
        mSelectedLayoutTitle = (TextView) mCustomChildLayout.getChildAt(CustomChildLayout.TITLE_POSITION);

        mTotalHeight = mCustomRelativeLayout.getTotalHeight();
        mIndividualHeight = mCustomRelativeLayout.getIndividualHeight();

        mTextTitlePosition = mSelectedLayoutTitle.getTop();

        if(mCustomChildLayout.getViewPosition() != 0)
            mDownwardDivisor =  ((float) (mCustomRelativeLayout.getChildCount() - 1) / (float) mCustomChildLayout.getViewPosition());

        if(mCustomChildLayout.getViewPosition() != mCustomRelativeLayout.getChildCount() - 1)
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


        if(interpolatedTime == 1) {
            mCustomChildLayout.layout(0, mTopDimensions[mCustomChildLayout.getViewPosition()], mCustomChildLayout.getMeasuredWidth(), mTopDimensions[mCustomChildLayout.getViewPosition()] + mCustomChildLayout.getMeasuredHeight());
            mCustomImageView.layout(0, 0, mCustomChildLayout.getMeasuredWidth(), mCustomChildLayout.getMeasuredHeight());
        } else {
            int topIncrement = Math.round(mIncrementedDownwardTransition);
            int bottomIncrement = Math.round(mIncrementedUpwardTransition);

            mCustomChildLayout.layout(0, mTopDimensions[mCustomChildLayout.getViewPosition()] + topIncrement, mCustomChildLayout.getMeasuredWidth(), mTopDimensions[mCustomChildLayout.getViewPosition()] + mCustomChildLayout.getMeasuredHeight() + bottomIncrement);
            mCustomImageView.layout(0, 0, mCustomChildLayout.getMeasuredWidth(), mCustomChildLayout.getMeasuredHeight() - topIncrement + bottomIncrement);
        }

        mPreviousHeight = currentHeight;
    }


}
