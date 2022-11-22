/*
 * Created by dotrinh on 13:30, 11/11/2022
 * Copyright (c) 2022. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.draw_text;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.dotrinh.canvas.R;

public class DrawTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_act);
    }
}
