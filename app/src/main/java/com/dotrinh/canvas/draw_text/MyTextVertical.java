/*
 * Created by dotrinh on 13:20, 11/11/2022
 * Copyright (c) 2022. dotr Inc. All rights reserved.
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

import com.dotrinh.canvas.tool.Tool;

import java.util.ArrayList;
import java.util.List;

import static com.dotrinh.protool.StringTool.findMaxWidthOfInTextBound;

public class MyTextVertical extends View {

    TextPaint textPaint;
    static Paint RECT_PAINT;
    static Paint LINE_PAINT;
    Rect textBounds;
    //    private String textStr = "あえおaby";
    private String textStr = "あいえお\n高田様";

    public MyTextVertical(Context context) {
        super(context);
        initialize();
    }

    public MyTextVertical(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public MyTextVertical(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    TextItem textItem;
    private void initialize() {
        RECT_PAINT = new Paint();
        RECT_PAINT.setStrokeWidth(1);
        RECT_PAINT.setColor(Color.BLUE);
        RECT_PAINT.setStyle(Paint.Style.STROKE);
        RECT_PAINT.setAntiAlias(true);

        textBounds = new Rect();
        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(Tool.convertSpToPx(getContext(), 30));
        textPaint.setAntiAlias(true);

        textItem = new TextItem();
        textItem.txt = textStr;
        textItem.isVertical = true;
        textItem.process();
    }

    @Override
    protected void onDraw(Canvas canvas){
        textItem.drawText(canvas);
    }
//
//    void drawText(Canvas canvas) {
//        textPaint.setUnderlineText(true);
//        textPaint.getTextBounds(textStr, 0, textStr.length(), textBounds);
//        canvas.save();
//        canvas.translate(50, textPaint.measureText(textStr) + 100);
//        canvas.rotate(-90);
//        canvas.drawRect(0, 0, textPaint.measureText(textStr), textBounds.height(), RECT_PAINT);
//        canvas.drawText(textStr, 0, -textBounds.top, textPaint);
//        canvas.restore();
//    }

    class StringInfo {//INFO của từng line
        String txt;
        Rect rect;
    }

    class TextItem {

        String txt;
        List<StringInfo> verticals;
        boolean isVertical;

        void process() {
            verticals = new ArrayList<>();
            String[] lines = txt.split("\\r?\\n");
            for (String line : lines) {
                StringInfo lineInfo = new StringInfo();
                lineInfo.txt = addEndLineToString(line);
                lineInfo.rect = measureStringBound(lineInfo.txt);
                verticals.add(lineInfo);
            }
        }

        String addEndLineToString(String str) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                if (i > 0) {
                    sb.append("\n");
                }
                sb.append(str.charAt(i));
            }
            return sb.toString();
        }

        Rect measureStringBound(String str) {
            Rect bound = new Rect();
            textPaint.getTextBounds(str, 0, str.length(), bound);
            return bound;//width height x y cua str
        }

        void drawText(Canvas canvas) {
            canvas.save();
            int drawAtX = 180; //vẽ line đầu tiên ở vị trí 0
            canvas.translate(drawAtX, 0);
            for (StringInfo line : verticals) {
                StaticLayout.Builder letG = StaticLayout.Builder.obtain(line.txt, 0, line.txt.length(), textPaint, line.rect.width());
                letG.setAlignment(Layout.Alignment.ALIGN_NORMAL);
                StaticLayout myStaticLayout = letG.build();
//                canvas.rotate(-90);
                myStaticLayout.draw(canvas);
                canvas.translate(findMaxWidthOfInTextBound(line.txt, textPaint), 0);
            }
            canvas.restore();
            textPaint.setTextSize(88);
            // canvas.drawText("hihihi高田",0,StringTool.getTextBoundHeightWithBottom("hihihi",textPaint),textPaint);
        }
    }

}