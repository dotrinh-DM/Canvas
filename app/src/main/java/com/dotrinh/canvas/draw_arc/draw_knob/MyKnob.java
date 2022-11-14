/*
 * Created by dotrinh on 15:58, 11/11/2022
 * Copyright (c) 2022. dotr Inc. All rights reserved.
 */

package com.dotrinh.canvas.draw_arc.draw_knob;

import static android.view.MotionEvent.INVALID_POINTER_ID;
import static com.dotrinh.canvas.tool.LogUtil.LogI;
import static com.dotrinh.canvas.tool.StringTool.getTextBoundHeightWithBottom;

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
import com.dotrinh.canvas.tool.StringTool;
import com.dotrinh.canvas.tool.Tool;

public class MyKnob extends View implements View.OnTouchListener {

    Paint progress_realtime_paint;
    Paint BG_paint;
    Paint circle_paint;
    private Paint line;

    public enum CONTENT_TYPE {
        TEXT_ONLY,
        IMG_ONLY,
        IMG_AND_TEXT
    }

    public CONTENT_TYPE current_content = CONTENT_TYPE.TEXT_ONLY;

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

        center_text_paint = new TextPaint();
        center_text_paint.setTypeface(Typeface.SERIF);
        center_text_paint.setStrokeWidth(7);
        center_text_paint.setTextSize(40);
        center_text_paint.setAntiAlias(true);
        center_text_paint.setPathEffect(null);
        center_text_paint.setColor(Color.BLUE);
        center_text_paint.setStyle(Paint.Style.STROKE);

        right_text_paint = new TextPaint();
        right_text_paint.setTypeface(Typeface.SERIF);
        right_text_paint.setStrokeWidth(7);
        right_text_paint.setTextSize(Tool.convertSpToPx(getContext(), 40));
        right_text_paint.setAntiAlias(true);
        right_text_paint.setPathEffect(null);
        right_text_paint.setColor(Color.BLUE);
        right_text_paint.setStyle(Paint.Style.STROKE);

        progress_realtime_paint = new Paint();
        progress_realtime_paint.setColor(getResources().getColor(R.color.white, null));
        progress_realtime_paint.setAntiAlias(true);

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
        img_shape_paint = new Paint();
        img_shape_paint.setAntiAlias(true);
        img_shape_paint.setColor(Color.MAGENTA);

