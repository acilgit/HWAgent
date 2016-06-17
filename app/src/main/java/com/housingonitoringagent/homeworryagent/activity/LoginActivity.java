package com.housingonitoringagent.homeworryagent.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.housingonitoringagent.homeworryagent.beans.UserBean;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.housingonitoringagent.homeworryagent.utils.UIUtils.underLine;


/**登录
 * HomeWorry
 * Created by Administrator on 2016/2/24 0024.
 */
public class LoginActivity extends BaseActivity {
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.etAccount)
    EditText etAccount;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.tvRetrieve)
    TextView tvRetrieve;

    public static Activity instance;

    private final String account = "18918918909";
    private final String easeAccount ="fc_18918918909";
    private final String password = "123456";

    public static void finishInstance() {
        if (instance != null) {
            instance.finish();
        }
        instance = null;
    }

   /* public static void start(Context activity) {
        Intent intent = new Intent();
        intent.setClass(activity, LoginActivity.class);

        if (!(activity instanceof Activity))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_login);
//        setSwipeBackEnable(false);
        ButterKnife.bind(this);
        underLine(tvRetrieve);
        setListener();
        etAccount.setText(account);
        etPassword.setText(password);
    }

    private void setListener() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.onClick(v);
            }
        };

        btnLogin.setOnClickListener(onClickListener);
//        mAgreement.setOnClickListener(onClickListener);
//        mRegister.setOnClickListener(onClickListener);
        tvRetrieve.setOnClickListener(onClickListener);
    }

    private void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                requestLogin();
                break;
//            case R.id.agreement_tv:
//                break;
            case R.id.tvRetrieve:
                start(ForgetPasswordActivity.class);
                break;
        }
    }

    private void requestLogin() {
        final String username = etAccount.getText().toString().trim();
        final String password = etPassword.getText().toString();
        // 合法性检查
        if (username.length() < 1) {
            QBLToast.show(R.string.please_input_username);
            return;
        } else if (password.length() < 1) {
            QBLToast.show(R.string.please_input_password);
            return;
        }
        showProgressDialog(getString(R.string.signing_in));

        StringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.LOGIN,
                new VolleyResponseListener() {
                    @Override
                    public void handleJson(JSONObject json) {
                        super.handleJson(json);
                        dismissProgressDialog();
                        int result = json.getIntValue("resultCode");
                        String  msg = json.getString("message");
                        switch (result){
                            case 1:
//                                JSONObject userJSON = json.getJSONObject("content");
                                UserBean userInfo = JSON.toJavaObject(json, UserBean.class);
                                userInfo.getContent().setMobilephone(username);
//                                userInfo.setSessionId(userInfo.getSessionId("sessionId"));
                                onLogin(userInfo);
                                App.getInstance().EaseLogIn(easeAccount, easeAccount);
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
                params.put("mobilephone", username);
                params.put("password", password);
                return params;
            }
        };
        getVolleyRequestQueue().add(request);
    }

    private void onLogin(UserBean loginResp) {
        onLogin(null, loginResp);
    }

    /**
     *
     * @param provider 账号来源，如微信登录
     * @param loginResp
     */
    private void onLogin(String provider, UserBean loginResp) {
        // 设置登录态
        User.logIn(provider, loginResp);

        QBLToast.show(R.string.sign_in_success);


        if (!getIntent().getBooleanExtra("FromVisitor", false)) {
            start(MainActivity.class);
        }
        LoginActivity.finishInstance();
    }
}
