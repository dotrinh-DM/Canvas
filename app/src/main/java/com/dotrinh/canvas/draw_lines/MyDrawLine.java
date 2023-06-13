/*
 * Created by dotrinh on 7/22/20 8:04 PM
 * Copyright (c) 2020. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.draw_lines;

import static com.dotrinh.canvas.tool.StringTool.getTextBoundHeightWithBottom;
import static com.dotrinh.canvas.tool.StringTool.getTextBoundWidthOfString;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.dotrinh.canvas.tool.StringTool;
import com.dotrinh.canvas.tool.Tool;

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
        centerY = getHeight() / 2;
    }

    int centerY;

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO: 2020/09/09 maybe use canvas.drawLines() is better
        // TODO: 2020/09/09 maybe performance: canvas.drawPath > canvas.drawLines
//        TransactionTime transactionTime = new TransactionTime(System.currentTimeMillis());
//        canvas.drawLine(20, centerY, 20, centerY - (int) ((Math.random() * (200))), textPaint);
//        canvas.drawLine(30, centerY, 30, centerY - (int) ((Math.random() * (200))), textPaint);
//        canvas.drawLine(40, centerY, 40, centerY - (int) ((Math.random() * (200))), textPaint);
//        canvas.drawLine(50, centerY, 50, centerY - (int) ((Math.random() * (200))), textPaint);
//        canvas.drawLine(60, centerY, 60, centerY - (int) ((Math.random() * (200))), textPaint);
//        canvas.drawLine(70, centerY + 1, 70, centerY - (int) ((Math.random() * (200))), textPaint);
//
//        for (int i = 0; i < 10000; i++) {
//            canvas.drawLine(80, centerY, 80, centerY - (int) ((Math.random() * (200))), textPaint);
//        }
//
//        canvas.drawLine(20, centerY + 3, 20, centerY + (int) ((Math.random() * (200))), textPaint);
//        canvas.drawLine(30, centerY + 3, 30, centerY + (int) ((Math.random() * (200))), textPaint);
//        canvas.drawLine(40, centerY + 3, 40, centerY + (int) ((Math.random() * (200))), textPaint);
//        canvas.drawLine(50, centerY + 3, 50, centerY + (int) ((Math.random() * (200))), textPaint);
//        canvas.drawLine(60, centerY + 3, 60, centerY + (int) ((Math.random() * (200))), textPaint);
//        canvas.drawLine(70, centerY + 3, 70, centerY + (int) ((Math.random() * (200))), textPaint);
//        canvas.drawLine(80, centerY + 3, 80, centerY + (int) ((Math.random() * (200))), textPaint);
//        transactionTime.setEnd(System.currentTimeMillis());
//        LogI("time:::::::: " + transactionTime.getDuration());
        drawImprove(canvas);

//        float[] radius = new float[12];
//        radius[0] = 20;
//        radius[1] = centerY;
//        radius[2] = 20;
//        radius[3] = centerY - (int) ((Math.random() * (200)));
//
//        radius[4] = 60;
//        radius[5] = centerY;
//        radius[6] = 60;
//        radius[7] = centerY - (int) ((Math.random() * (200)));
//
//        radius[8] = 100;
//        radius[9] = centerY;
//        radius[10] = 100;
//        radius[11] = centerY - (int) ((Math.random() * (200)));
//        canvas.drawLines(radius, textPaint);
    }

    void drawImprove(Canvas canvas) {
        float[] maXArr = new float[192];
        int startX1 = 20;
        for (int i = 0; i < maXArr.length; i += 4) {
            maXArr[i] = startX1;
            maXArr[i + 1] = centerY - 100;
            maXArr[i + 2] = startX1;
            maXArr[i + 3] = centerY - 100 - (int) ((Math.random() * (300)));
            startX1 += 30;
        }
        canvas.drawLines(maXArr, textPaint);

        canvas.drawText("dotrinh", getWidth() / 2 - getTextBoundWidthOfString("dotrinh", textPaint) / 2f, centerY - getTextBoundHeightWithBottom("dotrinh",textPaint)/2f, textPaint);

        float[] minArr = new float[192];
        int startX2 = 20;
        for (int i = 0; i < minArr.length; i += 4) {
            minArr[i] = startX2;
            minArr[i + 1] = centerY + 10;
            minArr[i + 2] = startX2;
            minArr[i + 3] = centerY + (int) ((Math.random() * (300)));
            startX2 += 30;
        }
        canvas.drawLines(minArr, textPaint);
    }
}