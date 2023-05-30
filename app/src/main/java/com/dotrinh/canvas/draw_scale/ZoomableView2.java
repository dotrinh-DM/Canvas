package com.dotrinh.canvas.draw_scale;

import static com.dotrinh.canvas.tool.LogUtil.LogI;
import static com.dotrinh.protool.StringTool.getTextBoundHeightWithBottom;
import static com.dotrinh.protool.StringTool.getTextBoundWidthOfString;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.dotrinh.canvas.R;
import com.dotrinh.canvas.tool.Tool;

public class ZoomableView2 extends View {

    //touch & scale
    private static final int INVALID_POINTER_ID = -1;

    public Bitmap mImageBmp;
    private float mPosX;
    private float mPosY;

    private float mLastTouchX;
    private float mLastTouchY;
    private int mActivePointerId = INVALID_POINTER_ID;

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    //REAL SIZE CUSTOM VIEW
    int newWidth;
    int newHeight;

    //rect
    Rect tapeRect;
    Paint tapePaint;

    //text
    TextPaint textPaint;

    //bitmap
    Paint bmpPaint;
    Rect bmpRect;

    public ZoomableView2(Context context) {
        this(context, null, 0);
        initialize(context);

    }

    public ZoomableView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initialize(context);

    }

    public ZoomableView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mImageBmp = BitmapFactory.decodeResource(getResources(), R.drawable.phunuvn);

        //text
        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.SERIF);
        textPaint.setStrokeWidth(7);
        textPaint.setTextSize(Tool.convertSpToPx(getContext(), 20));
        textPaint.setAntiAlias(true);
        textPaint.setPathEffect(null);
        textPaint.setColor(Color.BLUE);
        textPaint.setStyle(Paint.Style.STROKE);

        //white area
        tapePaint = new Paint();
        tapePaint.setColor(Color.WHITE);
        tapePaint.setStrokeWidth(7);
        tapePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        tapePaint.setAntiAlias(true);

        //bmp
        bmpPaint = new Paint();
        bmpPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int newWidth, int newHeight, int xOld, int yOld) {
        super.onSizeChanged(newWidth, newHeight, xOld, yOld);
        this.newWidth = newWidth;
        this.newHeight = newHeight;

        //bmp
        bmpRect = new Rect(0, 0, mImageBmp.getWidth() / 3, mImageBmp.getHeight() / 3);

        //init white rect
        int tapeHeight = 400;
        int tapeWidth = 1900;
        int left = (int) (newWidth / 2f - tapeWidth / 2f);
        int top = (int) (newHeight / 2f - tapeHeight / 2f);
        int right = left + tapeWidth;
        int bottom = top + tapeHeight;
        tapeRect = new Rect(0, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mScaleDetector.onTouchEvent(ev);

        final int action = ev.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                final float x = ev.getX();
                final float y = ev.getY();

                mLastTouchX = x;
                mLastTouchY = y;
                mActivePointerId = ev.getPointerId(0);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                final float x = ev.getX(pointerIndex);
                final float y = ev.getY(pointerIndex);

                // Only move if the ScaleGestureDetector isn't processing a gesture.
                if (!mScaleDetector.isInProgress()) {
                    LogI("111111 --------------------------------- mScaleFactor: " + mScaleFactor);
                    final float dx = (x - mLastTouchX) / mScaleFactor;
                    final float dy = (y - mLastTouchY) / mScaleFactor;
                    mPosX += dx;
                    mPosY += dy;

                    invalidate();
                }

                mLastTouchX = x;
                mLastTouchY = y;

                break;
            }

            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = ev.getX(newPointerIndex);
                    mLastTouchY = ev.getY(newPointerIndex);
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                }
                break;
            }
        }
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.LTGRAY);

        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor, newWidth / 2f, newHeight / 2f);
        canvas.translate(mPosX, mPosY);

        canvas.drawRect(tapeRect, tapePaint); //tape area
        canvas.drawBitmap(mImageBmp, bmpRect, new Rect(tapeRect.left, tapeRect.top, tapeRect.left + 300, tapeRect.top + 250), bmpPaint);
        canvas.drawBitmap(mImageBmp, bmpRect, new Rect(tapeRect.left + 350, tapeRect.top, tapeRect.left + 300 + 350, tapeRect.top + 250), bmpPaint);

        String myString = "1000%";
        canvas.drawText(myString, (getWidth() / 2f - getTextBoundWidthOfString(myString, textPaint) / 2f), (getHeight() / 2f + getTextBoundHeightWithBottom(myString, textPaint) / 2f), textPaint);

        LogI("--------------------------------- x: " + mPosX + " Y: " + mPosY);
        LogI("--------------------------------- mScaleFactor: " + mScaleFactor);
        LogI("--------------------------------- w h: " + getWidth() + " | " + getHeight());
        canvas.drawCircle(0, 0, 88, textPaint); //test original
        canvas.restore();

        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, 123, textPaint); //center
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            LogI("--------------------------------- detector.getScaleFactor(): " + detector.getScaleFactor());
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            invalidate();
            return true;
        }
    }
}