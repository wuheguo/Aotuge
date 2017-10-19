package com.aotu.uploadphoto;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CropUtil {

    public static final String CROP_TEMP_FILE_NAME = "TEMPIMG.jpg";
    public static final int CROP_PHOTO_LEN = 640;// 图片剪切最大高度或宽度


    /**
     * 压缩图片并缓存到存储卡，startActivityForResult方式调用剪切程序
     */
    public static void cropPhoto(Activity activity, String imagePath, boolean isParent) {
        Intent intent = new Intent(activity, CropImageActivity.class);
        Bundle extras = new Bundle();
        extras.putString("circleCrop", "true");
        extras.putInt("aspectX", 200);
        extras.putInt("aspectY", 200);


        extras.putString("imagePath", imagePath);
        intent.putExtras(extras);
        if (isParent) {
            activity.getParent().startActivityForResult(intent, PictureConstants.PHOTO_CROP_DATA);
        } else {
            activity.startActivityForResult(intent, PictureConstants.PHOTO_CROP_DATA);
        }
    }

    public static String getRealPathFromURI(Activity activity, Uri contentUri) {
        if ((contentUri.getScheme().equals("file")))
            return contentUri.getPath();
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.managedQuery(contentUri, proj, null, // WHERE clause;
                // which rows to
                // return (all
                // rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /**
     * 压缩图片并返回图片字节数据
     * 
     * @param b ：被压缩的图片
     * @param len ：指定被压缩后的最大宽度或高度
     * @return
     */
    public static byte[] compressPhotoByte(Bitmap b, int len, int maxSize) {
        int w = b.getWidth();
        int h = b.getHeight();
        float s;
        if (w < len && h < len) {
            s = 1;
        }
        if (w > h) {
            s = (float) len / w;
        } else {
            s = (float) len / h;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(s, s);
        // 压缩图片
        Bitmap newB = Bitmap.createBitmap(b, 0, 0, w, h, matrix, false);
        // 将压缩后的图片转换为字节数组，如果字节数组大小超过200K，继续压缩
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int qt = 70;
        newB.compress(CompressFormat.JPEG, qt, bos);
        int size = bos.size();
        while (qt != 0 && size > maxSize) {
            if (qt < 0)
                qt = 0;
            bos.reset();
            newB.compress(CompressFormat.JPEG, qt, bos);
            size = bos.size();
            qt -= 10;
        }
        String log = String.format("压缩后的图片大小：%d", bos.size());
        return bos.toByteArray();
    }

    /**
     * 关闭IO流
     * 
     * @param in
     * @param out
     */
    public static void closeIO(InputStream in, OutputStream out) {
        try {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        } catch (IOException e) {

        }
    }

    public static String saveBitmap(Bitmap bitmap, String fileName) {
        String filePath = ImageLoader.getInstance().getDiskCache().getDirectory().getAbsolutePath();
        File file = new File(filePath, fileName);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(CompressFormat.JPEG, 90, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        return file.getPath();
    }

    /**
     * 缓存图片到本地存储卡
     * 
     * @param nameKey ：文件名称
     * @param len :指定被压缩后的最大宽度或高度
     */
    public static File makeTempFile(Bitmap photo, String nameKey, int len) {
        // 判断是否有存储卡
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED))
            throw new RuntimeException("没有存储卡");
        // 等比例压缩图片，将较长的一边压缩到600px一下，最大容量不超过200K
        byte[] tempData = CropUtil.compressPhotoByte(photo, len, 200 * 1024);
        String filePath = ImageLoader.getInstance().getDiskCache().getDirectory().getAbsolutePath();
        // 将压缩后的图片缓存到存储卡根目录下（权限）
        File bFile = new File(filePath, nameKey);
        if (bFile.exists()) {
            bFile.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(bFile);
            fos.write(tempData);
            fos.flush();
            if (bFile.exists() && bFile.length() > 0)
                return bFile;
        } catch (Exception e) {

        } finally {
            CropUtil.closeIO(null, fos);
        }
        return null;
    }
}
