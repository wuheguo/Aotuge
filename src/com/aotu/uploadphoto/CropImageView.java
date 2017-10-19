package com.aotu.uploadphoto;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class CropImageView extends ImageViewTouchBase{
    private static final String TAG = "CropImageView";
    float mLastX, mLastY;
    int mMotionEdge;

    Context mContext;

    Rect selectRect;
    private final Paint mFocusPaint = new Paint();
    private final Paint mNoFocusPaint = new Paint();
    private final Paint mOutlinePaint = new Paint();

    private GestureDetector gestureScanner;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if ((changed || selectRect == null) && mBitmapDisplayed.getBitmap() != null) {// 这段代码要放到super.onLayout之前，getProperBaseMatrix用到了selectRect
            int width = right - left;// mBitmap.getWidth();
            int height = bottom - top;// mBitmap.getHeight();
            int cropWidth = Math.min(width, height);
            int cropHeight = cropWidth;

            int x = (width - cropWidth) / 2;
            int y = (height - cropHeight) / 2;
            if (selectRect == null) {
                selectRect = new Rect();
            }
            selectRect.set(x, y, x + cropWidth, y + cropHeight);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    public CropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mNoFocusPaint.setARGB(125, 50, 50, 50);
        mFocusPaint.setARGB(125, 50, 50, 50);
        mNoFocusPaint.setARGB(125, 50, 50, 50);
        mOutlinePaint.setStrokeWidth(3F);
        mOutlinePaint.setStyle(Paint.Style.STROKE);
        mOutlinePaint.setAntiAlias(true);

        gestureScanner = new GestureDetector(new MySimpleGesture());
    }

    @Override
    protected void zoomTo(float scale, float centerX, float centerY) {
        super.zoomTo(scale, centerX, centerY);

    }

    @Override
    protected void zoomIn() {
        super.zoomIn();

    }

    @Override
    protected void zoomOut() {
        super.zoomOut();

    }

    @Override
    protected void postTranslate(float deltaX, float deltaY) {
        super.postTranslate(deltaX, deltaY);
        invalidate();
    }

    float baseValue;
    float originalScale;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CropImageActivity cropImage = (CropImageActivity) mContext;
        if (cropImage.mSaving) {
            return false;
        }

        if (gestureScanner.onTouchEvent(event)) {
            return true;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_POINTER_2_DOWN:
                baseValue = 0;
                originalScale = getScale();
                break;
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                mLastY = event.getY();
                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() == 1) {
                    if (getScale() >= 1) {// 小于1时不允许移动
                        float x = event.getX();
                        float y = event.getY();
                        panBy(x - mLastX, y - mLastY);
                        mLastX = x;
                        mLastY = y;
                    }
                } else if (event.getPointerCount() == 2) {
                    float x = event.getX(0) - event.getX(1);
                    float y = event.getY(0) - event.getY(1);
                    float value = (float) Math.sqrt(x * x + y * y);
                    if (baseValue == 0) {
                        baseValue = value;
                    } else {
                        float scale = value / baseValue;
                        // scale the image
                        zoomTo(originalScale * scale, x + event.getX(1), y + event.getY(1));
                    }
                }
                break;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                center(true, true, 200);
                if (getScale() < 1) {
                    float cx = getWidth() / 2F;
                    float cy = getHeight() / 2F;
                    float duration = 100 / getScale();
                    if (duration > 200) {
                        duration = 200;
                    }
                    zoomTo(1, cx, cy, duration);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // if we're not zoomed then there's no point in even allowing
                // the user to move the image around. This call to center puts
                // it back to the normalized location (with false meaning don't
                // animate).
                // if (getScale() == 1F) {
                // center(true, true);
                // }
                break;
        }

        return true;
    }

    Rect drawingRect = new Rect();
    Path selectPath = new Path();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (selectRect != null) {
            getDrawingRect(drawingRect);
            // 画顶部和底部的阴影
            canvas.drawRect(drawingRect.left, drawingRect.top, drawingRect.right, selectRect.top, mFocusPaint);
            canvas.drawRect(drawingRect.left, selectRect.bottom, drawingRect.right, drawingRect.bottom, mFocusPaint);

            // 画选择框
            selectPath.reset();
            selectPath
                    .addRect(selectRect.left, selectRect.top, selectRect.right, selectRect.bottom, Path.Direction.CCW);
            mOutlinePaint.setColor(0xFFFF8A00);
            canvas.drawPath(selectPath, mOutlinePaint);
        }
    }

    private class MySimpleGesture extends SimpleOnGestureListener {
        public boolean onDoubleTap(MotionEvent e) {
            float x = e.getX();
            float y = e.getY();

            if (getScale() > 1) {
                zoomTo(1, x, y, 200f);
                // center(true, true);
            } else if (getScale() == 1) {
                zoomTo(mMaxZoom, x, y, 200f);
            } else {
                zoomTo(1, x, y, 200f);
            }
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }
    }

    @Override
    protected void center(boolean horizontal, boolean vertical, float durationMs) {
        if (mBitmapDisplayed.getBitmap() == null) {
            return;
        }

        Matrix m = getImageViewMatrix();

        RectF rect = new RectF(0, 0, mBitmapDisplayed.getBitmap().getWidth(), mBitmapDisplayed.getBitmap().getHeight());

        m.mapRect(rect);

        float height = rect.height();
        float width = rect.width();

        float deltaX = 0, deltaY = 0;

        if (vertical) {
            int viewHeight = getHeight();
            if (height < viewHeight) {
                deltaY = (viewHeight - height) / 2 - rect.top;
            } else if (rect.top > 0) {
                deltaY = -rect.top;
            } else if (rect.bottom < viewHeight) {
                deltaY = getHeight() - rect.bottom;
            }
            if (selectRect != null) {
                if (rect.bottom - rect.top < selectRect.bottom - selectRect.top) {
                    // 小于选择框时，图片已经居中
                } else if (rect.top > selectRect.top) {
                    deltaY = selectRect.top - rect.top;
                } else if (rect.bottom < selectRect.bottom) {
                    deltaY = selectRect.bottom - rect.bottom;
                } else {
                    deltaY = 0;
                }
            }
        }

        if (horizontal) {
            int viewWidth = getWidth();
            if (width < viewWidth) {
                deltaX = (viewWidth - width) / 2 - rect.left;
            } else if (rect.left > 0) {
                deltaX = -rect.left;
            } else if (rect.right < viewWidth) {
                deltaX = viewWidth - rect.right;
            }
        }

        float l = Math.abs(deltaX) + Math.abs(deltaY);
        if (l > 20 && durationMs > 0) {// durationMs > 0表示需要做动画
            if (l < 100) {
                durationMs = 50;
            } else if (l < 200) {
                durationMs = 100;
            } else {
                durationMs = 200;
            }
        }
        if (durationMs > 0 && getScale() >= 1) {
            final float duration = durationMs;
            final float xIncrementPerMs = (deltaX) / duration;
            final float yIncrementPerMs = (deltaY) / duration;
            final long startTime = System.currentTimeMillis();
            final float distanceX = deltaX;
            final float distanceY = deltaY;
            mHandler.post(new Runnable() {
                float tx = 0;
                float ty = 0;

                public void run() {
                    long now = System.currentTimeMillis();
                    float currentMs = Math.min(duration, now - startTime);
                    float currentX = xIncrementPerMs * currentMs;
                    float currentY = yIncrementPerMs * currentMs;
                    float dx = (Math.abs(currentX) < Math.abs(distanceX) ? currentX : distanceX) - tx;
                    float dy = (Math.abs(currentY) < Math.abs(distanceY) ? currentY : distanceY) - ty;
                  
                    panBy(dx, dy);
                    tx = currentX;
                    ty = currentY;

                    if (currentMs < duration) {
                        mHandler.post(this);
                    }
                }
            });
        } else {
            postTranslate(deltaX, deltaY);
            setImageMatrix(getImageViewMatrix());
        }
    }

    @Override
    protected void getProperBaseMatrix(RotateBitmap bitmap, Matrix matrix) {
        if (selectRect == null) {
            super.getProperBaseMatrix(bitmap, matrix);
            return;
        }
        float viewWidth = getWidth();
        float viewHeight = getHeight();

        float w = bitmap.getWidth();
        float h = bitmap.getHeight();
        matrix.reset();

        // We limit up-scaling to 3x otherwise the result may look bad if it's
        // a small icon.
        float widthScale = Math.min(viewWidth / w, 3.0f);
        float heightScale = Math.min(selectRect.height() / h, 3.0f);
        float scale = Math.max(widthScale, heightScale);

        matrix.postConcat(bitmap.getRotateMatrix());
        matrix.postScale(scale, scale);

        matrix.postTranslate((viewWidth - w * scale) / 2F, (viewHeight - h * scale) / 2F);
    }


}
