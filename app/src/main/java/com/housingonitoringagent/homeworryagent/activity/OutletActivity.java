package com.housingonitoringagent.homeworryagent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.pages.ConversationListFragment;
import com.housingonitoringagent.homeworryagent.pages.HouseFragment;
import com.housingonitoringagent.homeworryagent.pages.RecordFragment;
import com.housingonitoringagent.homeworryagent.views.XAdapter;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * HomeWorry
 * Created by Administrator on 2016/3/2 0002.
 */
public class OutletActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    private static final int CONTENT_TYPE_COMMENT = 0;
    private static final int CONTENT_TYPE_REPLY = 1;
    // 适配器最最评论显示数目
    private static final int maxContentSize = 4;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabMain)
    TabLayout tabMain;
    @Bind(R.id.vpMain)
    ViewPager vpMain;
//    @Bind(R.id.rvMain)
//    RecyclerView rvMain;
//    @Bind(R.id.refreshView)
//    BGARefreshLayout refreshView;

//    private XAdapter<NeighbourListBean.NeighbourMessagesBean.ContentBean> adapter;
//        private RecyclerView.Adapter adapter;
    private XAdapter<String> adapter;
    private HouseFragment fragmentBuy, fragmentRent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.title_order_list);
//        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initViews();
        initDate();
    }

    private void initViews() {

        vpMain.setAdapter(new HousePagerAdapter(getSupportFragmentManager()));
        tabMain.setupWithViewPager(vpMain);

//        rvMain.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new HomeNeighbourAdapter(this, new ArrayList<NeighbourListBean.NeighbourMessagesBean.ContentBean>());
//        BGARefreshLayoutBuilder.init(this, refreshView, true);


   /*     List<NeighbourListBean.NeighbourMessagesBean.ContentBean> list = new ArrayList<>();
        final ViewGroup.LayoutParams tvParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        adapter = new XAdapter<NeighbourListBean.NeighbourMessagesBean.ContentBean>(this, list, R.layout.item_home_neighbor) {
            @Override
            protected int getItemType(NeighbourListBean.NeighbourMessagesBean.ContentBean item) {
                List<NeighbourListBean.NeighbourMessagesBean.ContentBean.NeighbourCommentsBean> comments = item.getNeighbourComments();
                int count = 0;
                for (int i = 0; i < comments.size(); i++) {
                    count++;
                    List<NeighbourListBean.NeighbourMessagesBean.ContentBean.NeighbourCommentsBean.NeighbourReplysBean> replies = comments.get(i).getNeighbourReplys();
                    for (int j = 0; j < replies.size(); j++) {
                        if (replies.get(j).getId() != null && !replies.get(j).getId().isEmpty()) {
                            count++;
                        }
                    }
                }
                return count;
            }

            @Override
            public void creatingHolder(final XAdapter.CustomHolder holder, final List<NeighbourListBean.NeighbourMessagesBean.ContentBean> dataList, final int adapterPos, int viewType) {
                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.comment_btn:
                                inputContentWindow = new InputContentWindow<String>(getThis(), dataList.get(holder.getAdapterPosition()).getId(), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        switch (v.getId()) {
                                            case R.id.send:
                                                String content = inputContentWindow.getEtContent().getText().toString();
                                                String id = ((String) inputContentWindow.getData());
                                                sendComment(id, CONTENT_TYPE_COMMENT, content);
//                                                inputContentWindow.dismiss();
                                                Toast.makeText(getThis(), "content:" + content + "\nid:" + id, Toast.LENGTH_SHORT).show();
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                });
                                inputContentWindow.showAtLocation(getThis().findViewById(R.id.llMain), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                                inputContentWindow.getEtContent().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        setInputMethodVisible(inputContentWindow.getEtContent(), true);
                                    }
                                }, 10);
                                break;
                            case R.id.delete:
                                String id = dataList.get(holder.getAdapterPosition()).getId();
                                Toast.makeText(getThis(), "id:" + id, Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.more_comment:
                                dataList.get(holder.getAdapterPosition()).setShowMore(!dataList.get(holder.getAdapterPosition()).isShowMore());
                                notifyItemChanged(holder.getAdapterPosition());
                                break;
                            default:
                                break;
                        }
                    }
                };
                holder.getView(R.id.neighbor_head);
                holder.getView(R.id.neighbor_name);
                holder.getView(R.id.content);
                holder.getView(R.id.date);
                NineGridlayout glNine = holder.getView(R.id.photos);

                holder.getView(R.id.delete).setOnClickListener(clickListener);
                holder.getView(R.id.comment_btn).setOnClickListener(clickListener);
                holder.getView(R.id.more_comment).setOnClickListener(clickListener);

                LinearLayout ll = holder.getView(R.id.llComments);

                View.OnClickListener clickListenerTextView = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CommentForTextView item = (CommentForTextView) v.getTag();
                        if (item.getUserId().equals(User.getUserId())) {
                            return;
                        }
                        inputContentWindow = new InputContentWindow<>(getThis(), item, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String content = inputContentWindow.getEtContent().getText().toString();
                                String id = ((CommentForTextView) inputContentWindow.getData()).getNcId();

                                sendComment(id, CONTENT_TYPE_REPLY, content);
                                Toast.makeText(getThis(), "content:" + content + "\nid:" + id, Toast.LENGTH_SHORT).show();
//                                inputContentWindow.dismiss();
                            }
                        });
                        inputContentWindow.showAtLocation(getThis().findViewById(R.id.llMain), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                        inputContentWindow.getEtContent().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setInputMethodVisible(inputContentWindow.getEtContent(), true);
                            }
                        }, 10);
                    }
                };

                List<TextView> tvList = new ArrayList<>();
                for (int i = 0; i < viewType; i++) {
                    TextView tv = new TextView(getThis());
                    tv.setLayoutParams(tvParams);
                    tv.setVisibility(View.GONE);
                    ll.addView(tv);
                    tvList.add(tv);
                    tv.setOnClickListener(clickListenerTextView);
                }
                ll.setTag(tvList);
            }

            @Override
            public void bindingHolder(CustomHolder holder, List<NeighbourListBean.NeighbourMessagesBean.ContentBean> dataList, int pos) {

                SimpleDraweeView ivHead = holder.getView(R.id.neighbor_head);
                TextView tvName = holder.getView(R.id.neighbor_name);
                TextView tvContent = holder.getView(R.id.content);
                NineGridlayout nglPhotos = holder.getView(R.id.photos);
                TextView tvDate = holder.getView(R.id.date);
                TextView tvDelete = holder.getView(R.id.delete);
//                ImageView ivComment = holder.getView(R.id.comment_btn);
                ImageView ivMoreComment = holder.getView(R.id.more_comment);
                LinearLayout llComments = holder.getView(R.id.llComments);

                NeighbourListBean.NeighbourMessagesBean.ContentBean item = dataList.get(pos);
//                String item = dataList.get(pos);

                ivHead.setImageURI(Uri.parse(Const.SERVER + item.getUser().getAvatar()));
                tvContent.setText(item.getNmContent());
                tvName.setText(item.getUser().getName());
                tvDate.setText(DateUtil.DATE_FORMAT_SIMPLE.format(new Date(item.getNmDatetime())));

                if (User.getUserId() != null && User.getUserId().equals(item.getUser().getId())) {
                    tvDelete.setVisibility(View.VISIBLE);
                } else {
                    tvDelete.setVisibility(View.GONE);
                }
                final List<String> photoUrlList = new ArrayList<>();
//                final String[] photoUrls = new String[item.getNeighbourPictures().size()];
//                for (int i = 0; i < photoUrls.length; i++) {
//                    photoUrls[i] = item.getNeighbourPictures().get(i).getUrl();
//                }
                for (NeighbourListBean.NeighbourMessagesBean.ContentBean.NeighbourPicturesBean pic : item.getNeighbourPictures()) {
                    photoUrlList.add(pic.getUrl());
                }
//        new String[item.getNeighbourPictures().size()];
                String[] photoUrls = photoUrlList.toArray(new String[0]);
                if (photoUrls.length != 0) {
                    nglPhotos.setImagesData(photoUrls, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int itemPos = (int) v.getTag();
                            Intent intent = new Intent(getThis(), ImageBrowserActivity.class);
                            intent.putExtra("position", itemPos);
                            intent.putStringArrayListExtra("images", (ArrayList<String>) photoUrlList);
                            startActivity(intent);
                        }
                    });
                    nglPhotos.setVisibility(View.VISIBLE);
                } else {
                    nglPhotos.setVisibility(View.GONE);
                }

                List<CommentForTextView> commentList = new ArrayList<>();
                List<NeighbourListBean.NeighbourMessagesBean.ContentBean.NeighbourCommentsBean> comments = item.getNeighbourComments();
                for (int i = 0; i < comments.size(); i++) {
                    NeighbourListBean.NeighbourMessagesBean.ContentBean.NeighbourCommentsBean aComment = comments.get(i);
                    commentList.add(new CommentForTextView(aComment.getUser().getName(), aComment.getUser().getId(), "", aComment.getId(), aComment.getNcContent()));
                    List<NeighbourListBean.NeighbourMessagesBean.ContentBean.NeighbourCommentsBean.NeighbourReplysBean> replies = aComment.getNeighbourReplys();
                    for (int j = 0; j < replies.size(); j++) {
                        NeighbourListBean.NeighbourMessagesBean.ContentBean.NeighbourCommentsBean.NeighbourReplysBean aReply = replies.get(j);
                        if (aReply.getId() != null && !aReply.getId().isEmpty()) {
                            commentList.add(new CommentForTextView(aReply.getUser().getName(), aReply.getUserId(), aReply.getReplyName(), aComment.getId(), aReply.getNrContent()));
                        }
                    }
                }

                if (llComments.getTag() != null && llComments.getTag() instanceof List) {
                    List tvList = (List) llComments.getTag();
                    for (int i = 0; i < tvList.size(); i++) {
                        if (tvList.get(i) instanceof TextView) {
                            TextView tv = (TextView) tvList.get(i);
                            if (i >= commentList.size() || (!item.isShowMore() && i >= maxContentSize)) {
                                tv.setVisibility(View.GONE);
                            } else {
                                tv.setText(commentList.get(i).getHtmlString());
                                tv.setBackgroundColor(0x00000000);
                                int pad = UIUtils.dip2px(getThis(), 8);
                                tv.setPadding(pad, pad / 2, pad, pad / 2);
                                tv.setTag(commentList.get(i));
                                tv.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    ivMoreComment.setVisibility((!item.isShowMore() && commentList.size() > maxContentSize) ? View.VISIBLE : View.GONE);
                }
            }
        };
        rvMain.setAdapter(adapter);*/
//        ivPost.setOnClickListener(this);
//        ivBack.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.home_neighbour, menu);
        return true;
    }

    private void initDate() {

  /*      try {
            NeighbourListBean bean = JSON.parseObject(User.getNeighbours().toString(), NeighbourListBean.class);
            lastPage = bean.getNeighbourMessages().isLastPage();
            villages = bean.getVillageNames();
            adapter.setDataList(bean.getNeighbourMessages().getContent());
        } catch (Exception e) {
            e.printStackTrace();
            getNeighbors(++pageIndex, 10, getSelectedVillage(selectedVillageIndex), Const.RefreshType.REFRESH);
        }*/
    }


    /**
     * 获取小区

     * @param page        页码
     * @param villageName 小区id
     * @param refreshType 状态
     */
    private void getNeighbors(final int page, final int pageSize, final String villageName, final int refreshType) {
    /*    VolleyStringRequest request = new VolleyStringRequest(Request.Method.POST, Const.serviceMethod.NEIGHBOR_LIST, new VolleyResponseListener() {
            @Override
            public void handleJson(JSONObject json) {
                super.handleJson(json);
                int resultCode = json.getIntValue("resultCode");
                String message = json.getString("message");
                if (resultCode == 1) {
                    NeighbourListBean bean = JSON.parseObject(json.toString(), NeighbourListBean.class);
                    lastPage = bean.getNeighbourMessages().isLastPage();
                    villages = bean.getVillageNames();
                    User.setNeighbours(json.toString());
                    switch (refreshType) {
                        case Const.RefreshType.REFRESH:
//                            refreshView.endRefreshing();
//                            adapter.clearData();
                            if (bean.getNeighbourMessages().getContent().size() > 0) {
                                adapter.setDataList(bean.getNeighbourMessages().getContent());
                            }
                            break;
                        case Const.RefreshType.LOAD:
//                            refreshView.endLoadingMore();
                            pageIndex++;
                            if (bean.getNeighbourMessages().getContent().size() > 0) {
//                                adapter.addAdapterData(bean.getNeighbourMessages().getContent());
                            }
                            break;
                    }
                } else {
                    QBLToast.show(message);
                    switch (refreshType) {
                        case Const.RefreshType.REFRESH:
//                            refreshView.endRefreshing();
                            break;
                        case Const.RefreshType.LOAD:
//                            refreshView.endLoadingMore();
                            break;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                QBLToast.show(R.string.network_exception);
                switch (refreshType) {
                    case Const.RefreshType.REFRESH:
//                        refreshView.endRefreshing();
                        break;
                    case Const.RefreshType.LOAD:
//                        refreshView.endLoadingMore();
                        break;
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = super.getParams();
                params.put("page", String.valueOf(page));
                if (!villageName.isEmpty()) {
                    params.put("villageName", villageName);
                }
                params.put("pageSize", String.valueOf(pageSize));
                return params;
            }
        };
        getVolleyRequestQueue().add(request);*/
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
//        getNeighbors(++pageIndex, 10, villages.get(selectedVillageIndex), Const.RefreshType.REFRESH);
//        if (adapter.getItemCount() > 0) {
//            getNeighbors(pageIndex, adapter.get.get(mBuildSelectIndex).getId(), villageName, Const.RefreshType.REFRESH);
//        } else {
//            refreshView.endRefreshing();
//            QBLToast.show(R.string.text_no_data);
//        }
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
//        if (!lastPage) {
//            getNeighbors(++pageIndex, 10, villages.get(selectedVillageIndex), Const.RefreshType.LOAD);
//        }
      /*  if (mVillages.size() > 0) {
            if (!lastPage) {
                getNeighbors(pageIndex, adapter.getData().get(mBuildSelectIndex).getId(), villageName, Const.RefreshType.LOAD);
            }
        } else {
            QBLToast.show(R.string.text_no_data);
        }
    else

    {
        refreshView.endRefreshing();
        QBLToast.show(R.string.text_no_village_tip);
    }*/

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.action_post:
//                Intent intent = new Intent(this, HomeNeighbourCommitActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_NEW_COMMENT);
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ivPost:
//                Intent intent = new Intent(this, HomeNeighbourCommitActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_NEW_COMMENT);
//                getNeighbors(0, 10, getSelectedVillage(selectedVillageIndex), Const.RefreshType.REFRESH);
//                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent();
//        if (position == ImageBean.bmp.size()) {
//            intent.setClass(getThis(), PicSelectActivity.class);
//            startActivityForResult(intent, 0);
//        } else {
//            intent.setClass(getThis(), ImageBrowserActivity.class);
//            intent.putExtra("isdel", false);
//            intent.putExtra("position", position);
//            startActivity(intent);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
          /*  case REQUEST_CODE_NEW_COMMENT:
                if (resultCode == RESULT_OK) {
                    if (data != null && data.getStringExtra("data") != null) {
                        String json = data.getStringExtra("data");
//                        NeighbourListBean.NeighbourMessagesBean.ContentBean bean = JSON.parseObject(json, NeighbourListBean.NeighbourMessagesBean.ContentBean.class);
//                        adapter.addItem(0, bean);
                    }
                }
                break;*/
            default:
                break;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class HousePagerAdapter extends FragmentPagerAdapter {
        String[] titles = new String[]{"二手房", "租房"};

        public HousePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            setupTabIcons();
            switch (position) {
                case 0:
                    if (fragmentBuy == null) {
                        fragmentBuy = new HouseFragment(HouseFragment.TYPE_BUY);
                    }
                    return fragmentBuy;
                case 1:
                    if (fragmentRent == null) {
                        fragmentRent = new HouseFragment(HouseFragment.TYPE_RENT);
//                        fragmentRent.setType(HouseFragment.TYPE_RENT);
                    }
                    return fragmentRent;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
           return titles[position];
        /*    String title ="";
            Drawable image;
            SpannableString sb = new SpannableString(title + "   ");
            switch (position) {
                case 0:
                    title = "消息";
                    image = getResources().getDrawable(currentPage==0? R.mipmap.icon_msg_1 : R.mipmap.icon_msg_0);
                    break;
                case 1:
                    title = "看房";
                    image = getResources().getDrawable(currentPage==1? R.mipmap.icon_showing_1 : R.mipmap.icon_showing_0);
                    break;
                case 2:
                    title = "我的";
                    image = getResources().getDrawable(currentPage==2? R.mipmap.icon_me_1 : R.mipmap.icon_me_0);
                    break;
                default:
                    image = getResources().getDrawable(R.mipmap.icon_contact_0);
                    break;
            }

            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            // Replace blank spaces with image icon
            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            sb.setSpan(imageSpan, title.length(), title.length() + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
*/
        }
    }
}
