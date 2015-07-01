package com.crazydude.truckdashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.AndroidCharacter;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by kartavtsev.s on 30.06.2015.
 */
public class SpeedometerView extends ImageView {

    private int mSpeed = 0;
    private int mMaxSpeed = 100;
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

    public void setSpeed(int speed) {
        mSpeed = speed;
        invalidate();
    }

    public void setMaxSpeed(int speed) {
        mMaxSpeed = speed;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float factorX = canvas.getWidth() / 2048.0f;
        float factorY = canvas.getHeight() / 1152.0f;
        float offsetX = 573 * factorX;
        float offsetY = 557 * factorY;
        double posX = 100 * Math.sin(Math.toRadians(mSpeed / 2));
        double posY = 100 * Math.cos(Math.toRadians(mSpeed / 2));
        canvas.drawLine(offsetX, offsetY, offsetX + ((float) posX), offsetY + ((float) posY), mPaint);
    }

/*
    function getXYFromRadian(rotation, radius, offset)
        local posX = (radius + offset) * math.sin(math.rad(rotation))
        local posY = (radius + offset) * math.cos(math.rad(rotation))
        return posX, posY
    end
*/

}
