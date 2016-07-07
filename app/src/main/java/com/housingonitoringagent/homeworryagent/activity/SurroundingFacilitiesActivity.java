package com.housingonitoringagent.homeworryagent.activity;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyResponseListener;
import com.housingonitoringagent.homeworryagent.utils.net.VolleyStringRequest;
import com.housingonitoringagent.homeworryagent.utils.uikit.QBLToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;



/**
 * 周边设施
 * HomeWorry
 * Created by Administrator on 2016/2/22 0022.
 */
public class SurroundingFacilitiesActivity extends BaseActivity implements View.OnClickListener, AMap.OnMapClickListener,
        AMap.InfoWindowAdapter, AMap.OnMarkerClickListener, PoiSearch.OnPoiSearchListener {
    @Bind(R.id.bus)
    TextView mBusView;
    @Bind(R.id.metro)
    TextView mMetroView;
    @Bind(R.id.bank)
    TextView mBankView;
    @Bind(R.id.education)
    TextView mEducationView;
    @Bind(R.id.hospital)
    TextView mHospitalView;
    @Bind(R.id.casual)
    TextView mCasualView;
    @Bind(R.id.map_view)
    MapView mMapView;
    @Bind(R.id.toolbar)
    Toolbar mToolBar;
    @Bind(R.id.poi_detail)
    RelativeLayout mPoiDetail;
    @Bind(R.id.poi_name)
    TextView mPoiName;
    @Bind(R.id.poi_address)
    TextView mPoiAddress;
    //    private LocationService mLocationService;
    private double mLatitude = 0;
    private double mLongitude = 0;


    private String mLatitudeString;
    private String mLongitudeString;
    private AMap mAMap;
    private PoiResult poiResult; // poi返回的结果
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private LatLonPoint lp;
    private Marker locationMarker; // 选择的点
    private Marker detailMarker;
    private Marker mlastMarker;
    private PoiSearch poiSearch;
    private myPoiOverlay poiOverlay;// poi图层
    private List<PoiItem> poiItems;// poi数据
    private String city = "东莞市";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surrounding_facilities);
        ButterKnife.bind(this);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMapView.onCreate(savedInstanceState);
        getBunlde();

    }

    private void getBunlde() {
        mLatitudeString = getIntent().getStringExtra(getString(R.string.extra_latitude));
        mLongitudeString = getIntent().getStringExtra(getString(R.string.extra_longitude));
        if (!mLatitudeString.equals("") && !mLongitudeString.equals("")) {
            mLongitude = Double.valueOf(mLongitudeString);
            mLatitude = Double.valueOf(mLatitudeString);
        }
        showProgressDialog(getString(R.string.wait_a_few_times));
        StringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.AMAP,
                new VolleyResponseListener() {
                    @Override
                    public void handleJson(JSONObject json) {
                        super.handleJson(json);
                        dismissProgressDialog();
                        int status = json.getIntValue("status");
                        switch (status){
                            case 1:
                               String[] locations = json.getString("locations").split(",");
                                mLongitude = Double.valueOf(locations[0]);
                                mLatitude = Double.valueOf(locations[1]);
                                lp = new LatLonPoint(mLatitude,mLongitude);
                                setListeners();
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
                params.put("key", getString(R.string.gd_key));
                params.put("locations", mLongitude+","+mLatitude);
                params.put("coordsys", "baidu");
                params.put("output", "json");
                return params;
            }
        };
        getVolleyRequestQueue().add(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setListeners() {
        mBusView.setOnClickListener(this);
        mBankView.setOnClickListener(this);
        mMetroView.setOnClickListener(this);
        mBankView.setOnClickListener(this);
        mEducationView.setOnClickListener(this);
        mHospitalView.setOnClickListener(this);
        mCasualView.setOnClickListener(this);

        init();
//        LatLng ll = new LatLng(mLatitude,mLongitude);
//        mBaiduMap = mMapView.getMap();
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//        BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.mipmap.base_location);
//        OverlayOptions oo = new MarkerOptions().icon(bd).position(ll);
//        mBaiduMap.addOverlay(oo);
//        MapStatus.Builder builder = new MapStatus.Builder();
//        builder.target(ll).zoom(19.0f).build();
//        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    private void init() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
            mAMap.setOnMapClickListener(this);
            mAMap.setOnMarkerClickListener(this);
            mAMap.setInfoWindowAdapter(this);
            locationMarker = mAMap.addMarker(new MarkerOptions()
                    .anchor(0.5f, 0.5f)
                    .icon(BitmapDescriptorFactory
                            .fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.point4)))
                    .position(new LatLng(lp.getLatitude(), lp.getLongitude())));
        }
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lp.getLatitude(), lp.getLongitude()), 18));
    }



    private void setDefaultTextColor() {
        int defaultColorId = getResources().getColor(android.R.color.black);
        mBusView.setTextColor(defaultColorId);
        mMetroView.setTextColor(defaultColorId);
        mBankView.setTextColor(defaultColorId);
        mEducationView.setTextColor(defaultColorId);
        mHospitalView.setTextColor(defaultColorId);
        mCasualView.setTextColor(defaultColorId);
    }

    private void searchPoiKeyWord(TextView v) {
        int blueColorId = getResources().getColor(android.R.color.holo_blue_light);
        v.setTextColor(blueColorId);
        doSearchQuery((String) v.getText());
//        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption();
//        nearbySearchOption.location(new LatLng(mLatitude,mLongitude));
//        nearbySearchOption.keyword((String)v.getText());
//        nearbySearchOption.radius(1000);
//        nearbySearchOption.pageNum(1);
//        mPoiSearch.searchNearby(nearbySearchOption);
    }


    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery(String keyWord) {
        currentPage = 0;
        query = new PoiSearch.Query(keyWord, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        if (lp != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(lp, 5000, true));//
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        whetherToShowDetailInfo(false);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }


    @Override
    public void onClick(View v) {
        setDefaultTextColor();
        switch (v.getId()) {
            case R.id.bus:
                searchPoiKeyWord(mBusView);
                break;
            case R.id.metro:
                searchPoiKeyWord(mMetroView);
                break;
            case R.id.education:
                searchPoiKeyWord(mEducationView);
                break;
            case R.id.bank:
                searchPoiKeyWord(mBankView);
                break;
            case R.id.hospital:
                searchPoiKeyWord(mHospitalView);
                break;
            case R.id.casual:
                searchPoiKeyWord(mCasualView);
                break;
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        whetherToShowDetailInfo(false);
        if (mlastMarker != null) {
            resetlastmarker();
        }
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getObject() != null) {
            whetherToShowDetailInfo(true);
            try {
                PoiItem mCurrentPoi = (PoiItem) marker.getObject();
                if (mlastMarker == null) {
                    mlastMarker = marker;
                } else {
                    // 将之前被点击的marker置为原来的状态
                    resetlastmarker();
                    mlastMarker = marker;
                }
                detailMarker = marker;
                detailMarker.setIcon(BitmapDescriptorFactory
                        .fromBitmap(BitmapFactory.decodeResource(
                                getResources(),
                                R.mipmap.poi_marker_pressed)));
                setPoiItemDisplayContent(mCurrentPoi);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }else {
            whetherToShowDetailInfo(false);
            resetlastmarker();
        }
        return true;
    }

    @Override
    public void onPoiSearched(PoiResult result, int i) {
        if (i == 1000) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    if (poiItems != null && poiItems.size() > 0) {
                        //清除POI信息显示
                        whetherToShowDetailInfo(false);
                        //并还原点击marker样式
                        if (mlastMarker != null) {
                            resetlastmarker();
                        }
                        //清理之前搜索结果的marker
                        if (poiOverlay !=null) {
                            poiOverlay.removeFromMap();
                        }
                        mAMap.clear();
                        poiOverlay = new myPoiOverlay(mAMap, poiItems);
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();

                        mAMap.addMarker(new MarkerOptions()
                                .anchor(0.5f, 0.5f)
                                .icon(BitmapDescriptorFactory
                                        .fromBitmap(BitmapFactory.decodeResource(
                                                getResources(), R.mipmap.point4)))
                                .position(new LatLng(lp.getLatitude(), lp.getLongitude())));

                        mAMap.addCircle(new CircleOptions()
                                .center(new LatLng(lp.getLatitude(),
                                        lp.getLongitude())).radius(5000)
                                .strokeColor(Color.BLUE)
                                .fillColor(Color.argb(50, 1, 1, 1))
                                .strokeWidth(2));
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        QBLToast.show(getString(
                                R.string.no_result));
                    } else {
                        QBLToast.show(getString(
                                R.string.no_result));
                    }
                }
            } else {
                QBLToast
                        .show(getString( R.string.no_result));
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    private void whetherToShowDetailInfo(boolean isToShow) {
        if (isToShow) {
            mPoiDetail.setVisibility(View.VISIBLE);

        } else {
            mPoiDetail.setVisibility(View.GONE);

        }
    }

    // 将之前被点击的marker置为原来的状态
    private void resetlastmarker() {
        int index = poiOverlay.getPoiIndex(mlastMarker);
        if (index < 10) {
            mlastMarker.setIcon(BitmapDescriptorFactory
                    .fromBitmap(BitmapFactory.decodeResource(
                            getResources(),
                            markers[index])));
        }else {
            mlastMarker.setIcon(BitmapDescriptorFactory.fromBitmap(
                    BitmapFactory.decodeResource(getResources(), R.mipmap.marker_other_highlight)));
        }
        mlastMarker = null;

    }

    private void setPoiItemDisplayContent(final PoiItem mCurrentPoi) {
        mPoiName.setText(mCurrentPoi.getTitle());
        mPoiAddress.setText(mCurrentPoi.getSnippet());
    }

    private class myPoiOverlay {
        private AMap mamap;
        private List<PoiItem> mPois;
        private ArrayList<Marker> mPoiMarks = new ArrayList<Marker>();

        public myPoiOverlay(AMap amap, List<PoiItem> pois) {
            mamap = amap;
            mPois = pois;
        }

        /**
         * 添加Marker到地图中。
         *
         * @since V2.1.0
         */
        public void addToMap() {
            for (int i = 0; i < mPois.size(); i++) {
                Marker marker = mamap.addMarker(getMarkerOptions(i));
                PoiItem item = mPois.get(i);
                marker.setObject(item);
                mPoiMarks.add(marker);
            }
        }

        /**
         * 去掉PoiOverlay上所有的Marker。
         *
         * @since V2.1.0
         */
        public void removeFromMap() {
            for (Marker mark : mPoiMarks) {
                mark.remove();
            }
        }

        /**
         * 移动镜头到当前的视角。
         *
         * @since V2.1.0
         */
        public void zoomToSpan() {
            if (mPois != null && mPois.size() > 0) {
                if (mamap == null)
                    return;
                LatLngBounds bounds = getLatLngBounds();
                mamap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            }
        }

        private LatLngBounds getLatLngBounds() {
            LatLngBounds.Builder b = LatLngBounds.builder();
            for (int i = 0; i < mPois.size(); i++) {
                b.include(new LatLng(mPois.get(i).getLatLonPoint().getLatitude(),
                        mPois.get(i).getLatLonPoint().getLongitude()));
            }
            return b.build();
        }

        private MarkerOptions getMarkerOptions(int index) {
            return new MarkerOptions()
                    .position(
                            new LatLng(mPois.get(index).getLatLonPoint()
                                    .getLatitude(), mPois.get(index)
                                    .getLatLonPoint().getLongitude()))
                    .title(getTitle(index)).snippet(getSnippet(index))
                    .icon(getBitmapDescriptor(index));
        }

        protected String getTitle(int index) {
            return mPois.get(index).getTitle();
        }

        protected String getSnippet(int index) {
            return mPois.get(index).getSnippet();
        }

        /**
         * 从marker中得到poi在list的位置。
         *
         * @param marker 一个标记的对象。
         * @return 返回该marker对应的poi在list的位置。
         * @since V2.1.0
         */
        public int getPoiIndex(Marker marker) {
            for (int i = 0; i < mPoiMarks.size(); i++) {
                if (mPoiMarks.get(i).equals(marker)) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * 返回第index的poi的信息。
         *
         * @param index 第几个poi。
         * @return poi的信息。poi对象详见搜索服务模块的基础核心包（com.amap.api.services.core）中的类 <strong><a href="../../../../../../Search/com/amap/api/services/core/PoiItem.html" title="com.amap.api.services.core中的类">PoiItem</a></strong>。
         * @since V2.1.0
         */
        public PoiItem getPoiItem(int index) {
            if (index < 0 || index >= mPois.size()) {
                return null;
            }
            return mPois.get(index);
        }

        protected BitmapDescriptor getBitmapDescriptor(int arg0) {
            if (arg0 < 10) {
                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory.decodeResource(getResources(), markers[arg0]));
                return icon;
            } else {
                BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory.decodeResource(getResources(), R.mipmap.marker_other_highlight));
                return icon;
            }
        }

    }

    private int[] markers = {R.mipmap.poi_marker_1,
            R.mipmap.poi_marker_2,
            R.mipmap.poi_marker_3,
            R.mipmap.poi_marker_4,
            R.mipmap.poi_marker_5,
            R.mipmap.poi_marker_6,
            R.mipmap.poi_marker_7,
            R.mipmap.poi_marker_8,
            R.mipmap.poi_marker_9,
            R.mipmap.poi_marker_10
    };
}