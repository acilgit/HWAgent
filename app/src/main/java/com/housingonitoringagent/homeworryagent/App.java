package com.housingonitoringagent.homeworryagent;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.support.multidex.MultiDex;
//import android.support.multidex.MultiDex;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.housingonitoringagent.homeworryagent.activity.LoginActivity;
import com.housingonitoringagent.homeworryagent.activity.MainActivity;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.FileUtil;
import com.housingonitoringagent.homeworryagent.utils.LogUtils;
import com.housingonitoringagent.homeworryagent.utils.ThreadPool;
import com.housingonitoringagent.homeworryagent.utils.easeui.EaseHelper;
import com.housingonitoringagent.homeworryagent.utils.net.FrescoFactory;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyManager;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
//import com.hyphenate.util.NetUtils;
//import com.hyphenate.chat.EMClient;
//import com.hyphenate.chat.EMOptions;
//import com.hyphenate.easeui.controller.EaseUI;
//import com.hyphenate.util.PathUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by XY on 2016/5/28.
 */
public class App extends Application {
    // 上下文菜单
    private static App instance;
    private List<Activity> activities = new LinkedList<>();

    // 记录是否已经初始化
    private boolean isInit = false;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 初始化环信SDK
        initEaseMob();
        Log.e("ZJ", " create App " + 51);
    }

  /*  @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/

    /**
     *
     */
    private void initEaseMob() {
        // 获取当前进程 id 并取得进程名
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        /**
         * 如果app启用了远程的service，此application:onCreate会被调用2次
         * 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
         * 默认的app会在以包名为默认的process name下运行，如果查到的process name不是app的process name就立即返回
         */
        if (processAppName == null || !processAppName.equalsIgnoreCase(instance.getPackageName())) {
            // 则此application的onCreate 是被service 调用的，直接返回
            return;
        }
        if (isInit) {
            return;
        }

        /**
         * SDK初始化的一些配置
         * 关于 EMOptions 可以参考官方的 API 文档
         * http://www.easemob.com/apidoc/ ... .html
         */
//        Fresco.initialize(this);
        Fresco.initialize(this, FrescoFactory.getImagePipelineConfig(this));


        EaseHelper.getInstance().init(this);
      /*  EMOptions options = new EMOptions();
        // 设置Appkey，如果配置文件已经配置，这里可以不用设置
        // options.setAppKey("lzan13#hxsdkdemo");
        // 设置自动登录
        options.setAutoLogin(true);
        // 设置是否需要发送已读回执
        options.setRequireAck(true);
        // 设置是否需要发送回执，TODO 这个暂时有bug，上层收不到发送回执
        options.setRequireDeliveryAck(true);
        // 设置是否需要服务器收到消息确认
        options.setRequireServerAck(true);
        // 收到好友申请是否自动同意，如果是自动同意就不会收到好友请求的回调，因为sdk会自动处理，默认为true
        options.setAcceptInvitationAlways(false);
        // 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
        options.setAutoAcceptGroupInvitation(false);
        // 设置（主动或被动）退出群组时，是否删除群聊聊天记录
        options.setDeleteMessagesAsExitGroup(false);
        // 设置是否允许聊天室的Owner 离开并删除聊天室的会话
        options.allowChatroomOwnerLeave(true);
        // 设置google GCM推送id，国内可以不用设置
        // options.setGCMNumber(MLConstants.ML_GCM_NUMBER);
        // 设置集成小米推送的appid和appkey
        // options.setMipushConfig(MLConstants.ML_MI_APP_ID, MLConstants.ML_MI_APP_KEY);

        // 调用初始化方法初始化sdk
//        EMClient.getInstance().init(instance, options);
        EaseUI.getInstance().init(instance, options);*/

        EMClient.getInstance().addConnectionListener(new EMConnectionListener() {
            @Override
            public void onConnected() {

            }

            @Override
            public void onDisconnected(int error) {
                if (error == EMError.USER_REMOVED) {
                    User.logOut();
                    // 显示帐号已经被移除
                    QBLToast.show("您的帐号已经被移除");
                } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                    // 显示帐号在其他设备登录
                    QBLToast.show("帐号在其他设备登录");
                    User.logOut();
                } else {

                }
            }
        });


        // 设置开启debug模式
//        EMClient.getInstance().setDebugMode(true);

        // 设置初始化已经完成
        isInit = true;
    }

    public void EaseLogIn(String account, String password) {
        EMClient.getInstance().login(account, password, new MyEMCallBack());

    }

    /**
     * 根据Pid获取当前进程的名字，一般就是当前app的包名
     *
     * @param pid 进程的id
     * @return 返回进程的名字
     */
    private String getAppName(int pid) {
        String processName = null;
        ActivityManager activityManager = (ActivityManager) instance.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    processName = info.processName;
                    // 返回当前进程名
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 没有匹配的项，返回为null
        return null;
    }


    public static App getInstance() {
        if (instance == null) {
            return new App();
        }
        return instance;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivities() {
        // 发送广播，接收到广播的Activity将会执行finish()方法
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(BaseActivity.ACTION_FINISH_ACTIVITY));
    }


    public void restart() {
        // 销毁线程池
        ThreadPool.restart();
        // 结束所有Activity
        finishAllActivities();
//         启动登录Activity
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        this.startActivity(intent);
    }

    public void restart(Activity activity) {
        // 销毁线程池
        ThreadPool.restart();
        // 结束所有Activity
        finishAllActivities();
//         启动登录Activity
//        MainActivity.start(activity);
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        Fresco.shutDown();
    }

    public SharedPreferences getPreferences(String name) {
        return getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
        LogUtils.e("当前存在" + activities.size() + "个Activity");
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            activities.remove(activity);
        }
        LogUtils.e("当前存在" + activities.size() + "个Activity");
    }

    public MainActivity getMainActivity() {
        for (Activity activity : activities) {
            if (activity instanceof MainActivity) {
                return (MainActivity) activity;
            }
        }
        return null;
    }
    /**
     * 清除应用缓存
     */
    public void clearAppCache() {
//        // 清除ImageLoader缓存的图片
//        ImageLoader.getInstance().clearDiskCache();
        // 删除Cache目录
//        FileUtil.deleteFilesByDirectory(getCacheDir());
//        FileUtil.deleteFilesByDirectory(getExternalCacheDir());
    }

    /**
     * 清除应用数据
     */
    public void clearAppData() {
        // 清除所有用户数据
        clearUserData();
        // 删除目录
//        FileUtil.deleteFilesByDirectory(getFilesDir());
//        FileUtil.deleteFilesByDirectory(getExternalFilesDir(null));
    }

    /**
     * 清除用户数据
     */
    public static void clearUserData() {
        // 清空用户信息
        User.clear();
        removeCookie(getInstance());
    }

    /**
     * 删除Cookie
     *
     * @param context 上下文
     */
    private static void removeCookie(Context context) {
        CookieSyncManager.createInstance(context);
        if (VolleyManager.getCookies() != null) {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            CookieSyncManager.getInstance().sync();
        }
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//    }

    private static class MyEMCallBack implements EMCallBack {
        @Override
        public void onSuccess() {
            EMClient.getInstance().groupManager().loadAllGroups();
            EMClient.getInstance().chatManager().loadAllConversations();
            QBLToast.show("easeLog success");
        }

        @Override
        public void onError(int i, String s) {
            QBLToast.show("easeLog error:" + s);
        }

        @Override
        public void onProgress(int i, String s) {

        }
    }
}
