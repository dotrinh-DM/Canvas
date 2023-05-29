/*
 * Created by dotrinh on 5/29/23, 8:02 PM
 * Copyright (c) 2023. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.draw_scale;

import static com.dotrinh.canvas.tool.LogUtil.LogI;
import static com.dotrinh.protool.StringTool.getTextBoundHeightWithBottom;
import static com.dotrinh.protool.StringTool.getTextBoundWidthOfString;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dotrinh.canvas.tool.Tool;

import java.util.Timer;
import java.util.TimerTask;

public class ZoomableView extends View {

    private float scaleFactor = 1.0f;
    private float focalX;
    private float focalY;

    TextPaint textPaint;


    public ZoomableView(Context context) {
        super(context);
        initialize();
    }

    public ZoomableView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public ZoomableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.SERIF);
        textPaint.setStrokeWidth(7);
        textPaint.setTextSize(Tool.convertSpToPx(getContext(), 20));
        textPaint.setAntiAlias(true);
        textPaint.setPathEffect(null);
        textPaint.setColor(Color.BLUE);
        textPaint.setStyle(Paint.Style.STROKE);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scaleFactor += 0.001;
                invalidate();
            }
        }, 0, 10);

    }

    //REAL SIZE CUSTOM VIEW
    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int xOld, int yOld) {
        super.onSizeChanged(newWidth, newHeight, xOld, yOld);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.scale(scaleFactor, scaleFactor, focalX, focalY);
        String myString = "100%";
        canvas.drawText(myString,
                (getWidth() / 2f - getTextBoundWidthOfString(myString, textPaint) / 2f),
                (getHeight() / 2f + getTextBoundHeightWithBottom(myString, textPaint) / 2f),
                textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Get the touch coordinates
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                // Calculate the scaling factor based on the distance moved
                float dx = x - focalX;
                float dy = y - focalY;
                float distance = (float) Math.sqrt(dx * dx + dy * dy);
                scaleFactor = Math.max(0.1f, Math.min(distance / 100, 5.0f));
                LogI("scaleFactor: " + scaleFactor);
                // Update the focal point
                focalX = x;
                focalY = y;

                // Redraw the view
                invalidate();
                break;
        }

        return true;
    }
}
