package com.housingonitoringagent.homeworryagent.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.beans.HouseDealBean;
import com.housingonitoringagent.homeworryagent.beans.OperationLogBean;
import com.housingonitoringagent.homeworryagent.beans.PaymentDetailBean;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.DateUtil;
import com.housingonitoringagent.homeworryagent.utils.StringUtil;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;
import com.housingonitoringagent.homeworryagent.views.XAdapter;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public class PaymentDetailActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.tvPartAName)
    TextView tvPartAName;
    @Bind(R.id.tvPartAIdCard)
    TextView tvPartAIdCard;
    @Bind(R.id.tvPartAPhone)
    TextView tvPartAPhone;
    @Bind(R.id.tvPartBName)
    TextView tvPartBName;
    @Bind(R.id.tvPartBIdCard)
    TextView tvPartBIdCard;
    @Bind(R.id.tvPartBPhone)
    TextView tvPartBPhone;
    @Bind(R.id.tvIntermediaryName)
    TextView tvIntermediaryName;
    @Bind(R.id.tvIntermediaryPhone)
    TextView tvIntermediaryPhone;
    @Bind(R.id.tvBankAccount)
    TextView tvBankAccount;
    @Bind(R.id.tvBankName)
    TextView tvBankName;
    @Bind(R.id.tvMoney)
    TextView tvMoney;
    @Bind(R.id.tvOrderNo)
    TextView tvOrderNo;
    @Bind(R.id.rvOperationLog)
    RecyclerView rvOperationLog;//操作记录
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private HouseDealBean.ContentBean.PsBean.Content bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.text_order_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bean = (HouseDealBean.ContentBean.PsBean.Content) getIntent().getSerializableExtra(getString(R.string.extra_bean));
        showProgressDialog(getString(R.string.wait_a_few_times));
        getFundManagementById();

        tvPartAPhone.setOnClickListener(this);
        tvPartBPhone.setOnClickListener(this);
    }

    private void getFundManagementById(){
        StringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.PAYEMENT_DETAIL, new VolleyResponseListener() {
            @Override
            public void handleJson(JSONObject json) {
                dismissProgressDialog();
                super.handleJson(json);
                int result = json.getIntValue("resultCode");
                String msg = json.getString("message");
                if (result == 1){
                    JSONObject info = json.getJSONObject("content").getJSONObject("houseOldDealMap").getJSONObject("orderSell");
                    PaymentDetailBean bean = JSON.toJavaObject(info,PaymentDetailBean.class);
                    JSONArray log = json.getJSONObject("content").getJSONObject("houseOldDealMap").getJSONArray("operationLog");
                    List<OperationLogBean> logs = JSON.parseArray(log.toString(),OperationLogBean.class);
                    if (logs.size()>0) {
                        OperationLogBean titles = new OperationLogBean();
                        titles.setOperator(getString(R.string.text_operator));
                        titles.setOperatorAction(getString(R.string.text_operator_record));
                        logs.add(0, titles);
                    }
                    initViews(bean,logs);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                QBLToast.show(R.string.network_exception);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = super.getParams();
                params.put("id",bean.getId());
                return params;
            }
        };
        getVolleyRequestQueue().add(request);
    }

    private void initViews(PaymentDetailBean bean, List<OperationLogBean> logs){
        tvOrderNo.setText(bean.getOrderNo());
        tvPartAName.setText(bean.getAUserName());
        tvPartAIdCard.setText(bean.getAIdCard());
        tvPartAPhone.setText(bean.getAUserMobilephone());
        tvPartBName.setText(bean.getBUserName());
        tvPartBIdCard.setText(bean.getBIdCard());
        tvPartBPhone.setText(bean.getBUserMobilephone());
        tvIntermediaryName.setText(bean.getCUserName());
        tvIntermediaryPhone.setText(bean.getCUserMobilephone());
        tvBankAccount.setText(bean.getBankNumber());
        tvBankName.setText(bean.getBankName());
        tvMoney.setText(StringUtil.formatNumber(bean.getOrderMoney(), "#,##0.##"));
        rvOperationLog.setLayoutManager(new LinearLayoutManager(this));
//        rvOperationLog.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());

        XAdapter<OperationLogBean> adapter = new XAdapter<OperationLogBean>(getThis(), logs, R.layout.item_operation_log) {
            @Override
            public void creatingHolder(CustomHolder holder, List<OperationLogBean> dataList, int viewType) {
            }

            @Override
            public void bindingHolder(CustomHolder holder, List<OperationLogBean> dataList, int pos) {
                OperationLogBean bean = dataList.get(pos);
                String parseLongTime = DateUtil.parseTimeMillis(bean.getOperationTime());
                String createTime = DateUtil.getFormatDateString(DateUtil.stringToDate(parseLongTime));

                holder.getView(R.id.vTopLine).setVisibility(pos==0 ? View.VISIBLE : View.GONE);
                holder.setText(R.id.tvTime, pos==0 ? getString(R.string.text_time) : createTime)
                        .setText(R.id.tvOperation, bean.getOperator())
                        .setText(R.id.tvLog, bean.getOperatorAction());
            }
        };
        rvOperationLog.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvPartAPhone:
                if (tvPartAPhone.getText().toString().isEmpty()) {
                    return;
                }
                Intent intentA = new Intent(Intent.ACTION_DIAL);
                intentA.setData(Uri.parse("tel:"+ tvPartAPhone.getText().toString()));
                getThis().startActivity(intentA);
            case R.id.tvPartBPhone:
                if (tvPartBPhone.getText().toString().isEmpty()) {
                    return;
                }
                Intent intentB = new Intent(Intent.ACTION_DIAL);
                intentB.setData(Uri.parse("tel:"+ tvPartBPhone.getText().toString()));
                getThis().startActivity(intentB);
            default:
                break;
        }
    }
}
