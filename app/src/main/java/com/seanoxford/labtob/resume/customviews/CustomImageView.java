package com.seanoxford.labtob.resume.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.seanoxford.labtob.resume.widgets.MatrixHelper;

/**
 * Created by labtob on 12/7/2014.
 */

public class CustomImageView extends ImageView {

    public static final int VALUE_UNSET = -1;

    protected float mWidthPercent = 0;
    protected float mHeightPercent = -1;
    protected String mHexColorOverlay;
    protected int mOrder = -1;
    protected CustomImageView theCustomImageView;
    protected MatrixHelper mMatrixHelper = null;
    protected int previousHeight = 218;
    protected int incrementedHeight = 436;
    protected int currentHeight = 0;
    protected boolean mScaleToFill = true;


    protected Matrix mMatrix = null;
    protected Context mContext;

    protected CustomRelativeLayout mGrandParentLayout;
    protected RelativeLayout mParentLayout;
    protected boolean mHasBeenResized = false;


    public void setOrder(int order) {
        mOrder = order;
    }

    public int getOrder() {
        return mOrder;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setScaleToFill(boolean scaleToFill){
        mScaleToFill = scaleToFill;
    }

    public void setPercentage(float percent) {
        mHeightPercent = percent;
    }

    public float getPercentage() {
        return mHeightPercent;
    }

    public void setColor(String color) {
        mHexColorOverlay = color;
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomImageView(Context context) {
        super(context);
        init();
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setScaleType(ScaleType.MATRIX);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        setMatrix();
        //TODO set color options
        if (mHexColorOverlay != null)
            canvas.drawColor(Color.parseColor(mHexColorOverlay));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mGrandParentLayout = (CustomRelativeLayout) getParent().getParent();
        mParentLayout = (RelativeLayout) getParent();
        if (!mHasBeenResized && mScaleToFill)
            scaleToFill();

        setMatrix();
        super.onLayout(changed, l, t, r, b);
    }

    private void scaleToFill() {
        Bitmap bm = ((BitmapDrawable) getDrawable()).getBitmap();
        int bitmapHeight = bm.getHeight();
        int bitmapWidth = bm.getWidth();
        int parentHeight = mGrandParentLayout.getTotalHeight();
        int parentWidth = mGrandParentLayout.getMeasuredWidth();

        int widthDelta = parentWidth - bitmapWidth;
        int heightDelta = parentHeight - bitmapHeight;

        if (widthDelta > 0 || heightDelta > 0) {
            int newWidth = bitmapWidth;
            int newHeight = bitmapHeight;

            if (widthDelta > 0 && heightDelta > 0) {
                if (widthDelta > heightDelta) {
                    newWidth = parentWidth;
                    newHeight = (parentWidth / bitmapWidth) * bitmapHeight;
                } else {
                    newHeight = parentHeight;
                    newWidth = (parentHeight / bitmapHeight) * bitmapWidth;
                }
            } else if (widthDelta > 0) {
                newWidth = parentWidth;
                newHeight = (parentWidth / bitmapWidth) * bitmapHeight;
            } else if (heightDelta > 0) {
                newHeight = parentHeight;
                newWidth = (parentHeight / bitmapHeight) * bitmapWidth;
            }
            setImageBitmap(Bitmap.createScaledBitmap(bm, newWidth, newHeight, true));
        }

        mHasBeenResized = true;
    }



    public void setMatrix() {
        if (mMatrixHelper == null) {
            //Used to cache some values for the matrix calculations
            mMatrixHelper = new MatrixHelper(this, mHeightPercent, mGrandParentLayout.getTotalHeight());
        }
        setImageMatrix(mMatrixHelper.matrixIt());
    }

}