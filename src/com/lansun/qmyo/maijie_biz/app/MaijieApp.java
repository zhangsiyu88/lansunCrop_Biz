package com.lansun.qmyo.maijie_biz.app;

import java.io.File;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.lansun.qmyo.maijie_biz.R;
import com.lansun.qmyo.maijie_biz.db.DataBaseManager;
import com.lansun.qmyo.maijie_biz.dirmanager.DirType;
import com.lansun.qmyo.maijie_biz.log.FrameLog;
import com.lansun.qmyo.maijie_biz.log.Logger;
import com.lansun.qmyo.maijie_biz.utils.AppContext;
import com.lansun.qmyo.maijie_biz.utils.PrefsConfig;
import com.lansun.qmyo.maijie_biz.utils.SysUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * 应用的基类
 * @author  Yeun.Zhang
 *
 */
public class MaijieApp extends Application {
	private static String TAG = "App";
	// 初始化文件系统是否失
	public boolean mInitFSFailed = false;
	private static Context mContext = null;
	public boolean isDebuggable = true;
	private static MaijieApp mApp = null;
	public boolean mIsInited = false;
	private Handler mainThreadHandler = null;
	
    private static Context sContext;
    public static Context getsContext() {
		return sContext;
	}


	public static Handler getsHandler() {
		return sHandler;
	}


	public static Thread getsMainThread() {
		return sMainThread;
	}


	public static int getsMainThreadId() {
		return sMainThreadId;
	}
	private static Handler sHandler;
    private static Thread sMainThread;
    private static int sMainThreadId;


	public static Context getAppContext() {
		return mContext;
	}
	// public static App getMyApp(){
	// return mApp;
	// }


	public Handler getMainThreadHandler() {
		return mainThreadHandler;
	}
	
	public static MaijieApp getInstance() {
		return mApp;
	}
	// 初始化文件系统是否失


	@Override
	public void onCreate() {
		mApp = this;
		mContext = this.getApplicationContext();
		
		  MaijieApp.sContext = getApplicationContext();
		  MaijieApp.sHandler = new Handler();
		  MaijieApp.sMainThread = Thread.currentThread();
		  MaijieApp.sMainThreadId = android.os.Process.myTid();
		  
		isDebuggable = SysUtils.getDebuggable(mContext);

		// 设置日志权限
		FrameLog.setDebug(isDebuggable);
		FrameLog.trace(isDebuggable);
		FrameLog.getLogger().setLevel(Logger.VERBOSE);
		
		// 初始化数据库
		DataBaseManager.init(this);
		
		// 加载配置信息
		PrefsConfig.loadFromPref(this);
		if(mainThreadHandler == null)
			mainThreadHandler = new Handler();

//		if (isDebuggable) {
			// 崩溃记录
			Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
//		}

		initImageLoader(mContext);
		super.onCreate();
	}


	public synchronized void initApp(Activity activity) {

		if (mIsInited || mInitFSFailed) {
			return;
		}
		try {
			PrefsConfig.loadFromPref(this);// 加载配置
		} catch (Exception e) {
			e.printStackTrace();
		}

		mInitFSFailed = !AppContext.init(activity);//获取手机与应用的信息
		if (mInitFSFailed) {
			return;
		}

		// 创建快捷方式.
		if (!PrefsConfig.has_shortcut) {
			SysUtils.createShortcut(this);
			PrefsConfig.saveToPref(this);
		}
		mIsInited = true;
	}

	public void initImageLoader(Context context) {
		
		/*DisplayImageOptions*/
		DisplayImageOptions options = new DisplayImageOptions
				.Builder()	
				.showImageOnLoading(R.drawable.car_default)
				.showImageForEmptyUri(R.drawable.car_default) //
				.showImageOnFail(R.drawable.car_default) // resource
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.EXACTLY)
				//.displayer(new FadeInBitmapDisplayer(150, true, false, false))
				.build();

		/*File cacheDir = AppContext.getDirManager().getDir(DirType.CACHE);*/  //篡改
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,"qmyo/Cachee");
			if (!cacheDir.exists()) {
				cacheDir.mkdir();
			}
		

		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
			
		/*ImageLoaderConfiguration.Builder*/
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration
				.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(4 * 1024 * 1024))
				.denyCacheImageMultipleSizesInMemory()
				.memoryCacheExtraOptions(200, 200)
				.threadPoolSize(3)
				.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 15 * 1000))
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024) // 50 MiB
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCache(new UnlimitedDiskCache(cacheDir))
				.defaultDisplayImageOptions(options);
				// config.writeDebugLogs(); // Remove for release app
				// Initialize ImageLoader with configuration.
		
		ImageLoader.getInstance().init(config.build());
	}
    /**
     * 退出程序杀掉进程
     */
	public void exitApp() {
		android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
	}

}
