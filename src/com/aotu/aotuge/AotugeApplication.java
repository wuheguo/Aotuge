package com.aotu.aotuge;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import com.aotu.activity.LogonActivity;
import com.aotu.data.AotuInfo;
import com.aotu.data.BaseDataItem;
import com.aotu.data.BaseGrade;
import com.aotu.data.CurriculumVitaeItem;
import com.aotu.data.IntentionItem;
import com.aotu.data.NetReturnInfo;
import com.aotu.data.PersonGrades;
import com.aotu.data.ProvinceCityItem;
import com.aotu.data.TixianInfo;
import com.aotu.net.AsyncHttpClient;
import com.aotu.net.AsyncHttpResponseHandler;
import com.aotu.net.JosnParser;
import com.aotu.net.NetUrlManager;
import com.aotu.utils.FileManager;
import com.custom.calendar.CustomDate;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;

public class AotugeApplication extends Application{
	
	static AotugeApplication mInstance = null;
	static String mfilename = "autu.osw";
	public boolean mIsNullAotuInfo = false;
	public AotuInfo mAotuInfo = new AotuInfo();
	public BaseDataItem mBaseDataItem = new BaseDataItem();
	public BaseGrade mBaseGrade = new BaseGrade();
	public List<ProvinceCityItem> mCityItemList = new ArrayList<ProvinceCityItem>();
	public TixianInfo mTixianInfo = new TixianInfo();
	
	boolean mIsFirst = false;
	int mCodeTimer;
	SharedPreferences preferences;
	Editor editor;
	OutputStream os;
	public CustomDate mCurdate = null;
	public IntentionItem mIntentionItem;
	public PersonGrades mPersonLanguages;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance = this;
		GetFirstRun();
		getBaseData();
		getBaseGradeData();
		FileManager.getInstance().initPath(this);
		initImageLoader(getApplicationContext());
		AotuInfo aotuinfo = readUserInfo();
		if(aotuinfo!=null)
		   mAotuInfo = aotuinfo;
		else
			mIsNullAotuInfo = true;
			
	}
	
	
	protected void GetFirstRun() {
		preferences = getSharedPreferences("phone", Context.MODE_PRIVATE);
		// 判断是不是首次登录，
		if (preferences.getBoolean("firststart", true)) {
			editor = preferences.edit();
			mIsFirst = true;

			// 将登录标志位设置为false，下次登录时不在显示首次登录界面
			editor.putBoolean("firststart", false);
			editor.commit();

			File sdFile = new File(FileManager.getInstance().getRootPath(),
					mfilename);
			if (sdFile.exists())
				sdFile.delete();
		}
	}
	
public void saveUserInfo() {
	    
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File sdFile = new File(FileManager.getInstance().getRootPath(),mfilename);
			try {
				FileOutputStream fos = new FileOutputStream(sdFile);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(mAotuInfo);// 写入
				fos.close(); // 关闭输出流
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private AotuInfo readUserInfo() {
		AotuInfo oAuth_1 = null;
		String filename = FileManager.getInstance().getRootPath()+ mfilename;
	
		boolean isExist = FileManager.isFileExist(filename);
		
		if(isExist)
		{
			File sdFile = new File(filename); 
		try {
			 FileInputStream fis=new FileInputStream(sdFile);   //获得输入流  
		      ObjectInputStream ois = new ObjectInputStream(fis);  
			  oAuth_1 = (AotuInfo) ois.readObject();
		} catch (StreamCorruptedException e) {
			
			e.printStackTrace();
		} catch (OptionalDataException e) {
			
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		}
		return oAuth_1;
	}
	
	public void deleteUserInfo()
	{
		mAotuInfo = null;
		String filename = FileManager.getInstance().getRootPath()+ mfilename;
		
		boolean isExist = FileManager.isFileExist(filename);
		
		if(isExist)
		{
			FileManager.getInstance().deleteFile(filename);
		}
	}
	


	public static AotugeApplication getInstance() {
		// TODO Auto-generated method stub
		return mInstance;
	}


	public boolean getIsFirst() {
		// TODO Auto-generated method stub
		return mIsFirst;
	}


	public void setIsFirst(boolean First) {
		// TODO Auto-generated method stub
		mIsFirst = First;
	}
	
	private void getBaseData()
	{
		String url = NetUrlManager.getBaseDataUrl();
		AsyncHttpClient client = new AsyncHttpClient();

		client.get(url,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getBaseData(content, mBaseDataItem);
				
				
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				
			}
		});
	}
	
	private void getBaseGradeData()
	{
		String url = NetUrlManager.getBaseGradeUrl();
		AsyncHttpClient client = new AsyncHttpClient();

		client.get(url,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String content) {
				// TODO Auto-generated method stub
				super.onSuccess(content);
				NetReturnInfo info = JosnParser.getBaseGrade(content, mBaseGrade);
				
				
			}

			@Override
			public void onFailure(Throwable error, String content) {
				super.onFailure(error, content);
				
			}
		});
	}
	
	public static void initImageLoader(Context context) {
		File cacheDirectory = StorageUtils.getOwnCacheDirectory(context,
				"akuaiting/image");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(480, 800)
				// 设置内存缓存的最大宽和高
				.threadPoolSize(5)
				// 线程池的大小，以目前手机配置来说，一般3-5比较合适吧
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// 线程的优先级
				.denyCacheImageMultipleSizesInMemory()
				// 关闭多规格缓存图片，为啥？浪费空间
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				// 图片名称采用md5加密，为啥？保护隐湿
				.memoryCacheSize(1024 * 1024 * 5)
				// 内存最大缓存值 5M
				.diskCacheSize(1024 * 1024 * 50)
				// 储存卡最大缓存值 50M
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// 设置任务的处理顺序
				.discCache(new UnlimitedDiscCache(cacheDirectory))
				// 使用cacheDirectory自定义存储卡缓存
				.imageDownloader(
						new BaseImageDownloader(context, 1000 * 3, 1000 * 15)) // 连接超时时间，读取小时时间
				.writeDebugLogs() // 写调试信息
				.build(); // 配置完毕
		ImageLoader.getInstance().init(config);// 全局初始化完毕。

	}
	
    public void ResertApp()
    {
    	Intent intent = new Intent(AotugeApplication.getInstance().getBaseContext(),LogonActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		AotugeApplication.getInstance().getBaseContext().startActivity(intent);
    }
}
