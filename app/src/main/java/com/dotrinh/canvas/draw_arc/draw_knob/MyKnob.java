/*
 * Created by dotrinh on 15:58, 11/11/2022
 * Copyright (c) 2022. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.draw_arc.draw_knob;

import static android.view.MotionEvent.INVALID_POINTER_ID;
import static com.dotrinh.canvas.tool.LogUtil.LogI;
import static com.dotrinh.canvas.tool.StringTool.getTextBoundHeightWithBottom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dotrinh.canvas.R;
import com.dotrinh.canvas.tool.Tool;

public class MyKnob extends View implements View.OnTouchListener {

    TextPaint textPaint;
    Paint myPaint;
    Paint BG_paint;
    Paint circle_paint;

    RectF testRect;

    public MyKnob(Context context) {
        super(context);
        initialize();
    }

    public MyKnob(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public MyKnob(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        setOnTouchListener(this);

        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.SERIF);
        textPaint.setStrokeWidth(7);
        textPaint.setTextSize(Tool.convertSpToPx(getContext(), 40));
        textPaint.setAntiAlias(true);
        textPaint.setPathEffect(null);
        textPaint.setColor(Color.BLUE);
        textPaint.setStyle(Paint.Style.STROKE);

        myPaint = new Paint();
        myPaint.setColor(getResources().getColor(R.color.white, null));
        myPaint.setAntiAlias(true);

        BG_paint = new Paint();
        BG_paint.setStrokeWidth(80);
        BG_paint.setStyle(Paint.Style.FILL);
        BG_paint.setColor(Color.GRAY);
        // BG_paint.setColor(Color.parseColor("#e3e3e3"));
        BG_paint.setStrokeCap(Paint.Cap.ROUND);
        BG_paint.setAntiAlias(true);


        circle_paint = new Paint();
        circle_paint.setStyle(Paint.Style.FILL);
        circle_paint.setColor(Color.LTGRAY);
        circle_paint.setStrokeCap(Paint.Cap.ROUND);
        circle_paint.setAntiAlias(true);

        // img shape paint
        imgShapePaint = new Paint();
        imgShapePaint.setAntiAlias(true);
        imgShapePaint.setColor(Color.BLACK);

    }

    int size = 400;
    final int radius = size - 50;
    float degree = 0;

    //single txt or img
    String center_string = "x3";
    Paint imgShapePaint;
    public Bitmap center_bitmap;
    public Rect bitmap_rect = new Rect(0, 0, 200, 200);

    //txt & img group
    public Bitmap left_bmp;
    public Rect left_bmp_rect = new Rect();
    public Rect right_txt_rect = new Rect();
    public Rect bmp_txt_grp_rect = new Rect(0, 0, (int) (radius * 1.55f), radius);

    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int xOld, int yOld) {
        super.onSizeChanged(newWidth, newHeight, xOld, yOld);
        int left = getWidth() / 2 - size;
        int top = getHeight() / 2 - size;
        testRect = new RectF(left, top, getWidth() / 2f + size, getHeight() / 2f + size);
        dx_dy.right = (int) (0.7 * max_val);
        calculate_degree();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), BG_paint); //full fill
        canvas.drawRect(testRect, textPaint);
        canvas.drawArc(testRect, 135, degree, true, myPaint);//realtime
        canvas.drawCircle(testRect.centerX(), testRect.centerY(), radius, circle_paint); //big circle layer
        // canvas.drawCircle(testRect.centerX(), testRect.centerY(), 15, textPaint); //test dot

        //-----------------center text
        // float xTxt = testRect.centerX() - getTextBoundWidthOfString(center_string, textPaint) / 2f;
        // float yTxt = (testRect.top + (testRect.height() / 2f + getTextBoundHeightWithBottom(center_string, textPaint) / 2f));
        // canvas.drawText(center_string, xTxt, yTxt, textPaint);

        //-----------------center image
        // float xBmp = testRect.centerX() - bitmap_rect.width() / 2f;
        // float yBmp = testRect.centerY() - bitmap_rect.height() / 2f;
        // bitmap_rect = new Rect((int) xBmp, (int) yBmp, (int) (xBmp + bitmap_rect.width()), (int) (yBmp + bitmap_rect.height()));
        // canvas.drawRect(bitmap_rect, textPaint);
        // canvas.drawBitmap(center_bitmap, new Rect(0, 0, center_bitmap.getWidth(), center_bitmap.getHeight()), bitmap_rect, imgShapePaint);

        //-----------------center image & text group
        float xGrp = testRect.centerX() - bmp_txt_grp_rect.width() / 2f;
        float yGrp = testRect.centerY() - bmp_txt_grp_rect.height() / 2f;
        bmp_txt_grp_rect = new Rect((int) xGrp, (int) yGrp, (int) (xGrp + bmp_txt_grp_rect.width()), (int) (yGrp + bmp_txt_grp_rect.height()));
        // canvas.drawRect(bmp_txt_grp_rect, textPaint);
            // textPaint.setTextSize(bmp_txt_grp_rect.width()/4f);

            //right bmp
            int yVertical_bmp = (int) (bmp_txt_grp_rect.top + (bmp_txt_grp_rect.height() / 2f - left_bmp.getHeight() / 2f)); //draw from top - left
            left_bmp_rect = new Rect((int) xGrp, yVertical_bmp, (int) (xGrp + left_bmp.getWidth()), (int) (yVertical_bmp + left_bmp.getHeight()));
            // canvas.drawRect(left_bmp_rect, imgShapePaint);//black bg
            canvas.drawBitmap(left_bmp, new Rect(0, 0, left_bmp.getWidth(), left_bmp.getHeight()), left_bmp_rect, imgShapePaint);

            //left text
            float yTxt_2 = (bmp_txt_grp_rect.top + (bmp_txt_grp_rect.height() / 2f + getTextBoundHeightWithBottom(center_string, textPaint)/2f)); //draw from baseline
            right_txt_rect = new Rect((int) left_bmp_rect.right, left_bmp_rect.top, bmp_txt_grp_rect.right, (int) yTxt_2);
            // canvas.drawRect(right_txt_rect, imgShapePaint);//black bg
            canvas.drawText(center_string, left_bmp_rect.right, yTxt_2, textPaint);

        // float textWidthFactor = getTextBoundWidthOfString(center_string, textPaint) / (float) bitmap_txt_group_rect.width();
        // float bmpWidthFactor = (1 - textWidthFactor);

    }

    private Point downPt = new Point(); //must be global
    private Point realtimePt = new Point(); //must be global
    private int mActivePointerID = INVALID_POINTER_ID;

    enum EVENT_STAT {
        NONE, MOVE
    }

    private Point ball = new Point();
    EVENT_STAT stat = EVENT_STAT.NONE;

    int min_val = 0;
    int max_val = 598;
    // int max_val = 939 - 599;//340
    // int max_val = (962 - 940) * 30; //fake range 660 units
    Rect dx_dy = new Rect();

    public void calculate_degree() {
        degree = (dx_dy.right / (float) max_val) * (405 - 135);
        center_string = String.valueOf(Math.round(dx_dy.right));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        LogI("touch");

        final int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                mActivePointerID = event.getPointerId(0);
                downPt.x = (int) event.getX();
                downPt.y = (int) event.getY();
                boolean is_inside = testRect.contains(downPt.x, downPt.y);
                if (is_inside) {
                    stat = EVENT_STAT.MOVE;
                    LogI("trong HCN");
                } else {
                    stat = EVENT_STAT.NONE;
                    LogI("ngoai HCN");
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = event.findPointerIndex(mActivePointerID);
                int realX = (int) event.getX(pointerIndex);
                int realY = (int) event.getY(pointerIndex);

                switch (stat) {
                    case MOVE: {
                        realtimePt.x = realX;
                        realtimePt.y = realY;

                        int dX = realtimePt.x - downPt.x;
                        int dY = realtimePt.y - downPt.y;

                        //guard: check if smaller min || greater max
                        if (dx_dy.right + dX < min_val) {
                            dx_dy.right = min_val;
                            calculate_degree();//important
                            invalidate();
                            return true;
                        }
                        if (dx_dy.right + dX > max_val) {
                            dx_dy.right = max_val;
                            calculate_degree();//important
                            invalidate();
                            return true;
                        }

                        dx_dy.right += dX;//important
                        dx_dy.top += dY;//important
                        // if (realX < downPt.x) {
                        //     LogI("cho x be di: " + dx_dy.right);
                        // } else if (realX > downPt.x) {
                        //     LogI("cho x to len: " + dx_dy.right);
                        // }
                        // if (realY < downPt.y) {
                        //     LogI("cho y be di");
                        // } else if (realY > downPt.y) {
                        //     LogI("cho y to len");
                        // }

                        calculate_degree();//important

                        // last pos is old pos - smooth
                        downPt.x = realX;
                        downPt.y = realY;

                        //---- special, send to FW
                        int realValToSend = 0;
                        if (max_val == 598) {
                            realValToSend = dx_dy.right;
                        } else if (max_val == 340) {
                            realValToSend = 599 + dx_dy.right;
                        } else if (max_val == 660) {
                            realValToSend = 940 + (dx_dy.right / 30);
                        }
                        LogI("send------------>: " + realValToSend);
                        break;
                    }
                }
                invalidate();
                break;
            }
        }
        return true;
    }
}
