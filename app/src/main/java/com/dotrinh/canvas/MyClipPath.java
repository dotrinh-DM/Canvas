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
import android.os.Handler;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.concurrent.ThreadLocalRandom;

import static com.dotrinh.canvas.MainActivity.ACTIVE_BITMAP;
import static com.dotrinh.canvas.MainActivity.ACTIVE_SIZE;
import static com.dotrinh.canvas.MainActivity.BACKGROUND_BITMAP;
import static com.dotrinh.canvas.MainActivity.BACKGROUND_SIZE;
import static com.dotrinh.protool.LogUtil.LogI;

public class MyClipPath extends View {

    static double START_X_PERCENT = 3.54330708661417f;
    public double startX;
    static double WIDTH_PERCENT = 95.8661417322835f;
    static Paint RECT_PAINT;
    static Paint LINE_PAINT;
    TextPaint textPaint;
    Rect textBounds = new Rect();
    String testText = "test clip";


    Rect backgroundRect;
    Rect activeRect;
    Rect clipRect;


    public MyClipPath(Context context) {
        super(context);
        initialize();
    }

    public MyClipPath(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public MyClipPath(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        RECT_PAINT = new Paint();
        RECT_PAINT.setStrokeWidth(3);
        RECT_PAINT.setColor(Color.BLUE);
        RECT_PAINT.setStyle(Paint.Style.STROKE);
        RECT_PAINT.setAntiAlias(true);

        LINE_PAINT = new Paint();
        LINE_PAINT.setStrokeWidth(1);
        LINE_PAINT.setColor(Color.YELLOW);
        LINE_PAINT.setStyle(Paint.Style.STROKE);
        LINE_PAINT.setAntiAlias(true);

        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.SERIF);
        textPaint.setColor(Color.BLUE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(Tool.convertSpToPx(getContext(), 20));
        textPaint.setAntiAlias(true);

//        startTimer();
    }

    //REAL SIZE CUSTOM VIEW
    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        LogI("-- xOld: " + xOld);
        LogI("-- yOld: " + yOld);
        LogI("-- xNew: " + xNew);
        LogI("-- yNew: " + yNew);

        float factor = (float) xNew / (float) BACKGROUND_SIZE.x;

        backgroundRect = new Rect(0, 0, xNew, (int) (BACKGROUND_SIZE.y * factor));

        activeRect = new Rect(0, 0, (int) ((WIDTH_PERCENT * xNew) / 100f), (int) (ACTIVE_SIZE.y * factor));
        startX = (float) (START_X_PERCENT * xNew) / 100f;

        clipRect = new Rect(0, 0, xNew, 100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //BACKGROUND LEVEL
        canvas.drawBitmap(BACKGROUND_BITMAP, null, backgroundRect, null);


        //ACTIVE LEVEL
        canvas.translate((float) startX, 0);
//
        canvas.clipRect(clipRect);
//        canvas.drawColor(Color.BLUE);
        canvas.drawBitmap(ACTIVE_BITMAP, null, activeRect, null);
    }

    //TIMER
    private boolean runnableStarted = false;
    private Handler handler = new Handler();
    int dem = 0;
    int randomNum;

    public void startTimer() {
        runnableStarted = true;
        handler.postDelayed(runnable, 200);
    }

    public void stopTimer() {
        runnableStarted = false;
        handler.removeCallbacks(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (runnableStarted) {
                dem++;
                LogI("..........");
                LogI("dem: " + dem);
                startTimer();
                randomNum = ThreadLocalRandom.current().nextInt(150, 600);
                invalidate();
            }
            if (dem == 1000) {
                stopTimer();
            }
        }
    };
}