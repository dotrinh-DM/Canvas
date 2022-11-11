/*
 * Created by dotrinh on 6/30/20 8:27 PM
 * Copyright (c) 2020. dotr Inc. All rights reserved.
 */
package com.dotrinh.canvas.tool;

import android.content.Context;

public class Tool {
    public static float convertSpToPx(Context ctx, int sp) {
        float scaledDensity = ctx.getResources().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }


}
