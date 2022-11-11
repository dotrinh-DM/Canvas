/*
 * Created by dotrinh on 7/22/20 8:04 PM
 * Copyright (c) 2020. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.LED;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import static com.dotrinh.protool.LogUtil.LogI;

import com.dotrinh.canvas.R;

public class LED_MainActivity extends AppCompatActivity {

    public static Point dotrinhSize = new Point();
    public static Bitmap BACKGROUND_BITMAP;
    public static Point BACKGROUND_SIZE = null;

    public static Bitmap ACTIVE_BITMAP;
    public static Point ACTIVE_SIZE = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BACKGROUND_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.levelmeter_bg);
        BACKGROUND_SIZE = new Point(BACKGROUND_BITMAP.getWidth(), BACKGROUND_BITMAP.getHeight());

        ACTIVE_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.active_meter);
        ACTIVE_SIZE = new Point(ACTIVE_BITMAP.getWidth(), ACTIVE_BITMAP.getHeight());
        MyClipPath_LED level = findViewById(R.id.Level);
        level.startTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindowManager().getDefaultDisplay().getSize(dotrinhSize);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogI("deeeeeeeeeeeeeeee");
    }
}