        //line
        line = new Paint();
        line.setTypeface(Typeface.SERIF);
        line.setStrokeWidth(2);
        line.setAntiAlias(true);
        line.setPathEffect(null);
        line.setColor(Color.BLUE);
        line.setStyle(Paint.Style.STROKE);
    }

    public int relative_size;
    public RectF rect_outer;
    public int radius;
    public float degree = 0;

    //-----------------center single text
    TextPaint center_text_paint;
    public String center_string = "4000";

    //-----------------center single text
    public Paint img_shape_paint;
    public Bitmap center_bitmap;
    public Rect center_bmp_rect;

    //-----------------left image & right text group
    public Rect bmp_txt_grp_rect;
    public int x_Grp;
    public int y_Grp;

    public Bitmap left_bmp;
    public Rect left_bmp_rect;

    public TextPaint right_text_paint;
    public Rect right_txt_rect;
    public float y_baseline_txt;


    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int xOld, int yOld) {
        super.onSizeChanged(newWidth, newHeight, xOld, yOld);
        int left = getWidth() / 2 - relative_size;
        int right = getWidth() / 2 + relative_size;
        int top = getHeight() / 2 - relative_size;
        int bottom = getHeight() / 2 + relative_size;
        rect_outer = new RectF(left, top, right, bottom); //hình vuông ngoài cùng
        radius = relative_size - 40;

        //center img case
        center_bmp_rect = new Rect(0, 0, (int) (relative_size / 1.2), (int) (relative_size / 1.2));

        //left img & txt case
        bmp_txt_grp_rect = new Rect(0, 0, (int) (radius * 1.55f), radius);
        x_Grp = (int) (rect_outer.centerX() - bmp_txt_grp_rect.width() / 2f);
        y_Grp = (int) (rect_outer.centerY() - bmp_txt_grp_rect.height() / 2f);
        int w_Grp = x_Grp + bmp_txt_grp_rect.width();
        int h_Grp = y_Grp + bmp_txt_grp_rect.height();
        bmp_txt_grp_rect = new Rect(x_Grp, y_Grp, w_Grp, h_Grp);//update right, bottom

        //img
        if (left_bmp != null) {
            float left_area_width = bmp_txt_grp_rect.width() * 0.5f;
            left_bmp = Bitmap.createScaledBitmap(left_bmp, (int) left_area_width, (int) left_area_width, false);
            float middle_y = bmp_txt_grp_rect.centerY() - left_bmp.getHeight() / 2f; //draw from top - left
            left_bmp_rect = new Rect((int) (bmp_txt_grp_rect.centerX() - left_area_width), (int) middle_y, bmp_txt_grp_rect.centerX(), (int) (middle_y + left_bmp.getHeight()));
        }
        //config right text
        if (left_bmp_rect != null) {
            float right_area_width = bmp_txt_grp_rect.width() * 0.5f;

            //calculate text font size
            float txtSize = DrawKnobActivity.getFitTextSizeHorizontal(right_text_paint, right_area_width, "123");
            right_text_paint.setTextSize(txtSize); //calculate text font size

            y_baseline_txt = (bmp_txt_grp_rect.centerY() + getTextBoundHeightWithBottom(center_string, right_text_paint) / 2f); //draw from baseline
            right_txt_rect = new Rect(bmp_txt_grp_rect.centerX(), left_bmp_rect.top, bmp_txt_grp_rect.centerX() + (int) right_area_width, (int) y_baseline_txt);
        }

        // show default knob val
        distance_histri.dx = (int) (0.99 * max_val);
        calculate_degree();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), BG_paint); //full fill
        canvas.drawRect(rect_outer, center_text_paint);
        canvas.drawArc(rect_outer, 135, degree, true, progress_realtime_paint);//realtime changes
        canvas.drawCircle(rect_outer.centerX(), rect_outer.centerY(), radius, circle_paint); //big circle layer
        // canvas.drawCircle(rect_outer.centerX(), rect_outer.centerY(), 15, center_text_paint); //test dot

        //-----------------center single text case
        if (current_content == CONTENT_TYPE.TEXT_ONLY) { //text length may be changed
            float xTxt = rect_outer.centerX() - StringTool.getTextBoundWidthOfString(center_string, center_text_paint) / 2f;
            float yTxt = (rect_outer.top + (rect_outer.height() / 2f + getTextBoundHeightWithBottom(center_string, center_text_paint) / 2f));
            canvas.drawText(center_string, xTxt, yTxt, center_text_paint);
        }

        //-----------------center single image case
        if (current_content == CONTENT_TYPE.IMG_ONLY) {
            float xBmp = rect_outer.centerX() - center_bmp_rect.width() / 2f;
            float yBmp = rect_outer.centerY() - center_bmp_rect.height() / 2f;
            center_bmp_rect = new Rect((int) xBmp, (int) yBmp, (int) (xBmp + center_bmp_rect.width()), (int) (yBmp + center_bmp_rect.height()));
            canvas.drawRect(center_bmp_rect, center_text_paint);
            canvas.drawBitmap(center_bitmap, new Rect(0, 0, center_bitmap.getWidth(), center_bitmap.getHeight()), center_bmp_rect, img_shape_paint);
        }

        //-----------------left image & right text GROUP case
        if (current_content == CONTENT_TYPE.IMG_AND_TEXT) {
            canvas.drawRect(bmp_txt_grp_rect, center_text_paint);
            //center line
            canvas.drawLine(bmp_txt_grp_rect.centerX(), bmp_txt_grp_rect.top, bmp_txt_grp_rect.centerX(), bmp_txt_grp_rect.bottom, line);

            //draw left bmp
            canvas.drawRect(left_bmp_rect, img_shape_paint);//black bg
            canvas.drawBitmap(left_bmp, new Rect(0, 0, left_bmp.getWidth(), left_bmp.getHeight()), left_bmp_rect, img_shape_paint);

            //right text
            canvas.drawRect(right_txt_rect, img_shape_paint);//black bg
            canvas.drawText("x " + center_string, bmp_txt_grp_rect.centerX(), y_baseline_txt, right_text_paint);
        }
    }

    //----------------- TOUCH LOGIC -----------------
    private Point downPt = new Point(); //must be global
    private Point realtimePt = new Point(); //must be global
    private int mActivePointerID = INVALID_POINTER_ID;

    enum EVENT_STAT {
        NONE,
        MOVE
    }

    //class
    class Distance_x_y {
        float dx;
        float dy;
    }

    EVENT_STAT stat = EVENT_STAT.NONE;

    int min_val = 0;
    // int max_val = 9;
    // int max_val = 939 - 599;//340
    int max_val = (962 - 940) * 30; //fake range 660 units
    Distance_x_y distance_histri = new Distance_x_y();

    public void calculate_degree() {
        degree = (distance_histri.dx / (float) max_val) * (405 - 135);
        center_string = String.valueOf(Math.round(distance_histri.dx));
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
                boolean is_inside = rect_outer.contains(downPt.x, downPt.y);
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
                        if (distance_histri.dx + dX < min_val) {
                            distance_histri.dx = min_val;
                            calculate_degree();//important
                            invalidate();
                            return true;
                        }
                        if (distance_histri.dx + dX > max_val) {
                            distance_histri.dx = max_val;
                            calculate_degree();//important
                            invalidate();
                            return true;
                        }

                        distance_histri.dx += dX;//important
                        distance_histri.dy += dY;//important
                        // if (realX < downPt.x) {
                        //     LogI("cho x be di: " + dx_dy.dx);
                        // } else if (realX > downPt.x) {
                        //     LogI("cho x to len: " + dx_dy.dx);
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
                            realValToSend = (int) distance_histri.dx;
                        } else if (max_val == 340) {
                            realValToSend = (int) (599 + distance_histri.dx);
                        } else if (max_val == 660) {
                            realValToSend = (int) (940 + (distance_histri.dx / 30));
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
