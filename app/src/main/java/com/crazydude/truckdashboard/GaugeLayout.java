package com.crazydude.truckdashboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Crazy on 03.07.2015.
 */
public class GaugeLayout extends ViewGroup {

    private Drawable mBackgroundDrawable;
    private Matrix mMatrix;

    public GaugeLayout(Context context) {
        super(context);
        init(null);
    }

    public GaugeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GaugeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GaugeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.GaugeLayout);
        mBackgroundDrawable = typedArray.getDrawable(R.styleable.GaugeLayout_drawable);
        typedArray.recycle();
        setWillNotDraw(false);

        mMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        mBackgroundDrawable.setBounds(0, 0, mBackgroundDrawable.getIntrinsicWidth(), mBackgroundDrawable.getIntrinsicHeight());
        RectF drawableRect = new RectF(mBackgroundDrawable.getBounds());
        RectF canvasRect = new RectF(canvas.getClipBounds());
        mMatrix.setRectToRect(drawableRect, canvasRect, Matrix.ScaleToFit.CENTER);
        canvas.setMatrix(mMatrix);
        mBackgroundDrawable.draw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            int sizeX = child.getMeasuredWidth() / 2;
            int sizeY = child.getMeasuredHeight() / 2;
            int posX = (int) (getMeasuredWidth() * layoutParams.getPerctangeX() - sizeX);
            int posY = (int) (getMeasuredHeight() * layoutParams.getPerctangeY() - sizeY);
            child.layout(posX, posY, posX + child.getMeasuredWidth(), posY + child.getMeasuredHeight());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {

        private float perctangeX;
        private float perctangeY;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray array = c.obtainStyledAttributes(attrs, R.styleable.GaugeLayout);
            perctangeX = array.getFloat(R.styleable.GaugeLayout_perctangeX, 0);
            perctangeY = array.getFloat(R.styleable.GaugeLayout_perctangeY, 0);
            array.recycle();
        }

        public float getPerctangeX() {
            return perctangeX;
        }

        public float getPerctangeY() {
            return perctangeY;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }


    }
}
