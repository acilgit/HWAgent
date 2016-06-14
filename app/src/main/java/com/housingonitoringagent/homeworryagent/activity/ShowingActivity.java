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

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.drawee.view.SimpleDraweeView;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.beans.ShowHouseBean;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowingActivity extends BaseActivity implements View.OnClickListener {

    public final static int PERMIT_STATUS_END = 0;
    public final static int PERMIT_STATUS_START = 2;

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
        init();
    }

    private void init() {
        switch (bean.getPermitStatus()) {
            case PERMIT_STATUS_END: // 待确认
                trShowingIn.setVisibility(View.GONE);
                trShowingOut1.setVisibility(View.VISIBLE);
                trShowingOut2.setVisibility(View.VISIBLE);
                break;
            case PERMIT_STATUS_START: // 未看房
                trShowingIn.setVisibility(View.VISIBLE);
                trShowingOut1.setVisibility(View.GONE);
                trShowingOut2.setVisibility(View.GONE);
                break;
            default:
                finish();
                break;
        }
        sivHead.setImageURI(Uri.parse(bean.getAvatar()));
        tvName.setText(bean.getName());
        tvCompany.setText(bean.getAgentName());
        tvOutlet.setText(bean.getStoreName());
        tvVillage.setText(bean.getVillage_name());
        tvBuildingNo.setText(bean.getHouse_number());
        tvShowingCountIn.setText(bean.getApplyVisitNumber());
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
        String url;
        switch (bean.getPermitStatus()) {
            case PERMIT_STATUS_END: // 待确认
                url = Const.serviceMethod.VISIT_PERMISSSION_END;
                break;
            default: // 未看房
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
                    new AlertDialog.Builder(getThis()).setCancelable(false)
                            .setTitle("message")
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
                    case PERMIT_STATUS_END:
                        params.put("realVisitNumber", String.valueOf(etShowingCountOut));
                        break;
                    case PERMIT_STATUS_START:
                        params.put("applyVisitNumber", String.valueOf(etShowingCountIn));
                        break;
                }
                return params;
            }
        };
        getThis().getVolleyRequestQueue().add(request);
    }
}
