package com.anirbansumon.utils;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DotsProgressBar extends View {

    private static final int DOT_COUNT = 3;
    private static final int ANIMATION_DELAY = 300;

    private int currentDot = 0;
    private Paint paint;
    private int dotRadius = 20;
    private int dotSpacing = 50;
    private int dotColor = 0xFF3F51B5;

    private boolean isRunning = true;

    private Runnable animator = new Runnable() {
        @Override
        public void run() {
            if (!isRunning) return;
            currentDot = (currentDot + 1) % DOT_COUNT;
            invalidate();
            postDelayed(this, ANIMATION_DELAY);
        }
    };

    public DotsProgressBar(Context context) {
        super(context);
        init();
    }

    public DotsProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DotsProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(dotColor);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        isRunning = true;
        post(animator);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isRunning = false;
        removeCallbacks(animator);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerY = getHeight() / 2;
        int centerX = getWidth() / 2;
        int totalWidth = (DOT_COUNT - 1) * dotSpacing;

        for (int i = 0; i < DOT_COUNT; i++) {
            int x = centerX - totalWidth / 2 + (i * dotSpacing);
            int radius = (i == currentDot) ? dotRadius : dotRadius / 2;
            paint.setAlpha((i == currentDot) ? 255 : 100);
            canvas.drawCircle(x, centerY, radius, paint);
        }
    }

    // Optional: Public methods for control

    public void startAnimation() {
        if (!isRunning) {
            isRunning = true;
            post(animator);
        }
    }

    public void stopAnimation() {
        isRunning = false;
        removeCallbacks(animator);
    }

    public void setDotColor(int color) {
        this.dotColor = color;
        paint.setColor(color);
        invalidate();
    }

    public void setDotRadius(int radius) {
        this.dotRadius = radius;
        invalidate();
    }

    public void setDotSpacing(int spacing) {
        this.dotSpacing = spacing;
        invalidate();
    }
}
