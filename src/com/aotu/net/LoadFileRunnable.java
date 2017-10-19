package com.aotu.net;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.aotu.loadfile.DownloadFileItem;
import com.aotu.loadfile.DownloadTask;
import com.aotu.loadfile.DownloadTaskListener;

import android.content.Context;

public class LoadFileRunnable implements Runnable {

	private volatile boolean isLoading;
	private final Object mutex = new Object();
	private List<DownloadFileItem> mRecentchats = new ArrayList<DownloadFileItem>();
	String mPlayPath;

	public LoadMp3interface minterface;

	public boolean isRun = false;
	private Context context;
	private DownloadFileItem mLoadItemData;

	public interface LoadMp3interface {
		void onLoadMp3complete(DownloadFileItem itemdata);

		void onErrorDownload(DownloadFileItem itemdata, int error);
	}

	public LoadFileRunnable(List<DownloadFileItem> aRecentchats, Context acontext) {
		mRecentchats = aRecentchats;
		context = acontext;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (this.isLoading) {
			if (mRecentchats.size() > 0) {
				if (!isRun) {
					isRun = true;
					startLoadMp3();
				}

			} else {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void setRecording(boolean aisRecording) {
		synchronized (mutex) {
			isLoading = aisRecording;
			if (this.isLoading) {
				mutex.notify();
			}
		}
	}

	public boolean isRecording() {
		synchronized (mutex) {
			return isLoading;
		}
	}

	private DownloadTask tasks;
	private DownloadTaskListener downloadTaskListener = new DownloadTaskListener() {

		@Override
		public void updateProcess(DownloadTask mgr) {

		}

		@Override
		public void finishDownload(DownloadTask mgr) {

			if (minterface != null)
				minterface.onLoadMp3complete(mLoadItemData);
			isRun = false;
		}

		@Override
		public void preDownload() {
			// TODO Auto-generated method stub

		}

		@Override
		public void errorDownload(int error) {
			// TODO Auto-generated method stub

			if (minterface != null)
				minterface.onErrorDownload(mLoadItemData, error);
			isRun = false;
		}
	};

	private void startLoadMp3() {

		mLoadItemData = mRecentchats.remove(0);

		String url = mLoadItemData.mFileUrl;
		String filePath = mLoadItemData.mSavePath;
		mPlayPath = filePath;

		try {
			tasks = new DownloadTask(context, url, mPlayPath, downloadTaskListener);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tasks.execute();
	}

}
