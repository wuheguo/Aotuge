package com.aotu.uploadphoto;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;



import java.io.File;

public class UploadPhotoAgentActivity extends Activity {

	public static final String EXTRA_ACTION = "action";
	public static final String EXTRA_IMAGEPATH = "imagePath";

	public static final int ACTION_UPLOAD_PHOTO_FROM_CAMERA = 2;// 拍照上传
	public static final int ACTION_MODIFY_HEAD_FROM_PHONE = 3;// 从手机相册修改头像
	public static final int ACTION_MODIFY_HEAD_FROM_MAMERA = 4;// 拍照修改头像
	public static final int ACTION_MODIFY_HEAD_FROM_SINGLE_PATH = 6;// 根据url修改头像，可以是本地路径或网络路径

	/** 从媒体库选择图片 */
	public static final int PHOTO_PICKED_WITH_DATA = 2000;
	/** 剪切图片 */
	public static final int PHOTO_CROP_DATA = 2001;
	/** 拍照图片 */
	public static final int CAMERA_WITH_DATA = 2002;

	private int action;
	private boolean isCropPhoto = false;// 图片是否需要裁剪

	private File mCurrentPhotoFile;// 照相机拍照得到的图片
	public static Uri originalUri;// 文件uri
	public final static String DOWNLOAD_IMAGE_PATH = "/yiqixue/pic_cache/";
	private String rootPath;
	private String downloadImagePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if (intent == null) {
			finish();
			return;
		}
		initPath(this);
		action = intent.getIntExtra(EXTRA_ACTION, -1);
		switch (action) {

		case ACTION_MODIFY_HEAD_FROM_PHONE:
			pickPhotoFromGallery();
			isCropPhoto = true;
			break;
		case ACTION_MODIFY_HEAD_FROM_MAMERA:
			photograph();
			isCropPhoto = true;
			break;
		default:
			finish();
			break;
		}
	}

	public void initPath(Context context) {
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			rootPath = context.getFilesDir().getPath();// 没存储卡时使用内存
		} else {
			rootPath = Environment.getExternalStorageDirectory()
					.getPath();
		}
		initDownloadImagePath();
	}

	private void initDownloadImagePath() {
		downloadImagePath = rootPath + DOWNLOAD_IMAGE_PATH;
		File file = new File(downloadImagePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	private String getDownloadImagePath() {
		// TODO Auto-generated method stub
		return downloadImagePath;
	}

	private void photograph() {
		String fileName = "IMG_"
				+ DateFormat.format("yyyyMMdd_kkmmss",
						System.currentTimeMillis()) + ".jpg";
		String filePath;

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			filePath = Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_PICTURES).getPath();
		} else {
			filePath = getDownloadImagePath();
		}
		File path = new File(filePath);
		if (path != null && !path.exists()) {
			path.mkdirs();
		}

		mCurrentPhotoFile = new File(filePath, fileName);
		// 获取这个图片的URI
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		originalUri = Uri.fromFile(mCurrentPhotoFile);// 这是个实例变量，方便下面获取图片的时候用
		intent.putExtra(MediaStore.EXTRA_OUTPUT, originalUri);
		// 获取这个图片的URI
		startActivityForResult(intent, CAMERA_WITH_DATA);
	}

	/** startActivityForResult方式选择图片，onActivityResult接收返回的图片文件 */
	private void pickPhotoFromGallery() {
		Intent intent = new Intent("android.intent.action.PICK");
		intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != RESULT_OK) {
			finish();
			return;
		}
		String imagePath = null;// 本地图片路径
		Uri uri = null;
		switch (requestCode) {
		case PictureConstants.PHOTO_PICKED_WITH_DATA: // 调用媒体库
			uri = data.getData();
			imagePath = CropUtil.getRealPathFromURI(this, uri);
			break;
		case PictureConstants.CAMERA_WITH_DATA: // 拍照功能
			if (data == null || data.getData() == null) {
				uri = originalUri;
			} else {
				uri = data.getData();
			}
			imagePath = CropUtil.getRealPathFromURI(this, uri);
			break;
		default:
			setResult(resultCode, data);
			finish();
			break;
		}
		if (imagePath != null && isCropPhoto) {
			CropUtil.cropPhoto(this, imagePath, false);
			isCropPhoto = false;
		}
	}

	public static class Entrance {
		// 从手机相册里选择照片设置头像
		public static void modifyHeadFromPhone(Activity activity) {
			Intent intent = new Intent(activity, UploadPhotoAgentActivity.class);
			intent.putExtra(EXTRA_ACTION, ACTION_MODIFY_HEAD_FROM_PHONE);
			if (activity.getParent() != null) {
				activity = activity.getParent();
			}
			activity.startActivityForResult(intent,
					PictureConstants.MODIFY_HEAD_IMAGE);
		}

		public static void modifyHeadFromCamera(Activity activity) {
			/*if (!DeviceInfo.openCamera(true)) {
				new GAlertDialog.Builder(activity)
						.setTitle("摄像头权限未开启")
						.setMessage("请在手机设置中开启摄像头权限")
						.setNegativeButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										return;
									}
								}).show();
				return;
			}*/

			Intent intent = new Intent(activity, UploadPhotoAgentActivity.class);
			intent.putExtra(EXTRA_ACTION, ACTION_MODIFY_HEAD_FROM_MAMERA);
			if (activity.getParent() != null) {
				activity = activity.getParent();
			}
			activity.startActivityForResult(intent,
					PictureConstants.MODIFY_HEAD_IMAGE);
		}
	}
}
