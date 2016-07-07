package com.housingonitoringagent.homeworryagent.utils;

import android.app.Activity;
import android.content.DialogInterface;

import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.utils.net.HttpConnectService;
import com.housingonitoringagent.homeworryagent.views.CustomDialog;


/**
 * Created by Administrator on 2016/6/28 0028.
 */
public class Update {
    public Update(String title, String time, String msg, final String url, final Activity activity){
        CustomDialog.Builder builder = new CustomDialog.Builder(activity);
        builder.setMessage(msg, time);
        builder.setTitle(title);
        builder.setPositiveButton(R.string.text_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置你的操作事项
                HttpConnectService hcs = new HttpConnectService();
                hcs.connectDownLoad(activity.getApplicationContext(), activity.getPackageName(),url);
            }
        });

        builder.setNegativeButton(R.string.text_cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }
}
