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

    public final static double START_X_PERCENT_1 = 3.54330708661417;
    public final static double START_Y_PERCENT_2 = 36.8421052631579;
    public static double START_X_1;
    public static double START_Y_2;
    public static final double WIDTH_PERCENT = 95.8661417322835;
    TextPaint textPaint;
    Rect backgroundRect;
    Rect activeRect;
    Rect clipRect;
    static double CLIP_RECT_WIDTH;


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
        textPaint.setColor(Color.BLUE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(Tool.convertSpToPx(getContext(), 20));
        textPaint.setAntiAlias(true);
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
        START_X_1 = (float) (START_X_PERCENT_1 * xNew) / 100f;
        START_Y_2 = (float) (START_Y_PERCENT_2 * backgroundRect.height()) / 100f;

        clipRect = new Rect(0, 0, xNew, 100);
        CLIP_RECT_WIDTH = xNew;
        startTimer();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogI("---- ondraw");

        //BACKGROUND LEVEL
        canvas.drawBitmap(BACKGROUND_BITMAP, null, backgroundRect, null);

        //LEVEL 1
        canvas.save();
        canvas.translate((float) START_X_1, 0);
        clipRect.set(clipRect.left, clipRect.top, (int) (CLIP_RECT_WIDTH - randomNum), clipRect.bottom);
        canvas.clipRect(clipRect);
        canvas.drawColor(Color.LTGRAY);
        canvas.drawBitmap(ACTIVE_BITMAP, null, activeRect, null);
        canvas.restore();

        //LEVEL 2
        canvas.save();
        canvas.translate((float) START_X_1, (float) START_Y_2);
        clipRect.set(clipRect.left, clipRect.top, (int) (CLIP_RECT_WIDTH - randomNum), clipRect.bottom);
        canvas.clipRect(clipRect);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(ACTIVE_BITMAP, null, activeRect, null);
        canvas.restore();
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
//                LogI("..........");
//                LogI("dem: " + dem);
                startTimer();
                randomNum = ThreadLocalRandom.current().nextInt(0, 300);
                invalidate();
            }
            if (dem == 1000) {
                stopTimer();
            }
        }
    };
}