package com.housingonitoringagent.homeworryagent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.User;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.net.SmsHelper;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**修改密码
 * Created by android on 2016/5/30.
 */
public class PasswordChangeActivity extends BaseActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

    // 验证码获取时间最小间隔
    private final int VERIFICATION_CODE_TIME_INTERVAL = 60;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.etCode)
    EditText etCode;
    @Bind(R.id.etNewPassword)
    EditText etNewPassword;
    @Bind(R.id.cbShowPassword)
    CheckBox cbShowPassword;
    @Bind(R.id.btnCommit)
    Button btnCommit;
    @Bind(R.id.btnCode)
    Button btnCode;


    // 倒计时
    int mVerifyCodeTime = 0;
    // 验证码计时器
    Timer mSMSTimer;
    Handler mUIHandler;
    SmsHelper mSMSHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.modify_password);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mUIHandler = new Handler();
        mSMSHelper = new SmsHelper(this, getVolleyRequestQueue());
        setListeners();
    }

    private void setListeners() {
        cbShowPassword.setOnCheckedChangeListener(this);
        btnCode.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        mSMSHelper.setListener(new SmsHelper.SmsListener() {

            @Override
            public void onRequestVerificationCode(boolean success, String reason) {
                dismissProgressDialog();
                PasswordChangeActivity.this.onGetVerificationCode(success, reason);
            }

            @Override
            public void onNetworkException() {
                dismissProgressDialog();
                QBLToast.show(R.string.network_exception);
            }

            @Override
            public void onReceivedSms(String smsBody) {
                dismissProgressDialog();
            }

            @Override
            public void onReceivedVerificationCode(String verificationCode) {
                dismissProgressDialog();
                PasswordChangeActivity.this.onVerificationCodeReceived(verificationCode);
            }
        });
    }

    private void onGetVerificationCode(boolean successful, String reason) {
        if (successful) {
            startVerificationCodeTimer();
            QBLToast.show(R.string.verification_code_send);
        } else {
            startVerificationCodeTimer();
            QBLToast.show(reason);
        }
    }
    private void onVerificationCodeReceived(String verificationCode) {
        etCode.setText(verificationCode);
    }

    /**
     * 开始倒计时，60秒内不可再次获取验证码
     */
    public void startVerificationCodeTimer() {
        // 先停止之前的，如果有
        stopVerificationCodeTimer();

        // 重置计时器时间
        mVerifyCodeTime = VERIFICATION_CODE_TIME_INTERVAL;
        // 初始化计时器
        mSMSTimer = new Timer();
        // 计时器每一秒回调
        final Runnable timerTickCallback = new Runnable() {
            @Override
            public void run() {
                onVerifyCodeTimerTick();
            }
        };
        // 初始化计时器任务
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mUIHandler.post(timerTickCallback);
            }
        };
        // 开始执行
        mSMSTimer.schedule(task, 0, 1000);
    }

    /**
     * 结束倒计时，可再次获取验证码
     */
    public void stopVerificationCodeTimer() {
        if (mSMSTimer != null) {
            mSMSTimer.cancel();
            mSMSTimer = null;
        }
        mVerifyCodeTime = 0;
    }

    /**
     * 验证码计时器每一跳都会回调此方法
     */
    public void onVerifyCodeTimerTick() {
        if (mVerifyCodeTime > 0) {
            mVerifyCodeTime -= 1;
            btnCode.setText(mVerifyCodeTime + getString(R.string.second));
            btnCode.setEnabled(false);
        } else {
            btnCode.setText(R.string.text_send_code);
            btnCode.setEnabled(true);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCode:
                String phone = User.getMobilephone();
                if (TextUtils.isEmpty(phone)) {
//                    QBLToast.show(R.string.text_hint_edit_phone);
                    return;
                }
//                showProgressDialog(getString(R.string.wait_a_few_times));
//                mSMSHelper.beginListenSms();
                mSMSHelper.getVerificationCode(phone.toString().trim());
                break;
            case R.id.btnCommit:
                String code = etCode.getText().toString().trim();
                String password = etNewPassword.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    QBLToast.show(R.string.text_empty_code);
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    QBLToast.show(getString(R.string.text_empty_password));
                    return;
                }
                commit();
                break;
            case R.id.tvAgreement:
                start(WebViewActivity.class, new BaseIntent() {
                    @Override
                    public void setIntent(Intent intent) {
                        intent.putExtra(getString(R.string.extra_title), getString(R.string.title_capital_administration));
                        intent.putExtra(getString(R.string.extra_url), Const.serviceMethod.USERREGISTERDEAL);
                    }
                });
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            etNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else {
            etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    /*private void getCode() {
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.GET_PHONE, new VolleyResponseListener() {
            @Override
            public void handleJson(com.alibaba.fastjson.JSONObject json) {
                super.handleJson(json);
                int resultCode = json.getIntValue("resultCode");
                String message = json.getString("message");
                if (resultCode == 1) {
                   String phone = json.getJSONObject("content").getString("mobilephone");
                    if (phone != null) {
                        etCode.setText(phone);
                    }
                } else {

                }
                QBLToast.show(message);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                QBLToast.show(R.string.network_exception);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = super.getParams();
                return params;
            }
        };
        getThis().getVolleyRequestQueue().add(request);
    }*/

    private void commit() {
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.CHANGE_PASSWORD, new VolleyResponseListener() {
            @Override
            public void handleJson(com.alibaba.fastjson.JSONObject json) {
                super.handleJson(json);
                int resultCode = json.getIntValue("resultCode");
                String message = json.getString("message");
                if (resultCode == 1) {
                } else {
                }
                QBLToast.show(message);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                QBLToast.show(R.string.network_exception);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = super.getParams();
                params.put("password", etNewPassword.getText().toString());
                params.put("captcha", etCode.getText().toString().trim());
                return params;
            }
        };
        getThis().getVolleyRequestQueue().add(request);
    }
}
