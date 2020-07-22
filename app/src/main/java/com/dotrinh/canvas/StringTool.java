/*
 * Created by dotrinh on 7/22/20 8:02 PM
 * Copyright (c) 2020. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas;

import android.graphics.Rect;
import android.text.TextPaint;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTool {

    public final static String TAG = "Pro Tool";

    //region CALCULATING SIZE OF STRING
    public static int getTextBoundWidthOfString(String text, TextPaint textPaint) {
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    public static int getTextBoundHeightWithBottom(String text, TextPaint textPaint) {
        Rect textBounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), textBounds);
        return textBounds.height();
    }

    public static int getTextBoundHeightWithoutBottom(String text, TextPaint textPaint) {
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height() - bounds.bottom; // we draw from baseline
    }

    public static int getStringBottom(String text, TextPaint textPaint) {
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.bottom; // we get from baseline
    }

    public static int getSumHeightOfCharsWithoutSpaceEachChars(String text, TextPaint paint) {
        char[] chars = text.toCharArray();
        int sum = 0;
        for (char c : chars) {
            String str = String.valueOf(c);
            sum += getTextBoundHeightWithoutBottom(str, paint); // 1, 2, 3, a, b, c, あ, い...
        }
        return sum;
    }

    //max width char
    public static int findMaxWidthOfInTextBound(String text, TextPaint textPaint) {
        int maxLineWidth = 0;
        Rect bounds = new Rect();
        int count = text.length();
        char[] chars = text.toCharArray();
        for (int j = 0; j < count; j++) {
            String str = String.valueOf(chars[j]);
            textPaint.getTextBounds(str, 0, str.length(), bounds);
            maxLineWidth = Math.max(maxLineWidth, bounds.width());
        }
        return maxLineWidth;
    }

    /**
     * max height char
     */
    public static int findMaxHeightInTextBound(String text, TextPaint textPaint) {
        int maxLineHeight = 0;
        Rect bounds = new Rect();
        int c = text.length();
        char[] chars = text.toCharArray();
        for (int j = 0; j < c; j++) {
            String str = String.valueOf(chars[j]);
            textPaint.getTextBounds(str, 0, str.length(), bounds);
            maxLineHeight = Math.max(maxLineHeight, bounds.height());
        }
        return maxLineHeight;
    }

    /**
     * find index that has max width in string
     *
     * @param text
     * @param textPaint
     * @return
     */
    public static int findPosMaxWidthInTextBound(String text, TextPaint textPaint) {
        int maxWidth = 0;
        int index = 0;
        Rect bounds = new Rect();
        int c = text.length();
        char[] chars = text.toCharArray();
        for (int j = 0; j < c; j++) {
            String str = String.valueOf(chars[j]);
            textPaint.getTextBounds(str, 0, str.length(), bounds);
            if (bounds.width() > maxWidth) {
                maxWidth = bounds.width();
                index = j;
            }
        }
        return index;
    }
    //endregion

    public static int countLines(String str) {
        Matcher m = Pattern.compile("\r\n|\r|\n").matcher(str);
        int lines = 1;
        while (m.find()) {
            lines++;
        }
        return lines;
    }

    public static String getLongestStringInMultiLines(String multilineStr, TextPaint textPaint) {
        //find max width by line && find max height by line
        int maxIndex = 0;
        float maxLineWidth = 0;
        String[] lines = multilineStr.split("\\r?\\n");
        int realCount = lines.length;
        Rect lineTextBound = new Rect();

        for (int j = 0; j < realCount; j++) {
            textPaint.getTextBounds(lines[j], 0, lines[j].length(), lineTextBound);

            //using measure
            if (checkSpaceInHeadAndTail(lines[j])) {
                if (textPaint.measureText(lines[j]) > maxLineWidth) {
                    maxLineWidth = textPaint.measureText(lines[j]);
                    maxIndex = j;
                }
            }
            //using textbound
            else {
                if (lineTextBound.width() > maxLineWidth) {
                    maxLineWidth = lineTextBound.width();
                    maxIndex = j;
                }
            }
        }
        return lines[maxIndex];
    }

    public static boolean checkSpaceInHeadAndTail(String str) {
        boolean isSpace = false;
        for (int i = 0; i < str.length(); i++) {
            if (i == 0 || (i == str.length() - 1)) {
                if (String.valueOf(str.charAt(i)).equals(" ") || String.valueOf(str.charAt(i)).equals("　")) { // check space in Vi, En, Jp, Cn...
                    isSpace = true;
                    break;
                }
            }
        }
        return isSpace;
    }

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }


}
