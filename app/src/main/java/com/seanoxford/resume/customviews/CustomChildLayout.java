package com.seanoxford.resume.customviews;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class CustomChildLayout extends RelativeLayout {

    public static final int CUSTOMIMAGEVIEW_POSITION = 0;
    public static final int TITLE_POSITION = 1;
    public static final int TITLE_VIEW_ID = 72;
    private static final int FRAGMENT_WRAPPER_ID = 2;

    protected CustomImageView mImageView;
    protected TextView mTitle;
    protected RelativeLayout mDetailsLayout;
    protected FragmentManager mFragmentManager;
    protected Fragment mFragment;

    protected Context mContext;
    protected Integer mViewPosition;
    protected Integer mTitleTextCenter;
    protected int mLayout;
    protected boolean mIsExpanded = false;

    public CustomChildLayout(Context context, String text, String color, int image, Typeface typeface, Fragment fragment) {
        super(context);
        mFragment = fragment;
        mContext = context;
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initImageView(image, color);
        initTitle(text, typeface);
    }

    public CustomChildLayout(Context context, String text, String color, int image, Typeface typeface, int layout) {
        super(context);
        mContext = context;
        if (layout != 0)
            mLayout = layout;
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        initImageView(image, color);
        initTitle(text, typeface);

    }

    private void initImageView(int image, String color) {
        mImageView = new CustomImageView(mContext);
        mImageView.setImageResource(image);
        mImageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mImageView.setColor(color);
        addView(mImageView);
    }

    private void initTitle(String text, Typeface typeface) {
        mTitle = new TextView(mContext);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 48);
        mTitle.setText(text);
        mTitle.setTextColor(Color.parseColor("#FFFFFF"));
        mTitle.setTypeface(typeface);
        mTitle.setId(TITLE_VIEW_ID);
        addView(mTitle);
    }

    public void onExpanded() {
        mIsExpanded = true;
        //To position added fragment within parent
        if (mFragmentManager != null) {
            if (mDetailsLayout == null) {
                mDetailsLayout = new RelativeLayout(mContext);
                LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                params.addRule(BELOW, TITLE_VIEW_ID);
                mDetailsLayout.setLayoutParams(params);

                FragmentTransaction fragTransaction = mFragmentManager.beginTransaction();


                //TODO find a way to make this not retarded
                mDetailsLayout.setId(mViewPosition + 1);

                fragTransaction.add(mDetailsLayout.getId(), mFragment);
                fragTransaction.commit();
                addView(mDetailsLayout);
                mDetailsLayout.setVisibility(View.VISIBLE);

            } else {
                mDetailsLayout.setVisibility(View.VISIBLE);
            }
        } else {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mDetailsLayout = (RelativeLayout) inflater.inflate(getLayout(), null);
            RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(BELOW, TITLE_VIEW_ID);
            mDetailsLayout.setLayoutParams(params);
            addView(mDetailsLayout);
        }
    }

    public void onCollapse() {
        mDetailsLayout.setVisibility(View.GONE);
    }

    public int getViewPosition() {
        return mViewPosition;
    }

    public void setViewPosition(int n) {
        mViewPosition = n;
    }

    public void setScaleToFill(boolean toScale) {
        mImageView.setScaleToFill(toScale);
    }

    public int getLayout() {
        return mLayout;
    }

    public void alignImageBottomOnResize(boolean alignBottom) {
        mImageView.alignImageBottomOnResize(alignBottom);
    }

    public void setFragmentManager(FragmentManager fm){
        mFragmentManager = fm;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mImageView == null)
            mImageView = (CustomImageView) getChildAt(CUSTOMIMAGEVIEW_POSITION);

        if (mTitle == null)
            mTitle = (TextView) getChildAt(TITLE_POSITION);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        //Calculate the "centerInParent" collapsed specs so text doesn't fall to center after expansion
        if (mTitleTextCenter == null) {
            mTitleTextCenter = (getLayoutParams().height / 2) - (mTitle.getMeasuredHeight() / 2);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            params.setMargins(0, mTitleTextCenter, 0, 0);
            mTitle.setLayoutParams(params);
        }
    }
}
