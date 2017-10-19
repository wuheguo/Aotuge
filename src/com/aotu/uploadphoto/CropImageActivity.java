/*
 * Copyright (C) 2007 The Android Open Source Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

// originally from AOSP Camera code. modified to only do cropping and return
// data to caller. Removed saving to file, MediaManager, unneeded options, etc.
package com.aotu.uploadphoto;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;




import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import com.auto.aotuge.R;


/**
 * The activity can crop specific region of interest from an image.
 */
public class CropImageActivity extends MonitoredActivity implements OnClickListener {

    // private static final String TAG = "CropImage";

    private static final boolean RECYCLE_INPUT = true;

    @SuppressWarnings("unused")
    private int mAspectX, mAspectY;
    private final Handler mHandler = new Handler();


    private int mOutputX, mOutputY;
    private boolean mScale;
    private boolean mScaleUp = true;
    @SuppressWarnings("unused")
    private boolean mCircleCrop = false;

    boolean mSaving;

    private CropImageView mImageView;

    private Bitmap mBitmap;
    private Uri bitmapUri;
    private String imagePath;

    private CropController cropController;

    @SuppressWarnings("unused")
    private ColorFilter colorFilter;
    public int ScreenWidth;
    public int ScreenHeight;

    private TextView txSelect;
    private TextView txCancel;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_cropimage);
        mImageView = (CropImageView) findViewById(R.id.image);
        mImageView.mContext = this;

        txSelect = (TextView) this.findViewById(R.id.cropimage_select_tx);
        txSelect.setOnClickListener(this);
        txCancel = (TextView) this.findViewById(R.id.cropimage_cancel_tx);
        txCancel.setOnClickListener(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        ScreenWidth = dm.widthPixels;
        ScreenHeight = dm.heightPixels;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            imagePath = extras.getString("imagePath");
            mAspectX = extras.getInt("aspectX");
            mAspectY = extras.getInt("aspectY");
            mOutputX = extras.getInt("outputX");
            mOutputY = extras.getInt("outputY");
            mScale = extras.getBoolean("scale", true);
            mScaleUp = extras.getBoolean("scaleUpIfNeeded", true);
            cropController = (CropController) extras.getSerializable("controller");
        }
        if (cropController == null) {
            cropController = new CropController();
        }
        cropController.setCropImageActivity(this);
        cropController.setHandler(mHandler);

        new LoadBitmapTask().execute();

        // Bitmap bitmap = null;
        // bitmap = cropController.loadBitmap(imagePath);
    }

    private class LoadBitmapTask extends AsyncTask<Void, Integer, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap bitmap = null;
            bitmap = loadBitmap(imagePath);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {

            if (result == null) {
                finish();
                return;
            }
            mBitmap = result;
            startFaceDetection();
        }
    }



    public void setSaveFailed() {
        mSaving = false;
    }

    @Override
    public void finish() {
        super.finish();

    }

    public Bitmap loadBitmap(String imagePath) {
        if (imagePath == null) {
            return null;
        }
        return loadBitmapFromLocal(imagePath);
    }

    protected Bitmap loadBitmapFromLocal(String imagePath) {

        try {
            File picture = new File(imagePath);
            Options bitmapFactoryOptions = new Options();
            // 下面这个设置是将图片边界不可调节变为可调节
            bitmapFactoryOptions.inJustDecodeBounds = true;
            Bitmap bmap = BitmapFactory.decodeFile(picture.getAbsolutePath(), bitmapFactoryOptions);


            float imagew = ScreenWidth;
            float imageh = ScreenHeight;
            int yRatio = (int) Math.ceil(bitmapFactoryOptions.outHeight / imageh);
            int xRatio = (int) Math.ceil(bitmapFactoryOptions.outWidth / imagew);
            if (yRatio > 1 || xRatio > 1) {
                if (yRatio > xRatio) {
                    bitmapFactoryOptions.inSampleSize = yRatio;
                } else {
                    bitmapFactoryOptions.inSampleSize = xRatio;
                }
            }
            bitmapFactoryOptions.inJustDecodeBounds = false;
            bmap = BitmapFactory.decodeFile(picture.getAbsolutePath(), bitmapFactoryOptions);
            
            int degree = readPictureDegree(picture.getAbsolutePath());  
            if (bmap != null) {
            	Bitmap bmapRotate = rotaingImageView(degree, bmap); 
                int size = bmap.getRowBytes() * bmap.getHeight();
                return bmapRotate;
            }
        } catch (Throwable ex) {

        }

        return null;

    }
    
    /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
                ExifInterface exifInterface = new ExifInterface(path);
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
        return degree;
    }
    
    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */ 
    public static Bitmap rotaingImageView(int angle , Bitmap bitmap) { 
        //旋转图片 动作  
        Matrix matrix = new Matrix();; 
        matrix.postRotate(angle); 
        // 创建新的图片  
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, 
                bitmap.getWidth(), bitmap.getHeight(), matrix, true); 
        return resizedBitmap; 
    }

    protected int rotation = 0;

    @SuppressWarnings("unused")
    private void onRotateLeft() {
        rotation = (rotation + 270) % 360;
        mImageView.mBitmapDisplayed.setRotation(rotation);
        mImageView.setImageRotateBitmapResetBase(mImageView.mBitmapDisplayed, true);
    }

    @SuppressWarnings("unused")
    private void onRotateRight() {
        rotation = (rotation + 90) % 360;
        mImageView.mBitmapDisplayed.setRotation(rotation);
        mImageView.setImageRotateBitmapResetBase(mImageView.mBitmapDisplayed, true);
    }

    @SuppressWarnings("unused")
    private void onFlipHorizontal() {
        mBitmap = flipHorizontal(mBitmap, RECYCLE_INPUT);
        mImageView.mBitmapDisplayed.setBitmap(mBitmap);
        if (rotation == 90) {
            rotation = 270;
        } else if (rotation == 270) {
            rotation = 90;
        }
        mImageView.mBitmapDisplayed.setRotation(rotation);
        mImageView.setImageRotateBitmapResetBase(mImageView.mBitmapDisplayed, true);
    }

    @SuppressWarnings("unused")
    private void onFlipVertical() {
        mBitmap = flipVertical(mBitmap, RECYCLE_INPUT);
        mImageView.mBitmapDisplayed.setBitmap(mBitmap);
        if (rotation == 90) {
            rotation = 270;
        } else if (rotation == 270) {
            rotation = 90;
        }
        mImageView.mBitmapDisplayed.setRotation(rotation);
        mImageView.setImageRotateBitmapResetBase(mImageView.mBitmapDisplayed, true);
    }

    private static Bitmap flipHorizontal(Bitmap source, boolean recycle) {
        int w = source.getWidth();
        int h = source.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Config.RGB_565);
        float[] values = {-1, 0, w, 0, 1, 0, 0, 0, 1};
        Matrix matrix = new Matrix();
        matrix.setValues(values);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(source, matrix, null);
        if (recycle) {
            source.recycle();
        }
        return bitmap;
    }

    private static Bitmap flipVertical(Bitmap source, boolean recycle) {
        int w = source.getWidth();
        int h = source.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Config.RGB_565);
        float[] values = {1, 0, 0, 0, -1, h, 0, 0, 1};
        Matrix matrix = new Matrix();
        matrix.setValues(values);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(source, matrix, null);
        if (recycle) {
            source.recycle();
        }
        return bitmap;
    }


    private void startFaceDetection() {
        if (isFinishing()) {
            return;
        }

        mImageView.setImageBitmapResetBase(mBitmap, true);

        startBackgroundJob(this, null, "", new Runnable() {
            public void run() {
                final CountDownLatch latch = new CountDownLatch(1);
                final Bitmap b = mBitmap;
                mHandler.post(new Runnable() {
                    public void run() {
                        if (b != mBitmap && b != null) {
                            mImageView.setImageBitmapResetBase(b, true);
                            mBitmap.recycle();
                            mBitmap = b;
                        }
                        if (mImageView.getScale() == 1F) {
                            mImageView.center(true, true);
                        }
                        latch.countDown();
                    }
                });
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, mHandler);
    }

    private static class BackgroundJob extends MonitoredActivity.LifeCycleAdapter implements Runnable {

        private final MonitoredActivity mActivity;
        private final ProgressDialog mDialog;
        private final Runnable mJob;
        private final Handler mHandler;
        private final Runnable mCleanupRunner = new Runnable() {
            public void run() {
                mActivity.removeLifeCycleListener(BackgroundJob.this);
                if (mDialog.getWindow() != null)
                    mDialog.dismiss();
            }
        };

        public BackgroundJob(MonitoredActivity activity, Runnable job, ProgressDialog dialog, Handler handler) {
            mActivity = activity;
            mDialog = dialog;
            mJob = job;
            mActivity.addLifeCycleListener(this);
            mHandler = handler;
        }

        public void run() {
            try {
                mJob.run();
            } finally {
                mHandler.post(mCleanupRunner);
            }
        }

        @Override
        public void onActivityDestroyed(MonitoredActivity activity) {
            // We get here only when the onDestroyed being called before
            // the mCleanupRunner. So, run it now and remove it from the queue
            mCleanupRunner.run();
            mHandler.removeCallbacks(mCleanupRunner);
        }

        @Override
        public void onActivityStopped(MonitoredActivity activity) {
            mDialog.hide();
        }

        @Override
        public void onActivityStarted(MonitoredActivity activity) {
            mDialog.show();
        }
    }

    private static void startBackgroundJob(MonitoredActivity activity, String title, String message, Runnable job,
            Handler handler) {
        // Make the progress dialog uncancelable, so that we can gurantee
        // the thread will be done before the activity getting destroyed.
        ProgressDialog dialog = ProgressDialog.show(activity, title, message, true, false);
        new Thread(new BackgroundJob(activity, job, dialog, handler)).start();
    }

    private void onSaveClicked() {

        if (mImageView.selectRect == null) {
            return;
        }

        if (mSaving)
            return;
        mSaving = true;

        Bitmap croppedImage;

        // If the output is required to a specific size, create an new image
        // with the cropped image in the center and the extra space filled.
        if (mOutputX != 0 && mOutputY != 0 && !mScale) {
            // Don't scale the image but instead fill it so it's the
            // required dimension
            croppedImage = Bitmap.createBitmap(mOutputX, mOutputY, Config.RGB_565);
            Canvas canvas = new Canvas(croppedImage);

            Rect srcRect = new Rect(mImageView.selectRect);
            Rect dstRect = new Rect(0, 0, mOutputX, mOutputY);

            int dx = (srcRect.width() - dstRect.width()) / 2;
            int dy = (srcRect.height() - dstRect.height()) / 2;

            // If the srcRect is too big, use the center part of it.
            srcRect.inset(Math.max(0, dx), Math.max(0, dy));

            // If the dstRect is too big, use the center part of it.
            dstRect.inset(Math.max(0, -dx), Math.max(0, -dy));

            // Draw the cropped bitmap in the center
            canvas.drawBitmap(mBitmap, srcRect, dstRect, null);

            // Release bitmap memory as soon as possible
            // mImageView.clear();
            // mBitmap.recycle();
        } else {
            Matrix m = mImageView.getImageViewMatrix();
            RectF rect =
                    new RectF(0, 0, mImageView.mBitmapDisplayed.getBitmap().getWidth(), mImageView.mBitmapDisplayed
                            .getBitmap().getHeight());
            m.mapRect(rect);

            float factor = rect.height() / mBitmap.getHeight();

            Rect r = new Rect();
            r.left = (int) ((mImageView.selectRect.left - rect.left) / factor);
            r.top = (int) ((mImageView.selectRect.top - rect.top) / factor);
            r.right = (int) ((mImageView.selectRect.right - rect.left) / factor);
            r.bottom = (int) ((mImageView.selectRect.bottom - rect.top) / factor);

            int width = r.width();
            int height = r.height();

            // If we are circle cropping, we want alpha channel, which is the
            // third param here.
            croppedImage = Bitmap.createBitmap(width, height, Config.RGB_565);

            Canvas canvas = new Canvas(croppedImage);
            Rect dstRect = new Rect(0, 0, width, height);
            canvas.drawBitmap(mBitmap, r, dstRect, null);



            if (mOutputX != 0 && mOutputY != 0 && mScale) {
                croppedImage = transform(new Matrix(), croppedImage, mOutputX, mOutputY, mScaleUp, RECYCLE_INPUT);
            }
        }

        if (rotation > 0) {
            croppedImage = rotate(croppedImage, rotation, RECYCLE_INPUT);
        }


        File file = CropUtil.makeTempFile(croppedImage, CropUtil.CROP_TEMP_FILE_NAME, CropUtil.CROP_PHOTO_LEN);
        croppedImage.recycle();
        if (file != null)
            cropController.onSaveClicked(file.getPath());
        else
            mSaving = true;

    }

    private static Bitmap transform(Matrix scaler, Bitmap source, int targetWidth, int targetHeight, boolean scaleUp,
            boolean recycle) {
        int deltaX = source.getWidth() - targetWidth;
        int deltaY = source.getHeight() - targetHeight;
        if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
            /*
             * In this case the bitmap is smaller, at least in one dimension, than the target.
             * Transform it by placing as much of the image as possible into the target and leaving
             * the top/bottom or left/right (or both) black.
             */
            Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight, Config.ARGB_8888);
            Canvas c = new Canvas(b2);

            int deltaXHalf = Math.max(0, deltaX / 2);
            int deltaYHalf = Math.max(0, deltaY / 2);
            Rect src =
                    new Rect(deltaXHalf, deltaYHalf, deltaXHalf + Math.min(targetWidth, source.getWidth()), deltaYHalf
                            + Math.min(targetHeight, source.getHeight()));
            int dstX = (targetWidth - src.width()) / 2;
            int dstY = (targetHeight - src.height()) / 2;
            Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight - dstY);
            c.drawBitmap(source, src, dst, null);
            if (recycle) {
                source.recycle();
            }
            return b2;
        }
        float bitmapWidthF = source.getWidth();
        float bitmapHeightF = source.getHeight();

        float bitmapAspect = bitmapWidthF / bitmapHeightF;
        float viewAspect = (float) targetWidth / targetHeight;

        if (bitmapAspect > viewAspect) {
            float scale = targetHeight / bitmapHeightF;
            if (scale < .9F || scale > 1F) {
                scaler.setScale(scale, scale);
            } else {
                scaler = null;
            }
        } else {
            float scale = targetWidth / bitmapWidthF;
            if (scale < .9F || scale > 1F) {
                scaler.setScale(scale, scale);
            } else {
                scaler = null;
            }
        }

        Bitmap b1;
        if (scaler != null) {
            // this is used for minithumb and crop, so we want to filter here.
            b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), scaler, true);
        } else {
            b1 = source;
        }

        if (recycle && b1 != source) {
            source.recycle();
        }

        int dx1 = Math.max(0, b1.getWidth() - targetWidth);
        int dy1 = Math.max(0, b1.getHeight() - targetHeight);

        Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth, targetHeight);

        if (b2 != b1) {
            if (recycle || b1 != source) {
                b1.recycle();
            }
        }

        return b2;
    }

    private static Bitmap rotate(Bitmap source, int rotation, boolean recycle) {
        int w = source.getWidth();
        int h = source.getHeight();
        Bitmap croppedImage;
        if ((rotation / 90) % 2 == 1) {
            croppedImage = Bitmap.createBitmap(h, w, Config.RGB_565);
        } else {
            croppedImage = Bitmap.createBitmap(w, h, Config.RGB_565);
        }
        Canvas canvas = new Canvas(croppedImage);
        Matrix matrix = new Matrix();
        matrix.postRotate(rotation, w / 2, h / 2);
        canvas.drawBitmap(source, matrix, new Paint());

        if (recycle) {
            source.recycle();
        }

        return croppedImage;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.cropimage_cancel_tx: {
                setResult(RESULT_CANCELED);
                finish();
            }
                break;
            case R.id.cropimage_select_tx:
                onSaveClicked();
                break;
        }
    }

}

