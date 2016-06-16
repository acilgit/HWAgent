package com.housingonitoringagent.homeworryagent.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
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
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**修改密码
 * Created by android on 2016/5/30.
 */
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.etCurrentPassword)
    EditText etCurrentPassword;
    @Bind(R.id.etNewPassword)
    EditText etNewPassword;
    @Bind(R.id.cbShowPassword)
    CheckBox cbShowPassword;
    @Bind(R.id.btnCommit)
    Button btnCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.modify_password);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setListeners();
    }

    private void setListeners() {
        cbShowPassword.setOnCheckedChangeListener(this);
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            etNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }else {
            etNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    private void commit() {
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.POST, "", new VolleyResponseListener() {
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
                params.put("id", String.valueOf(getIntent().getStringExtra(getString(R.string.extra_id))));
                return params;
            }
        };
        getThis().getVolleyRequestQueue().add(request);
    }
}
