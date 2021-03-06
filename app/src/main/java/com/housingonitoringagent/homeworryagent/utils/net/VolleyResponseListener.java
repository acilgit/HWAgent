package com.housingonitoringagent.homeworryagent.utils.net;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.housingonitoringagent.homeworryagent.App;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;

public abstract class VolleyResponseListener implements Response.Listener<String> {

    // 状态码 : 登录已过期
    static final int NOT_LOGIN = -1;

    @Override
    public void onResponse(String response) {
        Log.d(VolleyResponseListener.class.getName(), "返回值：" + response);
        JSONObject json;


        try {
            json = JSON.parseObject(response);
        } catch (Exception e) {
            Log.e(VolleyResponseListener.class.getName(), e.getMessage());
            Log.e(VolleyResponseListener.class.getName(), "转换返回值为JSON时失败");
            e.printStackTrace();
            json = new JSONObject();
        }

        try {
            ResponseModel respModel = JSON.toJavaObject(json, ResponseModel.class);
            Log.d(VolleyResponseListener.class.getName()+"yyy", "message : " + respModel.getMessage());
            if (respModel.getResultCode()==NOT_LOGIN) {
                QBLToast.show("登录失败，请重新登录");
                // 退出用户登录
//                User.logOut();
                // 重启应用
               /* App.getInstance().getActivity().start(LoginActivity.class, new BaseActivity.BaseIntent() {
                    @Override
                    public void setIntent(Intent intent) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    }
                });*/
                App.getInstance().restartAndLogin(App.getInstance().getString(R.string.text_relogin), App.getInstance().getString(R.string.app_name) + App.getInstance().getString(R.string.text_login_orther_phone));

                return;
            }
            Log.d(VolleyResponseListener.class.getName(), "message : " + respModel.getMessage());
        } catch (Exception e) {
            Log.e(VolleyResponseListener.class.getName(), e.toString());
            e.printStackTrace();
        }

        try {
            handleJson(json);
        } catch (Exception e) {
            Log.e(VolleyResponseListener.class.getName(), e.toString());
            e.printStackTrace();
        }
    }

    public void handleJson(JSONObject json) {

    }
}
