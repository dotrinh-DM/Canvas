/*
 * Created by dotrinh on 7/22/20 8:04 PM
 * Copyright (c) 2020. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.draw_arc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
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

public class DrawArcActivity extends AppCompatActivity {

    public static Point dotrinhSize = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(new MyArc(this));
    }
}

class MyArc extends View {

    TextPaint textPaint;
    Paint myPaint;
    Paint borderPaint;
    Rect testRect;

    public MyArc(Context context) {
        super(context);
        initialize();
    }

    public MyArc(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public MyArc(Context context, AttributeSet attrs) {
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
        myPaint.setColor(getResources().getColor(R.color.colorAccent, null));
        myPaint.setAntiAlias(true);

        borderPaint = new Paint();
        borderPaint.setStrokeWidth(80);
        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(Color.YELLOW);
        borderPaint.setStrokeCap(Paint.Cap.ROUND);
        borderPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int xOld, int yOld) {
        super.onSizeChanged(newWidth, newHeight, xOld, yOld);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.rotate(-10);
        testRect = new Rect(getWidth()/2 - 200, getHeight()/2 - 200, getWidth()/2 + 200, getHeight()/2 + 200);
        canvas.drawRect(0, 0, getWidth(), getHeight(), borderPaint); //full fill
        canvas.drawCircle(testRect.left, testRect.top, 20, textPaint);
        canvas.drawCircle(testRect.right, testRect.bottom, 20, textPaint);
        canvas.drawCircle(testRect.centerX(), testRect.centerY(), 5, textPaint);
        canvas.drawArc(testRect.left,testRect.top,testRect.right, testRect.bottom, 0, 40, true,  myPaint);
//        myPaint.setColor(Color.parseColor("#fcba03"));
//        canvas.drawArc(testRect.left,testRect.top,testRect.right, testRect.bottom, 45, 45, true,  myPaint);
        canvas.drawCircle(testRect.centerX(), testRect.centerY(), testRect.width()/2f, textPaint);
        canvas.drawRect(testRect, textPaint);
    }
}

















