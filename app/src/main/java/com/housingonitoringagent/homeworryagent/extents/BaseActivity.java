package com.housingonitoringagent.homeworryagent.extents;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.housingonitoringagent.homeworryagent.App;

/**
 * Created by Administrator on 2016/1/11 0011.
 */
public class BaseActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_GOT_RESULT = 100;

    ProgressDialog mProgressDialog;
    RequestQueue mVolleyRequestQueue;
    BroadcastReceiver mFinishReceiver;
    BaseActivity thisActivity;

    public static final String ACTION_FINISH_ACTIVITY = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
//        if (!(thisActivity instanceof LoginActivity)) {
            registerFinishReceiver();
//        }
        App.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
//        cancelVolleyRequestQueue();
        App.getInstance().removeActivity(this);
    }

    protected BaseActivity getThis() {
        return this;
    }

   /* public void start(Activity activity) {
        if (thisActivity!=null) {
            Intent intent = new Intent();
            intent.setClass(thisActivity, activity.getClass());
            activity.startActivity(intent);
        }
    }*/

    public void start(Class<? extends BaseActivity> activityClass) {
        Intent intent = new Intent();
        intent.setClass(this, activityClass);
        this.startActivity(intent);
    }

    public void start(Class<? extends BaseActivity> activityClass, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, activityClass);
        this.startActivityForResult(intent, requestCode);
    }

    public void start(Class<? extends BaseActivity> activityClass, BaseIntent baseIntent) {
        Intent intent = new Intent();
        intent.setClass(this, activityClass);
        baseIntent.setIntent(intent);
        this.startActivity(intent);
    }

    public void start(Class<? extends BaseActivity> activityClass, BaseIntent baseIntent, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, activityClass);
        baseIntent.setIntent(intent);
        this.startActivityForResult(intent, requestCode);
    }

    public void cancelVolleyRequestQueue() {
        if (mVolleyRequestQueue != null) {
            mVolleyRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
        }
    }

    public void showProgressDialog(CharSequence text) {
        showProgressDialog(text, false);
    }

    public void showProgressDialog(CharSequence text, boolean cancelable) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        } else {
            mProgressDialog.dismiss();
        }

        mProgressDialog.setCanceledOnTouchOutside(cancelable);
        if (cancelable) {
            mProgressDialog.setOnCancelListener(null);
        } else {
            mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
        }

        mProgressDialog.setMessage(text);
        mProgressDialog.show();
    }

    public void setProgressDialogMessage(int resId) {
        setProgressDialogMessage(getString(resId));
    }

    public void setProgressDialogMessage(CharSequence text) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setMessage(text);
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void hideSoftInput() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public RequestQueue getVolleyRequestQueue() {
        if (mVolleyRequestQueue == null) {
            mVolleyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mVolleyRequestQueue;
    }

    // 注册广播接收器
    // 当接收到广播时，退出Activity
    protected void registerFinishReceiver() {
        mFinishReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_FINISH_ACTIVITY);
        // 注册广播监听器
        LocalBroadcastManager.getInstance(this).registerReceiver(mFinishReceiver, filter);
    }

    /**
     * @param // activity               设置的activity
     * @param // drawerLayout           设置的控件
     * @param // displayWidthPercentage 滑动范围
     */
    /*protected static void setDrawerLeftEdgeSize(Activity activity,
                                                DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null)
            return;
        try {
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField(
                    "mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField
                    .get(drawerLayout);

            Field edgeSizeField = leftDragger.getClass().getDeclaredField(
                    "mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize,
                    (int) (dm.widthPixels * displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
            Log.d("NoSuchFieldException", e.toString());
        } catch (IllegalArgumentException e) {
            Log.d("IllegalArgumentExceptio", e.toString());
        } catch (IllegalAccessException e) {
            Log.d("IllegalAccessException", e.toString());
        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public interface BaseIntent {
        void setIntent(Intent intent);
    }

}
