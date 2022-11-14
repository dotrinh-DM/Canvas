/*
 * Created by dotrinh on 16:05, 11/11/2022
 * Copyright (c) 2022. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.draw_arc.draw_knob;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import com.dotrinh.canvas.R;
import com.dotrinh.canvas.tool.StringTool;

public class DrawKnobActivity extends AppCompatActivity {

    public Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_knob);
        ctx = this;
        int testSize = 110;
        MyKnob myKnob = findViewById(R.id.knob_1);
        myKnob.relative_size = (int) convertDpToPx(testSize);
        myKnob.current_content = MyKnob.CONTENT_TYPE.TEXT_ONLY;

        MyKnob myKnob2 = findViewById(R.id.knob_2);
        myKnob2.relative_size = (int) convertDpToPx(testSize);
        myKnob2.center_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.verified);
        myKnob2.current_content = MyKnob.CONTENT_TYPE.IMG_ONLY;

        MyKnob myKnob3 = findViewById(R.id.knob_3);
        myKnob3.relative_size = (int) convertDpToPx(testSize);
        myKnob3.left_bmp = BitmapFactory.decodeResource(getResources(), R.drawable.editampnote);
        myKnob3.current_content = MyKnob.CONTENT_TYPE.IMG_AND_TEXT;
    }

    public static float getFitTextSizeHorizontal(TextPaint currentPaint, float newWidth, String currentText) {
        float oldWidth = StringTool.getTextBoundWidthOfString(currentText, currentPaint); //calculate more accurately, dont use measure text at here
        float factor = (newWidth / oldWidth);
        return currentPaint.getTextSize() * factor;
    }

    public float convertDpToPx(float dp) {
        return dp * ((float) getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}