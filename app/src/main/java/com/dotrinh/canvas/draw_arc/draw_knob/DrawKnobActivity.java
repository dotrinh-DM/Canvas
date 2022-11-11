/*
 * Created by dotrinh on 7/22/20 8:04 PM
 * Copyright (c) 2020. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.draw_arc.draw_knob;

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
import com.dotrinh.canvas.tool.Tool;

public class DrawKnobActivity extends AppCompatActivity {

    public static Point dotrinhSize = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(new MyKnob(this));
    }
}

class MyKnob extends View {

    TextPaint textPaint;
    Paint myPaint;
    Paint BG_paint;
    Paint circle_paint;

    RectF testRect;

    public MyKnob(Context context) {
        super(context);
        initialize();
    }

    public MyKnob(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public MyKnob(Context context, AttributeSet attrs) {
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

        myPaint = new Paint();
        myPaint.setColor(getResources().getColor(R.color.white, null));
        myPaint.setAntiAlias(true);

        BG_paint = new Paint();
        BG_paint.setStrokeWidth(80);
        BG_paint.setStyle(Paint.Style.FILL);
        BG_paint.setColor(Color.GRAY);
        BG_paint.setStrokeCap(Paint.Cap.ROUND);
        BG_paint.setAntiAlias(true);


        circle_paint = new Paint();
        circle_paint.setStyle(Paint.Style.FILL);
        circle_paint.setColor(Color.GRAY);
        circle_paint.setStrokeCap(Paint.Cap.ROUND);
        circle_paint.setAntiAlias(true);
    }

    int size = 200;
    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int xOld, int yOld) {
        super.onSizeChanged(newWidth, newHeight, xOld, yOld);
        // testRect = new Rect(getWidth() / 2 - 200, getHeight() / 2 - 200, getWidth() / 2 + 200, getHeight() / 2 + 200);
        int left = getWidth() / 2 - size;
        int top = getHeight() / 2 - size;
        testRect = new RectF(left, top, getWidth() / 2f + size, getHeight() / 2f + size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), BG_paint); //full fill
        // canvas.drawRect(testRect, textPaint);
        canvas.drawArc(testRect, 135, (250f/500) * (405 - 135), true, myPaint);
//        canvas.drawArc(testRect.left,testRect.top,testRect.right, testRect.bottom, 45, 45, true,  myPaint);
        canvas.drawCircle(testRect.centerX(), testRect.centerY(), size - 50, circle_paint);
        canvas.drawCircle(testRect.centerX(), testRect.centerY(), 5, textPaint);
    }
}

















