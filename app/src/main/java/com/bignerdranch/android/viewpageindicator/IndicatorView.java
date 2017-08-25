package com.bignerdranch.android.viewpageindicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING;

/**
 * Created by Administrator on 2017/8/25/025.
 */

public class IndicatorView extends View implements ViewPager.OnPageChangeListener {
    private int mRadius = 30;
    private int mStrokeWidth = 5;
    private int spaceWidth = 50;
    private int mCount = 4;
    private int mSelectPosition = 0;
    private int mClickX;
    private int mClickY;

    private List<Indicator> mIndicators;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintSelect = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ViewPager mViewPage;
    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);

        mPaintSelect.setColor(Color.BLUE);
        mPaintSelect.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
    }

    //relative position
    public void setIndicators() {
        mIndicators = new ArrayList<>();
        for (int i = 0; i < mCount; i++) {
            Indicator indicator = new Indicator();
            indicator.cx = mRadius + mStrokeWidth / 2 + (mRadius * 2 + mStrokeWidth + spaceWidth) * i;
            indicator.cy = mRadius + mStrokeWidth / 2;
            mIndicators.add(indicator);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = mRadius * mCount * 2 + mStrokeWidth * 4 + spaceWidth * (mCount - 1);
        int height = mRadius * 2 + mStrokeWidth;
        setMeasuredDimension(width, height);

        setIndicators();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mCount; i++) {
            Indicator indicator = mIndicators.get(i);
            if (i == mSelectPosition) {
                canvas.drawCircle(indicator.cx, indicator.cy, mRadius, mPaintSelect);
            } else {
                canvas.drawCircle(indicator.cx, indicator.cy, mRadius, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mClickX = (int) event.getX();
                mClickY = (int) event.getY();
                isClick();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void isClick() {
        for (int i = 0; i < mCount; i++) {
            Indicator indicator = mIndicators.get(i);
            if (Math.abs(indicator.cx - mClickX) < mRadius
                    && Math.abs(indicator.cy - mClickY) < mRadius) {
                mViewPage.setCurrentItem(i);
                break;
            }
        }
    }

    public void setUpWithViewPage(ViewPager viewPage) {
        mViewPage = viewPage;
        mViewPage.addOnPageChangeListener(this);
    }

    //OnPageChangeListener
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {
        mSelectPosition = position;
        invalidate();
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
