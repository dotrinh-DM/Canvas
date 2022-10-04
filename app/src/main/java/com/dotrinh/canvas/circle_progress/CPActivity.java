/*
 * Created by dotrinh on 7/22/20 8:04 PM
 * Copyright (c) 2020. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.circle_progress;


import static com.dotrinh.protool.StringTool.getTextBoundHeightWithBottom;
import static com.dotrinh.protool.StringTool.getTextBoundWidthOfString;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.dotrinh.canvas.R;
import com.dotrinh.canvas.Tool;
import com.dotrinh.protool.LogUtil;

public class CPActivity extends AppCompatActivity {

    public static Point dotrinhSize = new Point();
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(new MyProgress(this));
    }
}

class MyProgress extends View {

    TextPaint textPaint;
    Paint bigPaint_BG;
    Paint bigPaint;
    Paint smallPaint;
    Paint borderPaint;
    RectF testRect;
    RectF testRect2;

    public MyProgress(Context context) {
        super(context);
        initialize();
    }

    public MyProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public MyProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {

        textPaint = new TextPaint();
        Typeface tf =Typeface.createFromAsset(getContext().getAssets(),"fonts/BungeeSpice-Regular.ttf");
        textPaint.setTypeface(tf);
        textPaint.setStrokeWidth(7);
        textPaint.setTextSize(Tool.convertSpToPx(getContext(), 30));
        textPaint.setAntiAlias(true);
        textPaint.setPathEffect(null);
        textPaint.setColor(getResources().getColor(R.color.green, null));

        bigPaint_BG = new Paint();
        bigPaint_BG.setColor(getResources().getColor(R.color.gray, null));
        bigPaint_BG.setAntiAlias(true);

        bigPaint = new Paint();
        bigPaint.setColor(getResources().getColor(R.color.green, null));
        bigPaint.setAntiAlias(true);

        smallPaint = new Paint();
        smallPaint.setColor(getResources().getColor(R.color.white, null));
        smallPaint.setAntiAlias(true);

        borderPaint = new Paint();
        borderPaint.setStrokeWidth(80);
        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(Color.GRAY);
        borderPaint.setStrokeCap(Paint.Cap.ROUND);
        borderPaint.setAntiAlias(true);
    }

    float bigSize = 300f;
    float smallSize = 250f;
    int degree;
    int percentage;

    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int xOld, int yOld) {
        super.onSizeChanged(newWidth, newHeight, xOld, yOld);
        testRect = new RectF(getWidth() / 2f - bigSize, getHeight() / 2f - bigSize, getWidth() / 2f + bigSize, getHeight() / 2f + bigSize);
        testRect2 = new RectF(getWidth() / 2f - smallSize, getHeight() / 2f - smallSize, getWidth() / 2f + smallSize, getHeight() / 2f + smallSize);

        new Thread(() -> {
            int total = 1000;
            for (int sent = 0; sent < total; sent++) {
                sent += 1;
                float factor = (float) sent / total;
                degree = Math.round(factor * 360);
                percentage = Math.round(factor * 100);
                LogUtil.LogI("check: " + percentage + " " + degree);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                invalidate();
            }

        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        LogUtil.LogI("onDraw");
        canvas.drawRect(0, 0, getWidth(), getHeight(), borderPaint); //full fill BG

        canvas.drawArc(testRect, 0, 360, true, bigPaint_BG); //layer 1
        canvas.drawArc(testRect, -90, degree, true, bigPaint);          //layer 2: progress
        canvas.drawArc(testRect2, 0, 360, true, smallPaint); //layer 3
        String nicer = percentage + "%";
        canvas.drawText(nicer, (testRect2.centerX() - getTextBoundWidthOfString(nicer, textPaint) / 2f), (testRect2.top + (testRect2.height() / 2f + getTextBoundHeightWithBottom(nicer, textPaint) / 2f)), textPaint);
        /*canvas.drawCircle(testRect2.centerX(), testRect2.centerY(), 20, textPaint);
        myPaint.setColor(Color.parseColor("#fcba03"));
        canvas.drawArc(testRect.left,testRect.top,testRect.right, testRect.bottom, 45, 45, true,  myPaint);
        canvas.drawCircle(testRect.centerX(), testRect.centerY(), testRect.width()/2f, textPaint);
        canvas.drawRect(testRect, textPaint);*/
    }
}

















