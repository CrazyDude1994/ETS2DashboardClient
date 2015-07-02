package com.crazydude.truckdashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by kartavtsev.s on 30.06.2015.
 */
public class SpeedometerView extends ImageView implements Gaugage.OnValueChangedListener {

    private Paint mPaint;
    private ArrayList<Gaugage> mGaugages;

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
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
        mGaugages = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void addGaugage(Gaugage gaugage) {
        mGaugages.add(gaugage);
        gaugage.setOnValueChangedListener(this);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setMatrix(getImageMatrix());
        for (Gaugage gaugage : mGaugages) {
            canvas.drawLine(gaugage.getCalculatedStartX(),
                    gaugage.getCalculatedStartY(),
                    gaugage.getCalculatedEndX(),
                    gaugage.getCalculatedEndY(), mPaint);
        }
    }

    @Override
    public void onValueChanged(Gaugage gaugage, float newValue) {
        invalidate();
    }

    /*
    function getXYFromRadian(rotation, radius, offset)
        local posX = (radius + offset) * math.sin(math.rad(rotation))
        local posY = (radius + offset) * math.cos(math.rad(rotation))
        return posX, posY
    end
*/

}
