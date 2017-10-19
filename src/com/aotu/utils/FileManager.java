package com.aotu.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Random;


public class FileManager {
	private static FileManager inst = new FileManager();

	public final static String SYSTEM_DIRECTORY = "/auto/";
	public final static String DOWNLOAD_IMAGE_PATH = "/auto/image/";

	private boolean available = false;
	private boolean isSDCardAvailable = false;

	private String rootPath;
	private String multimediaRootPath;
	private String downloadImagePath;

	private FileManager() {

	}

	public static FileManager getInstance() {
		return inst;
	}

	public boolean isSDCardAvailable() {
		return isSDCardAvailable;
	}

	/**
	 * 初始化目�?
	 * 
	 * @param context
	 */
	public void initPath(Context context) {
		if (!android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
			rootPath = context.getFilesDir().getPath();// 没存储卡时使用内�?
			isSDCardAvailable = false;
		} else {
			rootPath = android.os.Environment.getExternalStorageDirectory()
					.getPath();
			isSDCardAvailable = true;
		}
		initFolders(rootPath);
		available = true;
	}

	private void initFolders(String rootPath) {

		initRootPath();
		initDownloadImagePath();

	}

	private void mkdir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static String getExternalStorageDirector() {
		return android.os.Environment.getExternalStorageDirectory().getPath();
	}

	private void initRootPath() {
		multimediaRootPath = rootPath + SYSTEM_DIRECTORY;
		mkdir(multimediaRootPath);
	}

	public String getRootPath() {
		if (!available) {
			return null;
		}
		return multimediaRootPath;
	}

	private void initDownloadImagePath() {
		downloadImagePath = rootPath + DOWNLOAD_IMAGE_PATH;
		mkdir(downloadImagePath);
	}

	public String getDownloadImagePath() {
		if (!available) {
			return null;
		}
		return downloadImagePath;
	}

	public void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * 获取指定路径文件夹大�?
	 */
	private long getFileSize(String filename) {
		long size = 0;
		File file = new File(filename);
		File[] files = file.listFiles();
		if (files != null) {
			for (File tmpFile : files) {
				size += tmpFile.length();
			}
		}
		return size;
	}

	/**
	 * 获取缓存大小
	 */
	public double getFileSize() {
		double size = (getFileSize(downloadImagePath)) / 1024.0 / 1024.0;
		return size;
	}

	public long clearCache(String filename) {
		long size = 0;
		File file = new File(filename);
		File[] files = file.listFiles();
		if (files != null) {
			for (File tmpFile : files) {
				if (tmpFile.isFile()) {
					tmpFile.delete();
				}
			}
		}
		return size;
	}

	/**
	 * 清空缓存
	 */
	public void clearCache() {
		clearCache(downloadImagePath);
	}

	/**
	 * 从指定的url中提取出文件名（去除域名，其余部�?替换为_, 文件类型替换�?face�?
	 * */
	public static String getFileName(String url) {
		if (url == null || url.length() == 0) {
			return "";
		} else {
			int i = 0;
			while (i < 3) {
				int index = url.indexOf("/");
				if (index != -1) {
					url = url.substring(index + 1);
					i++;
				} else {
					break;
				}
			}
			int index = url.lastIndexOf(".");
			if (index != -1) {
				url = url.substring(0, index) + ".face";
			}
			return url.replaceAll("/", "_");
		}
	}

	/**
	 * 从指定的url中提取出文件�?
	 * */
	public static String getFileNameFromUrl(String url) {
		if (url == null || url.length() == 0) {
			return "";
		} else {
			int i = 0;
			int index = url.lastIndexOf("/");
			if (index >= 0) {
				return url.substring(index + 1);
			}
			return url;
		}
	}

	/**
	 * 判断指定路径的文件是否存�?
	 */
	public static boolean isFileExist(String filePath) {
		try {
			return new File(filePath).exists();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getDate(int type) {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));
		String second = String.valueOf(c.get(Calendar.SECOND));
		StringBuffer sbBuffer = new StringBuffer();
		switch (type) {
		case 1:
			sbBuffer.append(year + "年" + month + "月" + day + "日" + hour + ":"
					+ mins + ":" + second);
			break;
		case 2:
			sbBuffer.append(year + "年" + month + "月" + day + "日");
			break;
		}

		return sbBuffer.toString();
	}

	public static String getRandomFileName() {
		String filename = "";
		int index = (int) (Math.random() * 1000);
		filename = String.valueOf(index);
		return filename;
	}
}
