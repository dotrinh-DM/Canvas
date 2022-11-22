/*
 * Created by dotrinh on 6/30/20 8:27 PM
 * Copyright (c) 2020. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.tool;

/**
 * -----------how to use-----------
 * start: abc = new TransactionTime(System.currentTimeMillis());
 * end:   abc.setEnd(System.currentTimeMillis());
 * check:
   if(abc.getDuration() < 1000) {
       smaller 1 sec
   }
 *
 */
public class TransactionTime {
    private long start;
    private long end;

    public TransactionTime(long start) {
        this.start = start;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getDuration() { //millisecond
        if (this.start > 0 && this.end > 0) {
            return this.end - this.start;
        } else {
            return 0;
        }
    }
}
