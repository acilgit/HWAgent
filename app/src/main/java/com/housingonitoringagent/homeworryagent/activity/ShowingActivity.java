package com.housingonitoringagent.homeworryagent.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowingActivity extends BaseActivity {

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
    @Bind(R.id.btnLogin)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.title_order_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
