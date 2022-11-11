/*
 * Created by dotrinh on 16:05, 11/11/2022
 * Copyright (c) 2022. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.draw_arc.draw_knob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.dotrinh.canvas.R;

public class DrawKnobActivity extends AppCompatActivity {

    public Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_knob);
        ctx = this;
        MyKnob myKnob = findViewById(R.id.knob);
        myKnob.center_bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.verified);
        myKnob.left_bmp = BitmapFactory.decodeResource(getResources(),R.drawable.editampnote);
    }
}