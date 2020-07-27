/*
 * Created by dotrinh on 7/22/20 8:04 PM
 * Copyright (c) 2020. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import static com.dotrinh.canvas.MainActivity.dotrinhSize;
import static com.dotrinh.canvas.StringTool.getTextBoundHeightWithBottom;

public class MyClipPath extends View {

    static Paint RECT_PAINT;
    static Paint LINE_PAINT;
    TextPaint textPaint;
    Rect textBounds = new Rect();
    Bitmap image;
    Bitmap image2;
    Rect dest;
    String testText = "test clip";


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
        image = BitmapFactory.decodeResource(getResources(), R.drawable.nongthonvn);
        image2 = BitmapFactory.decodeResource(getResources(), R.drawable.phunuvn);
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
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        float factor = (float) getWidth() / image.getWidth();
        dest = new Rect(0, 0, getWidth() - 1, (int) (image.getHeight() * factor));
        canvas.drawBitmap(image, null, dest, null);
        canvas.translate(330, 330);
        canvas.clipRect(new Rect(0, 0, 400, 400));
        canvas.drawColor(Color.WHITE);
        canvas.drawText(testText, 0, getTextBoundHeightWithBottom(testText, textPaint), textPaint);
    }

    void drawMyRect(Canvas canvas) {
        canvas.drawRect(0, 0, textBounds.width(), textBounds.height(), RECT_PAINT);
    }

    void drawMyLine(Canvas canvas) {
        canvas.drawLine(0, 100, getWidth(), 100, LINE_PAINT);
    }

}