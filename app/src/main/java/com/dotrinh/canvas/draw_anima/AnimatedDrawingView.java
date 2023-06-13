/*
 * Created by dotrinh on 6/13/23, 11:44 AM
 * Copyright (c) 2023. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.draw_anima;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class AnimatedDrawingView extends View {
    public Paint paint;
    public float radius;

    public AnimatedDrawingView(Context context) {
        super(context);
        init();
    }

    public AnimatedDrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        radius = 0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw a circle with the animated radius
        float centerX = getWidth() / 2f;
        float centerY = getHeight() / 2f;
        canvas.drawCircle(centerX, centerY, radius, paint);
    }
}

