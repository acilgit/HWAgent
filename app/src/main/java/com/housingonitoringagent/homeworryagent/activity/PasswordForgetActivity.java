package com.housingonitoringagent.homeworryagent.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.StringUtil;
import com.housingonitoringagent.homeworryagent.utils.net.SmsHelper;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 忘记密码
 * HomeWorry
 * Created by Administrator on 2016/2/24 0024.
 */
public class PasswordForgetActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.etPhone)
    EditText etPhone;
    @Bind(R.id.btnCode)
    Button btnCode;
    @Bind(R.id.etCode)
    EditText etCode;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @Bind(R.id.btnCommit)
    Button btnCommit;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tvAgreement)
    TextView tvAgreement;
//    @Bind(R.id.gps)
//    ImageView mGps;
//    @Bind(R.id.place_name)
//    TextView mPlace;
//    @Bind(R.id.statione_mail)
//    ImageView mStatione;
//    @Bind(R.id.communication)
//    ImageView mCmmunication;
//    @Bind(R.id.back)
//    ImageView mBack;


    // 验证码获取时间最小间隔
    private final int VERIFICATION_CODE_TIME_INTERVAL = 60;
    // 倒计时
    int mVerifyCodeTime = 0;
    // 验证码计时器
    Timer mSMSTimer;
    Handler mUIHandler;
    SmsHelper mSMSHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.label_forget_password);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mUIHandler = new Handler();
        mSMSHelper = new SmsHelper(this, getVolleyRequestQueue());
        initview();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSMSHelper.endListenSms();
        mUIHandler.removeCallbacksAndMessages(null);
    }

    private void setListener() {
        btnCode.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);

        mSMSHelper.setListener(new SmsHelper.SmsListener() {

            @Override
            public void onRequestVerificationCode(boolean success, String reason) {
                dismissProgressDialog();
                PasswordForgetActivity.this.onGetVerificationCode(success, reason);
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
                PasswordForgetActivity.this.onVerificationCodeReceived(verificationCode);
            }
        });
    }

    private void onVerificationCodeReceived(String verificationCode) {
        etCode.setText(verificationCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCode:
                String phone = etPhone.getText().toString();
                int phoneLength = phone.length();
                if (phoneLength < 1) {
                    QBLToast.show(R.string.text_hint_edit_phone);
                    return ;
                } else if (phoneLength < Const.Account.PHONE_LENGTH) {
                    QBLToast.show(R.string.please_input_vaild_phone);
                    return;
                }
                mSMSHelper.getVerificationCode(phone.toString().trim());
                break;
            case R.id.btnCommit:
                resetPassword();
                break;
            case R.id.tvAgreement:
                start(WebViewActivity.class, new BaseIntent() {
                    @Override
                    public void setIntent(Intent intent) {
                        intent.putExtra(getString(R.string.extra_title), getString(R.string.user_agreement));
                        intent.putExtra(getString(R.string.extra_url), Const.serviceMethod.USERREGISTERDEAL);
                    }
                });
                break;
        }
    }

    private void resetPassword() {
        final String phone = etPhone.getText().toString();
        final String password = etPassword.getText().toString();
        final String confirmPassword = etConfirmPassword.getText().toString();
        final String verificationCode = etCode.getText().toString();
        boolean inputValid = checkInputValid(phone, password, confirmPassword, verificationCode);
        if (!inputValid) {
            return;
        }

        showProgressDialog(getString(R.string.wait_a_few_times));
        StringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.SAVEPASSWORD,
                new VolleyResponseListener() {
                    @Override
                    public void handleJson(JSONObject json) {
                        super.handleJson(json);
                        dismissProgressDialog();
                        int result = json.getIntValue("resultCode");
                        String msg = json.getString("message");
                        switch (result) {
                            case 1:
                                PasswordForgetActivity.this.finish();
                                QBLToast.show("密码找回成功");
                                break;
                            default:
                                QBLToast.show(msg);
                                break;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissProgressDialog();

                        QBLToast.show(R.string.network_exception);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = super.getParams();
                params.put("mobilephone", phone);
                params.put("password", password);
                params.put("captcha", verificationCode);
                return params;
            }
        };
        getVolleyRequestQueue().add(request);
    }

    private void initview() {
        toolbar.getBackground().setAlpha(0);
        getSupportActionBar().setElevation(1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(1);
        }
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
            btnCode.setText(R.string.get_verification_code);
            btnCode.setEnabled(true);
        }
    }

    private boolean checkInputValid(String phone, String password, String confirmPassword, String verificationCode) {
        int phoneLength = StringUtil.getBytesLength(phone);
        int passwordLength = StringUtil.getBytesLength(password);
        int confirmPasswordLength = StringUtil.getBytesLength(confirmPassword);
        int verificationCodeLength = StringUtil.getBytesLength(verificationCode);

        if (phoneLength < 1) {
            QBLToast.show(R.string.text_hint_edit_phone);
            return false;
        } else if (phoneLength < Const.Account.PHONE_LENGTH) {
            QBLToast.show(R.string.please_input_vaild_phone);
            return false;
        } else if (verificationCodeLength < 1) {
            QBLToast.show(R.string.text_hint_edit_code);
            return false;
        } else if (passwordLength < 1) {
            QBLToast.show(R.string.text_hint_edit_new_password);

            return false;
        } else if (passwordLength < Const.Account.PASSWORD_MIN_LENGTH) {
            QBLToast.show(R.string.password_too_short);

            return false;
        } else if (passwordLength > Const.Account.PASSWORD_MAX_LENGTH) {
            QBLToast.show(R.string.password_too_long);
            return false;
        } else if (StringUtil.hasEmojiCharacter(password)) {
            QBLToast.show(R.string.password_not_allow);
            return false;
        } else if (confirmPasswordLength < 1) {
            QBLToast.show(R.string.please_confirm_password);
            return false;
        } else if (!password.equals(confirmPassword)) {
            QBLToast.show(R.string.password_not_match);
            return false;
        }
        return true;
    }
}
