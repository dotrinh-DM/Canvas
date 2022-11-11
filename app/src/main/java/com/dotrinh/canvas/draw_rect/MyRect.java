/*
 * Created by dotrinh on 7/22/20 8:04 PM
 * Copyright (c) 2020. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.draw_rect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import static com.dotrinh.canvas.tool.LogUtil.LogI;

import com.dotrinh.canvas.tool.Tool;

public class MyRect extends View {

    TextPaint textPaint;
    Rect myRect;


    public MyRect(Context context) {
        super(context);
        initialize();
    }

    public MyRect(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public MyRect(Context context, AttributeSet attrs) {
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
    int newWidth;
    int newHeight;

    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int xOld, int yOld) {
        super.onSizeChanged(newWidth, newHeight, xOld, yOld);
        int l = 50;
        int t = 50;
        int r = newWidth / 2 + 300;
        myRect = new Rect(l, t, r, newHeight / 2 + 300);
        this.newWidth = newWidth;
        this.newHeight = newHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (myRect != null) {
            canvas.drawRect(myRect, textPaint);
            canvas.drawCircle(myRect.centerX(), myRect.centerY(), 12, textPaint);
            LogI(":l : " + myRect.left);
            LogI(":r : " + myRect.right);
            LogI(":: " + myRect.width());
            LogI(":: " + myRect.height());
            LogI(":: " + (myRect.width()/2 + myRect.left));
            LogI(":: " + myRect.centerX());
        }

//        canvas.drawCircle(newWidth / 2f, newHeight / 2f, 90, textPaint);
    }
}