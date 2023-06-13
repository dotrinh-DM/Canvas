/*
 * Created by dotrinh on 6/13/23, 11:49 AM
 * Copyright (c) 2023. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.draw_anima;

import android.animation.ValueAnimator;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.dotrinh.canvas.R;

public class AnimActivity extends AppCompatActivity {

    private AnimatedDrawingView animatedDrawingView;
    private ValueAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anima);

        animatedDrawingView = findViewById(R.id.Level);

        animator = ValueAnimator.ofFloat(0f, 1100f);
        animator.setDuration(5000); // Animation duration in milliseconds
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedDrawingView.radius = (float) animation.getAnimatedValue();
                animatedDrawingView.invalidate();
            }
        });
        animator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        animator.cancel();
    }

}
