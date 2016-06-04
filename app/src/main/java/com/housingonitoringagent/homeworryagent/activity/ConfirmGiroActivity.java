package com.housingonitoringagent.homeworryagent.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
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
public class ConfirmGiroActivity extends BaseActivity implements View.OnClickListener, OnCheckedChangeListener{


    // 验证码获取时间最小间隔
    private final int VERIFICATION_CODE_TIME_INTERVAL = 60;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rbPartyA)
    RadioButton rbPartyA;
    @Bind(R.id.rbPartyB)
    RadioButton rbPartyB;
    @Bind(R.id.rgParty)
    RadioGroup rgParty;
    @Bind(R.id.tvAgreement)
    TextView tvAgreement;
    @Bind(R.id.tvGiroTo)
    TextView tvGiroTo;
    @Bind(R.id.tvAgentPhone)
    TextView tvAgentPhone;
    @Bind(R.id.etCode)
    EditText etCode;
    @Bind(R.id.btnCode)
    Button btnCode;
    @Bind(R.id.btnConfirm)
    Button btnConfirm;
    @Bind(R.id.btnCancel)
    Button btnCancel;
    @Bind(R.id.llGiro)
    LinearLayout llGiro;

    // 倒计时
    int mVerifyCodeTime = 0;
    // 验证码计时器
    Timer mSMSTimer;
    Handler mUIHandler;
    SmsHelper mSMSHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_giro);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mUIHandler = new Handler();
        mSMSHelper = new SmsHelper(this, getVolleyRequestQueue());

        if (savedInstanceState != null) {
            etCode.setText("");
        } else {
            tvAgentPhone.setText(getIntent().getStringExtra("agentPhone"));
            rbPartyA.setText(getIntent().getStringExtra("partyA"));
            rbPartyB.setText(getIntent().getStringExtra("partyB"));
        }
        llGiro.setVisibility(View.GONE);
        initView();
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
        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);

        mSMSHelper.setListener(new SmsHelper.SmsListener() {

            @Override
            public void onRequestVerificationCode(boolean success, String reason) {
                dismissProgressDialog();
                ConfirmGiroActivity.this.onGetVerificationCode(success, reason);
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
                ConfirmGiroActivity.this.onVerificationCodeReceived(verificationCode);
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
                String phone = tvAgentPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    QBLToast.show(R.string.text_hint_edit_phone);
                    return;
                }
//                showProgressDialog(getString(R.string.wait_a_few_times));
//                mSMSHelper.beginListenSms();
                mSMSHelper.getVerificationCode(phone.toString().trim());
                break;
            case R.id.btnConfirm:
                confirmGiro();
                break;
            case R.id.tvAgreement:

                break;
        }
    }

    private void confirmGiro() {
        final String phone = tvAgentPhone.getText().toString();
        final String verificationCode = etCode.getText().toString();
        boolean inputValid = checkInputValid(phone, verificationCode);
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
//                                ConfirmGiroActivity.this.finish();
//                                QBLToast.show("密码找回成功");
                                new AlertDialog.Builder(getThis()).setTitle(R.string.successful)
                                        .setMessage("")
                                        .setCancelable(false)
                                        .setPositiveButton(R.string.confirm, null)
                                        .show();
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
                params.put("mobilephone", phone);
                params.put("captcha", verificationCode);

                return params;
            }
        };
        getVolleyRequestQueue().add(request);
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

    private void initView() {
        toolbar.getBackground().setAlpha(0);
        getSupportActionBar().setElevation(1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(1);
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
            btnCode.setText(R.string.text_send_code);
            btnCode.setEnabled(true);
        }
    }

    private boolean checkInputValid(String phone, String verificationCode) {
        int phoneLength = StringUtil.getBytesLength(phone);
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
        }
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (llGiro.getVisibility()!=View.VISIBLE) {
            llGiro.setVisibility(View.VISIBLE);
        }
        String title;
        if (checkedId == R.id.rbPartyA) {
            title = "您已选择“将钱转给" + getString(R.string.party_a) + "”";
        } else {
            title = "您已选择“将钱转给" + getString(R.string.party_b) + "”";
        }
        tvGiroTo.setText(title);
    }
}
