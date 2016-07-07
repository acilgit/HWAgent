package com.housingonitoringagent.homeworryagent.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.housingonitoringagent.homeworryagent.App;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.User;
import com.housingonitoringagent.homeworryagent.beans.UpdaterBean;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.DateUtil;
import com.housingonitoringagent.homeworryagent.utils.UIUtils;
import com.housingonitoringagent.homeworryagent.utils.Update;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;
import com.hyphenate.chat.EMClient;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/1/15 0015.
 */
public class SecurityActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rlPassword)
    RelativeLayout rlLoginPassword;
    @Bind(R.id.rlSafeModifyPhone)
    RelativeLayout rlSafeModifyPhone;
    @Bind(R.id.rlCheckVersion)
    RelativeLayout rlCheckVersion;
    @Bind(R.id.pbSecurity)
    ProgressBar pbSecurity;
    @Bind(R.id.tvSecurityLevel)
    TextView tvSecurityLevel;
    @Bind(R.id.tvCheckVersion)
    TextView tvCheckVersion;
    @Bind(R.id.tvSafeModifyPhone)
    TextView tvSafeModifyPhone;
    @Bind(R.id.btnLogout)
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_securlty);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.title_security);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* init */
//        tvSecurityLevel.setText(User.get);
        tvSafeModifyPhone.setText(User.getMobilephone());

        int level = User.getSafeLevel();
        pbSecurity.setProgress(level==0 ? 30 : (level==1 ? 60 : 100));
        tvSecurityLevel.setText(level==0 ? getString(R.string.text_low) : (level==1 ? getString(R.string.text_middle) : getString(R.string.text_high)));
        tvCheckVersion.setText(UIUtils.getVersionName(getThis()));

        /* init listeners */
        rlLoginPassword.setOnClickListener(this);
        rlCheckVersion.setOnClickListener(this);
//        rlSafeModifyPhone.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlCheckVersion:
                updater();
                break;
            case R.id.rlSafeModifyPhone:
                break;
            case R.id.rlPassword:
                start(PasswordChangeActivity.class);
                break;
            case R.id.btnLogout:
                new AlertDialog.Builder(getThis()).setCancelable(true).setTitle(getString(R.string.logout))
                        .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                User.logOut();
                                if (EMClient.getInstance().isConnected() || EMClient.getInstance().isLoggedInBefore()) {
                                    EMClient.getInstance().logout(true);
                                }
                                App.getInstance().restartAndLogin();
                                finish();
                            }
                        }).setNegativeButton(R.string.cancel, null)
                        .show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //App.getRefWatcher(this).watch(this);
    }

    private void updater() {
        StringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.LATEST, new VolleyResponseListener() {
            @Override
            public void handleJson(JSONObject json) {
                super.handleJson(json);
                int result = json.getIntValue("resultCode");
                switch (result) {
                    case 1:
                        UpdaterBean bean = JSON.parseObject(json.toJSONString(), UpdaterBean.class);
                        int version = bean.getContent().getInteriorCode();
                        int version2 = UIUtils.getVersionCode(getThis());
                        if (version > version2) {
                            new Update(getString(R.string.text_update_tips), DateUtil.getStrTime(bean.getContent().getUpdateTime()), bean.getContent().getUpgradePoromet(), bean.getContent().getDlUrl(),getThis());
                        } else {
                            QBLToast.show(getString(R.string.text_latest_version));
                        }
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = super.getParams();
                params.put("type","2"); // 2为经纪人端
                return params;
            }
        };
        getVolleyRequestQueue().add(request);
    }

}
