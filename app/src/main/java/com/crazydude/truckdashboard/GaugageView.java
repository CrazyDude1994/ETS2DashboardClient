package com.crazydude.truckdashboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kartavtsev.s on 30.06.2015.
 */
public class GaugageView extends View {

    private Paint mPaint;
    private float mCurrentValue;
    private float mMaxValue;
    private float mStartAngle;
    private float mEndAngle;
    private float mStartLength;
    private float mEndLength;
    private float mCalculatedStartX;
    private float mCalculatedStartY;
    private float mCalculatedEndX;
    private float mCalculatedEndY;

    public GaugageView(Context context) {
        super(context);
        init(null);
    }

    public GaugageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GaugageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GaugageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.GaugageView);
        mStartAngle = typedArray.getFloat(R.styleable.GaugageView_startAngle, 0);
        mEndAngle = typedArray.getFloat(R.styleable.GaugageView_endAngle, 360);
        mStartLength = typedArray.getFloat(R.styleable.GaugageView_startLength, 0);
        mEndLength = typedArray.getFloat(R.styleable.GaugageView_endLength, 50);
        mCurrentValue = 0;
        mMaxValue = Math.abs(mEndAngle - mStartAngle);
        calculateArrowPosition();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int a = (int) (mEndLength * 2);
        setMeasuredDimension(a, a);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth() / 2;
        int height = canvas.getHeight() / 2;
        canvas.drawLine(width + mCalculatedStartX, height + mCalculatedStartY, width + mCalculatedEndX, height + mCalculatedEndY, mPaint);
    }

    private void calculateArrowPosition() {
        float angleSize = Math.abs(mEndAngle - mStartAngle);
        float valueCoef = mCurrentValue / mMaxValue;
        float angle = mStartAngle + (angleSize * (-valueCoef));
        double sin = Math.sin(Math.toRadians(angle));
        double cos = Math.cos(Math.toRadians(angle));
        mCalculatedStartX = (float) (mStartLength * sin);
        mCalculatedStartY = (float) (mStartLength * cos);
        mCalculatedEndX = (float) (mEndLength * sin);
        mCalculatedEndY = (float) (mEndLength * cos);
        invalidate();
    }

    public void setStartLength(float startLength) {
        this.mStartLength = startLength;
    }


    public void setEndLength(float endLength) {
        this.mEndLength = endLength;
    }

    public float getValue() {
        return mCurrentValue;
    }

    public void setValue(float value) {
        this.mCurrentValue = value;
        calculateArrowPosition();
    }


    public void setMaxValue(float maxValue) {
        this.mMaxValue = maxValue;
        calculateArrowPosition();
    }


    public void setStartAngle(float startAngle) {
        this.mStartAngle = startAngle;
    }


    public void setEndAngle(float endAngle) {
        this.mEndAngle = endAngle;
    }
}
