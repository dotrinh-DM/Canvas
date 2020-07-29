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

    public final static double START_X_PERCENT_1 = 0.0354330708661417;
    public final static double START_Y_PERCENT_2 = 0.0368421052631579;
    public static double START_X_1;
    public static double START_Y_2;
    public static final double WIDTH_PERCENT = 0.95866141732283;
    public static double ONE_UNIT_PX;
    public static double ONE_LEVEL_PERCENT = 0.02083333333333;
    public static double CONVERT_127_TO_48_LEVELS = 0.37795275590551;
    TextPaint textPaint;
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
        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.SERIF);
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(Tool.convertSpToPx(getContext(), 20));
        textPaint.setAntiAlias(true);
    }

    //REAL SIZE CUSTOM VIEW
    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int xOld, int yOld) {
        super.onSizeChanged(newWidth, newHeight, xOld, yOld);
        ONE_UNIT_PX = newWidth / 48f;

        float factorWidth = (float) newWidth / (float) BACKGROUND_SIZE.x;
        backgroundRect = new Rect(0, 0, newWidth, (int) (BACKGROUND_SIZE.y * factorWidth));
        activeRect = new Rect(0, 0, (int) (WIDTH_PERCENT * newWidth), (int) (ACTIVE_SIZE.y * factorWidth));
        clipRect = new Rect((int) 0, 0, activeRect.width(), activeRect.height());

        START_X_1 = (float) (START_X_PERCENT_1 * newWidth);
        START_Y_2 = (float) (START_Y_PERCENT_2 * backgroundRect.height());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        //BACKGROUND LEVEL
        canvas.translate(0, 100);
        canvas.drawBitmap(BACKGROUND_BITMAP, null, backgroundRect, null);
        canvas.restore();

        //demo WHITE
//        canvas.save();
//        canvas.translate((float) START_X_1, 140);
//        canvas.clipRect(new Rect(0, 0, activeRect.width(), activeRect.height()));
//        canvas.drawColor(Color.WHITE);
//        canvas.restore();

        //active BITMAP
        canvas.save();
        canvas.translate((float) START_X_1, 100);
        int widthVisible = (int) (randomNum * ONE_LEVEL_PERCENT * activeRect.width());
        canvas.clipRect(new Rect(0, 0, widthVisible, activeRect.height()));
        canvas.drawBitmap(ACTIVE_BITMAP, null, activeRect, null);
        canvas.restore();
    }

    //TIMER
    private boolean runnableStarted = false;
    private Handler handler = new Handler();
    int testID = 0;
    int randomNum;

    public void startTimer() {
        runnableStarted = true;
        handler.postDelayed(runnable, 230);
    }

    public void stopTimer() {
        runnableStarted = false;
        handler.removeCallbacks(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (runnableStarted) {
                testID++;
                LogI("..........");
                LogI("testID: " + testID);
                startTimer();
                randomNum = ThreadLocalRandom.current().nextInt(39, 48);
                invalidate();
            }
        }
    };
}