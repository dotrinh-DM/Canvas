/*
 * Created by dotrinh on 11:41, 11/11/2022
 * Copyright (c) 2022. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.tool;

import android.util.Log;

public class LogUtil {
    public static void LogD(String Tag, String Message) {
        Log.d(Tag, Message);
    }

    public static void LogE(String Tag, String Message) {
        Log.e(Tag, Message);
    }

    public static void LogI(String Message) {
        Log.i("viewlog", Message);
    }

    public static void LogW(String Tag, String Message) {
        Log.w(Tag, Message);
    }

}

