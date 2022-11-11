/*
 * Created by dotrinh on 11:41, 11/11/2022
 * Copyright (c) 2022. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.tool;

import android.content.Context;

public class Tool {
    public static float convertSpToPx(Context ctx, int sp) {
        float scaledDensity = ctx.getResources().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }


}
