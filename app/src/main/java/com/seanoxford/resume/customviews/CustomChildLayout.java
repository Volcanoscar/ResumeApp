package com.seanoxford.resume.customviews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomChildLayout extends RelativeLayout {

    public static final int CUSTOMIMAGEVIEW_POSITION = 0;
    public static final int TITLE_POSITION = 1;
    public static final int TITLE_VIEW_ID = 1;

    protected CustomImageView mImageView;
    protected TextView mTitle;
    protected Context mContext;
    protected Integer mViewPosition;
    protected Integer mTitleTextCenter;
    protected int mLayout;

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

    public CustomChildLayout(Context context, int layout, String text, String color, int image, Typeface typeface){
        super(context);
        mContext = context;
        if(layout != 0)
            mLayout = layout;
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mImageView = new CustomImageView(mContext);
        mImageView.setImageResource(image);
        mImageView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mImageView.setColor(color);
        addView(mImageView);
        mTitle = new TextView(mContext);
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 48);
        mTitle.setText(text);
        mTitle.setTextColor(Color.parseColor("#FFFFFF"));
        mTitle.setTypeface(typeface);
        mTitle.setId(TITLE_VIEW_ID);
        addView(mTitle);
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

    public int getLayout(){
        return mLayout;
    }

    public void alignImageBottomOnResize(boolean alignBottom){
        mImageView.alignImageBottomOnResize(alignBottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mImageView == null)
            mImageView = (CustomImageView) getChildAt(CUSTOMIMAGEVIEW_POSITION);

        if(mTitle == null)
            mTitle = (TextView) getChildAt(TITLE_POSITION);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        //Calculate the "centerInParent" collapsed specs so text doesn't fall to center after expansion
        if(mTitleTextCenter == null) {
            mTitleTextCenter = (getLayoutParams().height / 2) - (mTitle.getMeasuredHeight() / 2);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            params.setMargins(0, mTitleTextCenter, 0 , 0);
            mTitle.setLayoutParams(params);
        }
    }
}
