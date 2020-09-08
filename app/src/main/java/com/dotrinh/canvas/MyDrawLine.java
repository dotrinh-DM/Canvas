/*
 * Created by dotrinh on 7/22/20 8:04 PM
 * Copyright (c) 2020. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class MyDrawLine extends View {

    TextPaint textPaint;
    Rect backgroundRect;
    Rect activeRect;
    Rect clipRect;


    public MyDrawLine(Context context) {
        super(context);
        initialize();
    }

    public MyDrawLine(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public MyDrawLine(Context context, AttributeSet attrs) {
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
    }

    //REAL SIZE CUSTOM VIEW
    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int xOld, int yOld) {
        super.onSizeChanged(newWidth, newHeight, xOld, yOld);
        centerY = getHeight()/2;
    }

    int centerY;

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO: 2020/09/09 maybe use canvas.drawLines() is better
        // TODO: 2020/09/09 maybe performance: canvas.drawPath > canvas.drawLines
        canvas.drawLine(20, centerY, 20, centerY - (int) ((Math.random() * (200))), textPaint);
        canvas.drawLine(30, centerY, 30, centerY - (int) ((Math.random() * (200))), textPaint);
        canvas.drawLine(40, centerY, 40, centerY - (int) ((Math.random() * (200))), textPaint);
        canvas.drawLine(50, centerY, 50, centerY - (int) ((Math.random() * (200))), textPaint);
        canvas.drawLine(60, centerY, 60, centerY - (int) ((Math.random() * (200))), textPaint);
        canvas.drawLine(70, centerY, 70, centerY - (int) ((Math.random() * (200))), textPaint);
        canvas.drawLine(80, centerY, 80, centerY - (int) ((Math.random() * (200))), textPaint);

        canvas.drawLine(20, centerY + 3, 20, centerY + (int) ((Math.random() * (200))), textPaint);
        canvas.drawLine(30, centerY + 3, 30, centerY + (int) ((Math.random() * (200))), textPaint);
        canvas.drawLine(40, centerY + 3, 40, centerY + (int) ((Math.random() * (200))), textPaint);
        canvas.drawLine(50, centerY + 3, 50, centerY + (int) ((Math.random() * (200))), textPaint);
        canvas.drawLine(60, centerY + 3, 60, centerY + (int) ((Math.random() * (200))), textPaint);
        canvas.drawLine(70, centerY + 3, 70, centerY + (int) ((Math.random() * (200))), textPaint);
        canvas.drawLine(80, centerY + 3, 80, centerY + (int) ((Math.random() * (200))), textPaint);
    }
}