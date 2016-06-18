package com.housingonitoringagent.homeworryagent.activity;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.User;
import com.housingonitoringagent.homeworryagent.beans.ShowHouseBean;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowingActivity extends BaseActivity implements View.OnClickListener {

//    public final static int PERMIT_STATUS_END = 0;
//    public final static int PERMIT_STATUS_START = 2;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.sivHead)
    SimpleDraweeView sivHead;
    @Bind(R.id.tvCompany)
    TextView tvCompany;
    @Bind(R.id.tvOutlet)
    TextView tvOutlet;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvVillage)
    TextView tvVillage;
    @Bind(R.id.tvBuildingNo)
    TextView tvBuildingNo;
    @Bind(R.id.tvShowingCountIn)
    TextView tvShowingCountIn;
    @Bind(R.id.trShowingOut1)
    TableRow trShowingOut1;
    @Bind(R.id.etShowingCountOut)
    EditText etShowingCountOut;
    @Bind(R.id.trShowingOut2)
    TableRow trShowingOut2;
    @Bind(R.id.etShowingCountIn)
    EditText etShowingCountIn;
    @Bind(R.id.trShowingIn)
    TableRow trShowingIn;
    @Bind(R.id.btnCommit)
    Button btnCommit;

    private ShowHouseBean.ContentBean.Content bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.title_showing);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bean = (ShowHouseBean.ContentBean.Content) getIntent().getSerializableExtra(getString(R.string.extra_bean));
        btnCommit.setOnClickListener(this);
        init();
    }

    private void init() {
        switch (bean.getPermitStatus()) {
            case ShowHouseBean.PERMIT_STATUE_SHOWING: // 待确认
                btnCommit.setText("看房离开");
                trShowingIn.setVisibility(View.GONE);
                trShowingOut1.setVisibility(View.VISIBLE);
                trShowingOut2.setVisibility(View.VISIBLE);
                etShowingCountIn.setText(bean.getApplyVisitNumber() + "");
                break;
            case ShowHouseBean.PERMIT_STATUE_WAIT: // 未看房
                btnCommit.setText("进入看房");
                trShowingIn.setVisibility(View.VISIBLE);
                trShowingOut1.setVisibility(View.GONE);
                trShowingOut2.setVisibility(View.GONE);
                break;
            default:
                finish();
                break;
        }
        sivHead.setImageURI(Uri.parse(User.getHeadUrl()));
        tvName.setText(User.getNickname());
        tvCompany.setText(bean.getCompanyName());
        tvOutlet.setText(bean.getStoreName());
        tvVillage.setText(bean.getVillageName());
        tvBuildingNo.setText(bean.getHouseNumber());
        tvShowingCountIn.setText(bean.getApplyVisitNumber()+"人");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCommit:
                commit();
                break;
            default:
                break;
        }
    }

    private void commit() {
        final String inCount = etShowingCountIn.getText().toString();
        final String outCount = etShowingCountOut.getText().toString();
        String url;
        switch (bean.getPermitStatus()) {
            case ShowHouseBean.PERMIT_STATUE_SHOWING: // 待确认
                if (outCount.isEmpty()) {
                    QBLToast.show(R.string.err_text_no_people);
                    return;
                } else if(Integer.parseInt(outCount)<1){
                    QBLToast.show(getString(R.string.err_text_zero));
//                    return;
                }else if(Integer.parseInt(outCount)>Integer.parseInt(inCount)){
                    QBLToast.show(getString(R.string.err_text_out_bigger_in));
                    return;
                }
                url = Const.serviceMethod.VISIT_PERMISSSION_END;
                break;
            default: // 未看房
                if (inCount.isEmpty()) {
                    QBLToast.show(R.string.err_text_no_people);
                    return;
                } else if(Integer.parseInt(inCount)<1){
                    QBLToast.show(R.string.err_text_zero);
                    return;
                }
                url = Const.serviceMethod.VISIT_PERMISSSION_UPDATE;
                break;
        }
        VolleyStringRequest request = new VolleyStringRequest(Request.Method.POST, url, new VolleyResponseListener() {
            @Override
            public void handleJson(com.alibaba.fastjson.JSONObject json) {
                super.handleJson(json);
                int resultCode = json.getIntValue("resultCode");
                String message = json.getString("message");
                if (resultCode == 1) {
                    setResult(RESULT_OK);
                    new AlertDialog.Builder(getThis()).setCancelable(false)
                            .setTitle(message)
                            .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();
                } else {
                    QBLToast.show(message);
                }
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
                params.put("id", String.valueOf(bean.getId()));
                switch (bean.getPermitStatus()) {
                    case ShowHouseBean.PERMIT_STATUE_SHOWING:
                        params.put("realVisitNumber",outCount);
                        break;
                    case ShowHouseBean.PERMIT_STATUE_WAIT:
                        params.put("applyVisitNumber", inCount);
                        break;
                }
                return params;
            }
        };
        getThis().getVolleyRequestQueue().add(request);
    }
}
