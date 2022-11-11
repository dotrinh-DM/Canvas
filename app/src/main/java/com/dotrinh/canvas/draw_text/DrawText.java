/*
 * Created by dotrinh on 7/22/20 8:04 PM
 * Copyright (c) 2020. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.draw_text;

import android.content.Context;
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

import com.dotrinh.canvas.tool.StringTool;
import com.dotrinh.canvas.tool.Tool;

public class DrawText extends View {

    TextPaint textPaint;
    static Paint RECT_PAINT;
    static Paint LINE_PAINT;
    Rect textBounds;
    private String textStr = "サッカーをする\nビルド中だよね\n　高田　高田馬場\nカタカナボールペン";
//    private String textStr = "Olivia\n                   \nSmith";
//    private String textStr = " ";

    public DrawText(Context context) {
        super(context);
        initialize();
    }

    public DrawText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public DrawText(Context context, AttributeSet attrs) {
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

        textBounds = new Rect();
        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.SERIF);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
//        textPaint.setUnderlineText(true); // TODO: 2019-09-04 chu y khi ve underline
//        textPaint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC));
        textPaint.setTextSize(Tool.convertSpToPx(getContext(), 33));
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(50, 200);

        String daiNhat = StringTool.getLongestStringInMultiLines(textStr, textPaint);
        textPaint.getTextBounds(daiNhat, 0, daiNhat.length(), textBounds);

        StaticLayout.Builder builder = StaticLayout.Builder.obtain(textStr, 0, textStr.length(), textPaint, (int) textPaint.measureText(daiNhat))
//                .setAlignment(Layout.Alignment.ALIGN_OPPOSITE)
                .setAlignment(Layout.Alignment.ALIGN_CENTER)
//                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setIncludePad(false);
        StaticLayout myStaticLayout = builder.build();
        myStaticLayout.draw(canvas);
        canvas.drawRect(0, 0, myStaticLayout.getWidth(), myStaticLayout.getHeight(), RECT_PAINT);
    }

    void drawTextRotateAlignLEFT(Canvas canvas) {
        /*
            Notes:
            1. textbound la hinh chu nhat vua khit voi text (fit to text content), khong bao gom left va bottom
            2. -textBounds.top = textBounds.height() - textBounds.bottom (text ve tu baseline)
            3. Khi ve tu phia ben trai la phai ve tu -textBounds.left
        */

//        textPaint.getTextBounds(textStr, 0, textStr.length(), textBounds);
//        canvas.save();
//        canvas.translate(50, textBounds.width() + 100);
//        canvas.rotate(-90);
//        canvas.drawText(textStr, -textBounds.left, -textBounds.top, textPaint);
//        drawMyRect(canvas);
//        canvas.restore();
    }

    void drawMyRect(Canvas canvas) {
        canvas.drawRect(0, 0, textBounds.width(), textBounds.height(), RECT_PAINT);
    }

    void drawMyLine(Canvas canvas) {
        canvas.drawLine(0, 100, getWidth(), 100, LINE_PAINT);
    }

    void drawMeasureTextUnderline(Canvas canvas) {
        /*
            Notes:
            1. Khi ve underline chu y nen dung measure thi do dai text va underline moi bang nhau
        */
//        textPaint.setUnderlineText(true);
//        textPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC));
//        textPaint.setTextAlign(Paint.Align.RIGHT);
//        textPaint.getTextBounds(textStr, 0, textStr.length(), textBounds);
//        canvas.save();
//        canvas.drawRect(0, 0, textBounds.right, textBounds.height() , RECT_PAINT);
//        canvas.drawCircle(0,textBounds.height() - textBounds.bottom,40, RECT_PAINT);
//        canvas.drawText(textStr, 0, -textBounds.top, textPaint);
//        canvas.restore();


    }

}