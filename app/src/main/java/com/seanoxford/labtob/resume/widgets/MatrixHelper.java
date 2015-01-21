package com.seanoxford.labtob.resume.widgets;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;

import com.seanoxford.labtob.resume.customviews.CustomImageView;

public class MatrixHelper {

    protected CustomImageView mCustomImageView;
    protected int viewWidth = -1;
    protected int mViewHeight;
    protected float scale = -1;
    protected int drawableWidth = -1;
    protected int drawableHeight = -1;
    protected float viewToDrawableWidth = -1;
    protected float viewToDrawableHeight;
    protected float xOffset;
    protected float yOffset;
    protected float mWidthPercent = -1;
    protected float mHeightPercent = -1;
    protected int mMaxHeight = 0;
    protected int mHeightDeltaOffset = 0;

    public MatrixHelper(CustomImageView customImageView, float percent, int maxHeight, int heightDeltaOffset){
        mHeightDeltaOffset = heightDeltaOffset;
        mCustomImageView = customImageView;
        mHeightPercent = percent;
        mMaxHeight = maxHeight;
    }

    public Matrix matrixIt(){
        Matrix matrix = mCustomImageView.getImageMatrix();

        if(viewWidth == -1)
            viewWidth = mCustomImageView.getWidth() - mCustomImageView.getPaddingLeft() - mCustomImageView.getPaddingRight();
        int viewHeight = mCustomImageView.getHeight() - mCustomImageView.getPaddingTop() - mCustomImageView.getPaddingBottom();

        if(drawableWidth == -1)
            drawableWidth = mCustomImageView.getDrawable().getIntrinsicWidth();

        if(drawableHeight == -1)
            drawableHeight = mMaxHeight;

        if(scale == -1)
            if (drawableWidth * viewHeight > drawableHeight * viewWidth) {
                scale = (float) viewHeight / (float) drawableHeight;
            } else {
                scale = (float) viewWidth / (float) drawableWidth;
            }


        if(viewToDrawableWidth == -1)
            viewToDrawableWidth = viewWidth / scale;
        float viewToDrawableHeight = viewHeight / scale;

        if(xOffset == -1)
            xOffset = mWidthPercent * (drawableWidth - viewToDrawableWidth);
        float yOffset = Math.round((mHeightPercent * (drawableHeight - viewToDrawableHeight)) + mHeightDeltaOffset);

        RectF drawableRect = new RectF(xOffset, yOffset, xOffset + viewToDrawableWidth,
                yOffset + viewToDrawableHeight);

        RectF viewRect = new RectF(0, 0, viewWidth, viewHeight);

        matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.FILL);

        return matrix;
    }

}
