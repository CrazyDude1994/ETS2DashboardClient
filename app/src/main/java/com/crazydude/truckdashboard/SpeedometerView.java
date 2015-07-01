package com.crazydude.truckdashboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by kartavtsev.s on 30.06.2015.
 */
public class SpeedometerView extends ImageView {

    private float mSpeed = 0;
    private float mMaxSpeed = 140f;
    private float mStartAngle = -65f;
    private float mEndAngle = -295f;
    private Paint mPaint = new Paint();

    public SpeedometerView(Context context) {
        super(context);
        init();
    }

    public SpeedometerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpeedometerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
    }

    public void setSpeed(float speed) {
        mSpeed = speed;
        invalidate();
    }

    public float getEndAngle() {
        return mEndAngle;
    }

    public void setEndAngle(float mEndAngle) {
        this.mEndAngle = mEndAngle;
    }

    public float getStartAngle() {
        return mStartAngle;
    }

    public void setStartAngle(float mStartAngle) {
        this.mStartAngle = mStartAngle;
    }

    public void setMaxSpeed(float speed) {
        mMaxSpeed = speed;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float factorX = canvas.getWidth() / 2048.0f;
        float factorY = canvas.getHeight() / 1152.0f;
        float offsetX = 573 * factorX;
        float offsetY = 557 * factorY;
        float angleSize = Math.abs(mEndAngle - mStartAngle);
        float speedCoef = mSpeed / mMaxSpeed;
        float angle = mStartAngle + (angleSize * (-speedCoef));
        double sin = Math.sin(Math.toRadians(angle));
        double cos = Math.cos(Math.toRadians(angle));
        double startPosX = offsetX + (200 * sin);
        double startPosY = offsetY + (200 * cos);
        double endPosX = offsetX + (270 * sin);
        double endPosY = offsetY + (270 * cos);
        canvas.drawLine(((float) startPosX), ((float) startPosY), ((float) endPosX), ((float) endPosY), mPaint);
    }

/*
    function getXYFromRadian(rotation, radius, offset)
        local posX = (radius + offset) * math.sin(math.rad(rotation))
        local posY = (radius + offset) * math.cos(math.rad(rotation))
        return posX, posY
    end
*/

}
