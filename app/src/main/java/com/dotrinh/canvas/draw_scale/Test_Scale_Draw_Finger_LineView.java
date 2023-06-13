package com.dotrinh.canvas.draw_scale;

import static com.dotrinh.canvas.tool.LogUtil.LogI;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.dotrinh.canvas.R;
import com.dotrinh.canvas.tool.Tool;

import java.util.ArrayList;

public class Test_Scale_Draw_Finger_LineView extends View {

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
    Paint paint;

    //bitmap
    Paint bmpPaint;
    Rect bmpRect;
    private ArrayList<Path> listPath = new ArrayList<>();
    private Path path;
    private Drawable mIcon;
    private Rect clipBounds_canvas;

    public Test_Scale_Draw_Finger_LineView(Context context) {
        this(context, null, 0);
        initialize(context);

    }

    public Test_Scale_Draw_Finger_LineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initialize(context);

    }

    public Test_Scale_Draw_Finger_LineView(Context context, AttributeSet attrs, int defStyle) {
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

        //test
        paint = new TextPaint();
        paint.setTypeface(Typeface.SERIF);
        paint.setStrokeWidth(7);
        paint.setTextSize(Tool.convertSpToPx(getContext(), 20));
        paint.setAntiAlias(true);
        paint.setPathEffect(null);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);

        //white area
        tapePaint = new Paint();
        tapePaint.setColor(Color.WHITE);
        tapePaint.setStrokeWidth(7);
        tapePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        tapePaint.setAntiAlias(true);

        //bmp
        bmpPaint = new Paint();
        bmpPaint.setAntiAlias(true);

        mIcon = ContextCompat.getDrawable(context, R.drawable.phunuvn);
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
                int x = (int) (ev.getX() / mScaleFactor + clipBounds_canvas.left);
                int y = (int) (ev.getY() / mScaleFactor + clipBounds_canvas.top);
                mLastTouchX = x;
                mLastTouchY = y;
                mActivePointerId = ev.getPointerId(0);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                // final float x = ev.getX(pointerIndex);
                // final float y = ev.getY(pointerIndex);
                int x = (int) (ev.getX(pointerIndex) / mScaleFactor + clipBounds_canvas.left);
                int y = (int) (ev.getY(pointerIndex) / mScaleFactor + clipBounds_canvas.top);
                // Only move if the ScaleGestureDetector isn't processing a gesture.
                if (!mScaleDetector.isInProgress()) {
                    LogI("111111 --------------------------------- mScaleFactor: " + mScaleFactor);
                    final float dx = (x - mLastTouchX);
                    final float dy = (y - mLastTouchY);
                    mPosX += dx;
                    mPosY += dy;
                    invalidate();
                }

                mLastTouchX = x;
                mLastTouchY = y;

                break;
            }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastTouchX = (int) (ev.getX(pointerIndex) / mScaleFactor + clipBounds_canvas.left);
                    mLastTouchY = (int) (ev.getY(pointerIndex) / mScaleFactor + clipBounds_canvas.top);
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                }
                break;
            }
        }

        int x = (int) (ev.getX() / mScaleFactor + clipBounds_canvas.left);
        int y = (int) (ev.getY() / mScaleFactor + clipBounds_canvas.top);
        float objectNewX, objectNewY;
        if (mScaleFactor >= 1) {
            objectNewX = x + (x - super.getWidth() * 0.5f) * (mScaleFactor - 1);
            objectNewY = ev.getY() + (ev.getY() - super.getHeight() * 0.5f) * (mScaleFactor - 1);
        } else {
            objectNewX = x - (x - super.getWidth() * 0.5f) * (1 - mScaleFactor);
            objectNewY = y - (y - super.getHeight() * 0.5f) * (1 - mScaleFactor);
        }

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            path = new Path();
            path.moveTo(objectNewX, objectNewY);
            path.lineTo(objectNewX, objectNewY);
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            path.lineTo(objectNewX, objectNewY);
            listPath.add(path);
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            path.lineTo(objectNewX, objectNewY);
            listPath.add(path);
        }
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        clipBounds_canvas = canvas.getClipBounds();
        canvas.save();
        // canvas.translate(mPosX, mPosY);
        canvas.scale(mScaleFactor, mScaleFactor, super.getWidth() * 0.5f, super.getHeight() * 0.5f);
        mIcon.draw(canvas);
        for (Path path : listPath) {
            canvas.drawPath(path, paint);
        }
        canvas.restore();
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