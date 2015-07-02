package com.crazydude.truckdashboard;

/**
 * Created by kartavtsev.s on 02.07.2015.
 */
public class Gaugage {

    private float mX;
    private float mY;
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

    private OnValueChangedListener mOnValueChangedListener;
    private SpeedometerView mSpeedometerView;

    public interface OnValueChangedListener {
        void onValueChanged(Gaugage gaugage, float newValue);
    }

    public Gaugage(float x, float y, float maxValue, float startAngle, float endAngle,
                   float startLength, float endLength, SpeedometerView speedometerView) {
        this.mX = x * speedometerView.getDrawable().getIntrinsicWidth();
        this.mY = y * speedometerView.getDrawable().getIntrinsicHeight();
        this.mMaxValue = maxValue;
        this.mStartAngle = startAngle;
        this.mEndAngle = endAngle;
        this.mStartLength = startLength;
        this.mEndLength = endLength;
        this.mSpeedometerView = speedometerView;
        mSpeedometerView.addGaugage(this);
    }

    public void setOnValueChangedListener(OnValueChangedListener listener) {
        mOnValueChangedListener = listener;
    }

    public float getCalculatedStartX() {
        return mCalculatedStartX;
    }

    public float getCalculatedStartY() {
        return mCalculatedStartY;
    }

    public float getCalculatedEndX() {
        return mCalculatedEndX;
    }

    public float getCalculatedEndY() {
        return mCalculatedEndY;
    }

    public float getStartLength() {
        return mStartLength;
    }

    public void setStartLength(float startLength) {
        this.mStartLength = startLength;
    }

    public float getEndLength() {
        return mEndLength;
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
        mOnValueChangedListener.onValueChanged(this, value);
    }

    public float getX() {
        return mX;
    }

    public void setX(float x) {
        this.mX = x;
    }

    public float getY() {
        return mY;
    }

    public void setY(float y) {
        this.mY = y;
    }

    public float getMaxValue() {
        return mMaxValue;
    }

    public void setMaxValue(float maxValue) {
        this.mMaxValue = maxValue;
    }

    public float getStartAngle() {
        return mStartAngle;
    }

    public void setStartAngle(float startAngle) {
        this.mStartAngle = startAngle;
    }

    public float getEndAngle() {
        return mEndAngle;
    }

    public void setEndAngle(float endAngle) {
        this.mEndAngle = endAngle;
    }

    private void calculateArrowPosition() {
        float angleSize = Math.abs(mEndAngle - mStartAngle);
        float valueCoef = mCurrentValue / mMaxValue;
        float angle = mStartAngle + (angleSize * (-valueCoef));
        double sin = Math.sin(Math.toRadians(angle));
        double cos = Math.cos(Math.toRadians(angle));
        mCalculatedStartX = (float) (mX + (mStartLength * sin));
        mCalculatedStartY = (float) (mY + (mStartLength * cos));
        mCalculatedEndX = (float) (mX + (mEndLength * sin));
        mCalculatedEndY = (float) (mY + (mEndLength * cos));
    }
}
