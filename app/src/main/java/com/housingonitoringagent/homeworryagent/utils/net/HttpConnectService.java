package com.housingonitoringagent.homeworryagent.utils.net;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.utils.FileUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

public class HttpConnectService {
	private static final int NOTIFY_ID = 0;
	private NotificationManager mNotificationManager;
	private Notification mNotification;

	//  下载包安装路径
	private static final String savePath = FileUtil.SDPATH+"/HomeWorryAgent.apk";
	
	public void connectDownLoad(final Context context,final String packageName,String apkUrl) {
		mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		HttpUtils http = new HttpUtils();
		http.configTimeout(30000);
		http.configSoTimeout(30000);
		http.download(apkUrl,savePath, false, false,
				new RequestCallBack<File>() {

					@Override
					public void onStart() {	
                       setUpNotification(packageName);
					}

					@Override
					public void onLoading(long total,long current,boolean isUploading) {   
					    int progress = (int) (current * 100/total);
						RemoteViews contentView = mNotification.contentView;
						contentView.setTextViewText(R.id.tv_progress, progress + "%");
						contentView.setProgressBar(R.id.progressbar, 100, progress, false);
						mNotificationManager.notify(NOTIFY_ID, mNotification);
					}

					@Override
					public void onSuccess(ResponseInfo<File> responseInfo) {
						mNotificationManager.cancel(NOTIFY_ID);
						installAPK(responseInfo.result, context);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						RemoteViews contentview = mNotification.contentView;
						contentview.setTextViewText(R.id.tv_progress,"下载失败");
						contentview.setProgressBar(R.id.progressbar, 100,0, false);
						mNotificationManager.notify(NOTIFY_ID, mNotification);
					}
				});
	}


	/**
	 * 创建通知
	 */
	@SuppressWarnings("deprecation")
	private void setUpNotification(String packageName) {
		mNotification = new Notification(R.mipmap.ic_launcher, "正在下载",  System.currentTimeMillis());
		mNotification.flags |= Notification.FLAG_ONGOING_EVENT;// 出现在 “正在运行的”栏目下面
//		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		RemoteViews contentView = new RemoteViews(packageName, R.layout.download_notification_layout);
		mNotification.contentView = contentView;
		mNotificationManager.notify(NOTIFY_ID, mNotification);
	}
	
	//安装APK
	private void installAPK(File t,Context context) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(t),"application/vnd.android.package-archive");
		context.startActivity(intent);
	}
}
