package com.dotrinh.canvas.draw_scale;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.dotrinh.canvas.R;

import java.util.LinkedList;
import java.util.List;

public class ViewPort extends View {
    List<Layer> layers = new LinkedList<>();
    int[] ids = {R.drawable.phunuvn, R.drawable.phunuvn, R.drawable.levelmeter_bg};

    public ViewPort(Context context) {
        this(context, null, 0);
        initialize(context);

    }

    public ViewPort(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initialize(context);
    }

    public ViewPort(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize(context);
    }

    private void initialize(Context context) {
        Resources res = getResources();
        for (int i = 0; i < 1; i++) {
            Layer l = new Layer(context, this, BitmapFactory.decodeResource(res, ids[i]));
            layers.add(l);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Layer l : layers) {
            l.draw(canvas);
        }
    }

    private Layer target;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            target = null;
            for (int i = layers.size() - 1; i >= 0; i--) {
                Layer l = layers.get(i);
                if (l.contains(event)) {
                    target = l;
                    layers.remove(l);
                    layers.add(l);
                    invalidate();
                    break;
                }
            }
        }
        if (target == null) {
            return false;
        }
        return target.onTouchEvent(event);
    }
}

class Layer implements MatrixGestureDetector.OnMatrixChangeListener {
    Matrix matrix = new Matrix();
    Matrix inverse = new Matrix();
    RectF bounds;
    View parent;
    Bitmap bitmap;
    MatrixGestureDetector mgd = new MatrixGestureDetector(matrix, this);

    public Layer(Context ctx, View p, Bitmap b) {
        parent = p;
        bitmap = b;
        bounds = new RectF(0, 0, b.getWidth(), b.getHeight());
        matrix.postTranslate(50 + (float) Math.random() * 50, 50 + (float) Math.random() * 50);
    }

    public boolean contains(MotionEvent event) {
        matrix.invert(inverse);
        float[] pts = {event.getX(), event.getY()};
        inverse.mapPoints(pts);
        if (!bounds.contains(pts[0], pts[1])) {
            return false;
        }
        return Color.alpha(bitmap.getPixel((int) pts[0], (int) pts[1])) != 0;
    }

    public boolean onTouchEvent(MotionEvent event) {
        mgd.onTouchEvent(event);
        return true;
    }

    @Override
    public void onChange(Matrix matrix) {
        parent.invalidate();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, matrix, null);
    }
}

class MatrixGestureDetector {
    private static final String TAG = "MatrixGestureDetector";

    private int ptpIdx = 0;
    private Matrix mTempMatrix = new Matrix();
    private Matrix mMatrix;
    private OnMatrixChangeListener mListener;
    private float[] mSrc = new float[4];
    private float[] mDst = new float[4];
    private int mCount;

    interface OnMatrixChangeListener {
        void onChange(Matrix matrix);
    }

    public MatrixGestureDetector(Matrix matrix, MatrixGestureDetector.OnMatrixChangeListener listener) {
        this.mMatrix = matrix;
        this.mListener = listener;
    }

    public void onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 2) {
            return;
        }

        int action = event.getActionMasked();
        int index = event.getActionIndex();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                int idx = index * 2;
                mSrc[idx] = event.getX(index);
                mSrc[idx + 1] = event.getY(index);
                mCount++;
                ptpIdx = 0;
                break;

            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < mCount; i++) {
                    idx = ptpIdx + i * 2;
                    mDst[idx] = event.getX(i);
                    mDst[idx + 1] = event.getY(i);
                }
                mTempMatrix.setPolyToPoly(mSrc, ptpIdx, mDst, ptpIdx, mCount);
                mMatrix.postConcat(mTempMatrix);
                if (mListener != null) {
                    mListener.onChange(mMatrix);
                }
                System.arraycopy(mDst, 0, mSrc, 0, mDst.length);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerId(index) == 0) {
                    ptpIdx = 2;
                }
                mCount--;
                break;
        }
    }
}