package com.dotrinh.canvas;

import android.content.Context;
import android.graphics.Paint;

import java.lang.reflect.Field;

public class Tool {
    public static float convertSpToPx(Context ctx, int sp) {
        float scaledDensity = ctx.getResources().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }


}
