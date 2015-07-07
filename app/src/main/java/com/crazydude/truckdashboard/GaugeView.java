package com.crazydude.truckdashboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by kartavtsev.s on 30.06.2015.
 */
public class GaugeView extends ImageView {

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
    private float mCoefX;
    private float mCoefY;
    private Matrix mScaleMatrix;

    public GaugeView(Context context) {
        super(context);
        init(null);
    }

    public GaugeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GaugeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GaugeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mPaint = new Paint();

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.GaugeView);
        mStartAngle = typedArray.getFloat(R.styleable.GaugeView_startAngle, 0);
        mEndAngle = typedArray.getFloat(R.styleable.GaugeView_endAngle, 360);
        mStartLength = typedArray.getFloat(R.styleable.GaugeView_startLength, 0);
        mEndLength = typedArray.getFloat(R.styleable.GaugeView_endLength, 50);
        mMaxValue = typedArray.getFloat(R.styleable.GaugeView_maxValue, Math.abs(mEndAngle - mStartAngle));
        mCoefX = typedArray.getFloat(R.styleable.GaugeView_coefX, 0.5f);
        mCoefY = typedArray.getFloat(R.styleable.GaugeView_coefY, 0.5f);
        mCurrentValue = 0;
        typedArray.recycle();
        calculateArrowPosition();
        mScaleMatrix = new Matrix();
    }

/*    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int a = (int) (Math.max(mEndLength, mCircleRadius) * 2);
        setMeasuredDimension(a, a);
    }*/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float[] points = {mCalculatedStartX, mCalculatedStartY,
                mCalculatedEndX, mCalculatedEndY};

        getImageMatrix().mapPoints(points);

        float[] sizePoints = {getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight()};

        float[] scaleValues = new float[9];
        getImageMatrix().getValues(scaleValues);
        mScaleMatrix.setScale(scaleValues[Matrix.MSCALE_X], scaleValues[Matrix.MSCALE_Y]);

        mScaleMatrix.mapPoints(sizePoints);

        float scaledX = sizePoints[0] * mCoefX;
        float scaledY = sizePoints[1] * mCoefY;

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
        canvas.drawLine(points[0] + scaledX, points[1] + scaledY, points[2] + scaledX,
                points[3] + scaledY, mPaint);
    }

    private void calculateArrowPosition() {
        float angleSize = Math.abs(mEndAngle - mStartAngle);
        float valueCoef = mCurrentValue / mMaxValue;
        float angle = -mStartAngle + (angleSize * -valueCoef) + 90;
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
