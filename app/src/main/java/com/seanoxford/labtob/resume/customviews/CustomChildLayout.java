package com.seanoxford.labtob.resume.customviews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.seanoxford.labtob.resume.R;

/**
 * Created by labtob on 12/29/2014.
 */
public class CustomChildLayout extends RelativeLayout {

    public static final int CUSTOMIMAGEVIEW_POSITION = 0;
    public static final int TITLE_POSITION = 1;

    protected Integer mViewPosition;
    protected CustomImageView mImageView;
    protected TextView mTitle;
    protected Context mContext;

    public CustomChildLayout(Context context) {
        super(context);
        init();
    }

    public CustomChildLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomChildLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomChildLayout(Context context, String text, String color, int image, Typeface typeface){
        super(context);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mContext = context;
        CustomImageView civ = new CustomImageView(mContext);
        civ.setImageResource(image);
        civ.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        civ.setColor(color);
        addView(civ);
        TextView tv = new TextView(mContext);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 48);
        tv.setText(text);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        tv.setTypeface(typeface);
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        tv.setLayoutParams(layoutParams);
//        tv.setVisibility(View.INVISIBLE);
        addView(tv);
    }

    private void init(){
        mImageView = (CustomImageView) getChildAt(CUSTOMIMAGEVIEW_POSITION);
        mTitle = (TextView) getChildAt(TITLE_POSITION);
    }

    public int getViewPosition(){
        return mViewPosition;
    }

    public void setViewPosition(int n){
        mViewPosition = n;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mImageView == null)
            mImageView = (CustomImageView) getChildAt(CUSTOMIMAGEVIEW_POSITION);

        if(mTitle == null)
            mTitle = (TextView) getChildAt(TITLE_POSITION);


//        if(mTitleTextCenter == null) {
//            mTitleTextCenter = (getMeasuredHeight() / 2) - (mTitle.getMeasuredHeight() / 2);
//            Log.d("lll", String.format("mTextCenter: %d, measured height: %d, mtitlemeasuredHeight: %d", mTitleTextCenter, getMeasuredHeight(), mTitle.getMeasuredHeight()));
//            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
//            params.setMargins(0, mTitleTextCenter, 0 , 0);
//            mTitle.setLayoutParams(params);
//        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
    }
}
