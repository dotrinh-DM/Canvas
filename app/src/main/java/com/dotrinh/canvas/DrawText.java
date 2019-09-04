/*
 * Copyright (c) 2019. Trinh Thanh Do
 */

package com.dotrinh.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class DrawText extends View {

    TextPaint textPaint;
    static Paint RECT_PAINT;
    static Paint LINE_PAINT;
    Rect textBounds;
    //    private String textStr = "あえおaby";
    private String textStr = "あいえお";

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
        RECT_PAINT.setStrokeWidth(1);
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
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.LEFT);
//        textPaint.setUnderlineText(true); // TODO: 2019-09-04 chu y khi ve underline
//        textPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC));
        textPaint.setTextSize(Tool.convertSpToPx(getContext(), 110));
        textPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawMyLine(canvas);
        drawTextRotateAlignLEFT(canvas);
    }

    void drawTextRotateAlignLEFT(Canvas canvas) {
        /*
            Notes:
            1. textbound la hinh chu nhat vua khit voi text (fit to text content), khong bao gom left va bottom
            2. -textBounds.top = textBounds.height() - textBounds.bottom (text ve tu baseline)
        */

        textPaint.getTextBounds(textStr, 0, textStr.length(), textBounds);
        canvas.save();
        canvas.translate(50, textBounds.width() + 100);
        canvas.rotate(-90);
        canvas.drawText(textStr, -textBounds.left, -textBounds.top, textPaint);
        drawMyRect(canvas);
        canvas.restore();
    }

    void drawMyRect(Canvas canvas) {
        canvas.drawRect(0, 0, textBounds.width(), textBounds.height(), RECT_PAINT);
    }

    void drawMyLine(Canvas canvas) {
        canvas.drawLine(0, 100, getWidth(), 100, LINE_PAINT);
    }
}