/*
 * Created by dotrinh on 7/22/20 8:04 PM
 * Copyright (c) 2020. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.WAVE_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.dotrinh.canvas.tool.Tool;

public class MyPath_Wave extends View {

    TextPaint textPaint;
    Rect backgroundRect;
    Rect activeRect;
    Rect clipRect;


    public MyPath_Wave(Context context) {
        super(context);
        initialize();
    }

    public MyPath_Wave(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public MyPath_Wave(Context context, AttributeSet attrs) {
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
    }

    int maxLine = 100;

    @Override
    protected void onDraw(Canvas canvas) {
        Path p = new Path();
        p.moveTo(20, 20);
//        p.lineTo(30, 200);
//        p.lineTo(40, 20);
//        p.lineTo(50, 200);
//        p.lineTo(60, 20);
//        p.lineTo(70, 200);
//        p.lineTo(80, 20);
        for (int j = 30; j < 10000; j += 10) {
            p.lineTo(j, 200 + (int) ((Math.random() * (200))));
        }
        canvas.drawPath(p, textPaint);
    }
}