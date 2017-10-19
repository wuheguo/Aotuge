package com.aotu.uploadphoto;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;



/**
 * 图片剪裁界面的控制类
 * 
 * @author Xue Wenchao
 * 
 */
public class CropController implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 879368594787044152L;

    protected CropImageActivity cropImageActivity;
    protected Handler handler;

    public CropController() {

    }

    void setCropImageActivity(CropImageActivity cropImageActivity) {
        this.cropImageActivity = cropImageActivity;
    }

    void setHandler(Handler handler) {
        this.handler = handler;
    }



    protected Bitmap loadBitmapFromNet(String imagePath) {
        try {
            URL url = new URL(imagePath);
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            return BitmapFactory.decodeStream(is);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 默认的确定按钮响应操作
     * 
     * @param filePath
     */
    public void onSaveClicked(String filePath) {
        Bundle extras = new Bundle();
        extras.putString("dataPath", filePath);
        cropImageActivity.setResult(Activity.RESULT_OK, (new Intent()).putExtras(extras));
        cropImageActivity.finish();
    }
}
