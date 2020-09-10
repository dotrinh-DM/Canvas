/*
 * Created by dotrinh on 7/21/20 12:28 PM
 * Copyright (c) 2020. USE Inc. All rights reserved.
 */

package com.dotrinh.canvas;

public class TransactionTime {
    private long start;
    private long end;

    public TransactionTime(long start) {
        this.start = start;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getDuration() {
        if (this.start > 0 && this.end > 0) {
            return this.end - this.start;
        } else {
            return 0;
        }
    }
}
