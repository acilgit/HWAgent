package com.housingonitoringagent.homeworryagent;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.housingonitoringagent.homeworryagent.activity.ReLoginActivity;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.LogUtils;
import com.housingonitoringagent.homeworryagent.utils.easeui.EaseHelper;
import com.housingonitoringagent.homeworryagent.utils.net.FrescoFactory;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyManager;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

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
//    private boolean reLogin = false;

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = this;
            // 初始化环信SDK
            initEaseMob();
//        App.getInstance().getPackageName();
            Log.e("qqq", " create App " + 70);
        }

    }

  /*  @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/

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

        // 设置开启debug模式
//        EMClient.getInstance().setDebugMode(true);

        // 设置初始化已经完成
        isInit = true;
    }

    public void EaseLogIn(String account, String password) {
        EMClient.getInstance().login(account, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
//            QBLToast.show("easeLog success");
            }

            @Override
            public void onError(int i, String s) {
                QBLToast.show("easeLog error:" + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
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



    public void restartAndLogin() {
        restartAndLogin("", "");
    }

    public void restartAndLogin(final String title, final String message) {
//        reLogin = true;
        // 销毁线程池
//        ThreadPool.restart();
        // 结束所有Activity
//        finishAllActivity();

        final BaseActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LogUtils.e("restartAndLogin thread:" + Thread.currentThread().getName());
                    User.logOut();
                    EMClient.getInstance().logout(true);
                    finishAllActivities();
                    Intent intent = new Intent(activity, ReLoginActivity.class);
                    intent.putExtra(getInstance().getString(R.string.extra_title), title);
                    intent.putExtra(getInstance().getString(R.string.extra_message), message);
                    activity.startActivity(intent);
                }
            });
        }
    }

   /* public void restart(Activity activity) {
        // 销毁线程池
//        ThreadPool.restart();
        // 结束所有Activity
        finishAllActivities();
//         启动登录Activity
//        MainActivity.start(activity);
        Intent intent = new Intent();
        intent.setClass(this, activity.getClass());
        this.startActivity(intent);
    }*/

   public void getOut() {
       // 销毁线程池
//        ThreadPool.restart();
       // 结束所有Activity
       finishAllActivities();
//        finishAllActivity();
   }

    /**
     * 结束所有Activity
     * DialogActivity不在管理范围内
     */
    public void finishAllActivities() {
        // 发送广播，接收到广播的Activity将会执行finish()方法
        LocalBroadcastManager.getInstance(getInstance()).sendBroadcast(new Intent(BaseActivity.ACTION_FINISH_ACTIVITY));
    }
    public void addActivity(Activity activity) {
        activities.add(activity);
        LogUtils.e("当前存在" + activities.size() + "个Activity");
    }

    public void removeActivity(Activity activity) {
        if (activities.contains(activity)) {
            activities.remove(activity);
        }
        LogUtils.e("当前存在" + activities.size() + "个Activity");

//         启动登录Activity
       /* if (activities.size() == 0 && reLogin) {
            reLogin = false;
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);
        }*/
    }

    public BaseActivity getActivity() {
        for (Activity activity : activities) {
            return (BaseActivity) activity;
        }
        return null;
    }

    public void finishAllActivity() {
        for (Activity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        Fresco.shutDown();
    }

    public SharedPreferences getPreferences(String name) {
        return getSharedPreferences(name, Context.MODE_PRIVATE);
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

}
