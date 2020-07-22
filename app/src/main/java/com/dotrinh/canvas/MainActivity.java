/*
 * Created by dotrinh on 7/22/20 8:04 PM
 * Copyright (c) 2020. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import static com.dotrinh.protool.LogUtil.LogI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogI("deeeeeeeeeeeeeeee");
    }
}
