package com.housingonitoringagent.homeworryagent.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.activity.EstateAnnouncementInfoActivity;
import com.housingonitoringagent.homeworryagent.beans.AnnouncementListBean;
import com.housingonitoringagent.homeworryagent.extents.BaseActivity;
import com.housingonitoringagent.homeworryagent.utils.DateUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/4/13 0013.
 */
public class EstateAnnouncementAdapter extends RecyclerView.Adapter<EstateAnnouncementAdapter.ViewHolder> {

    private Activity activity;
    private List<AnnouncementListBean.ContentBean.AnnouncementBean.ContentInfoBean> mData;

    public EstateAnnouncementAdapter(Activity activity){
        this.activity=activity;
    }

    public void setAdapterData(List<AnnouncementListBean.ContentBean.AnnouncementBean.ContentInfoBean> data){
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addAdapterData(List<AnnouncementListBean.ContentBean.AnnouncementBean.ContentInfoBean> data) {
        if (data == null) {
            return;
        }
        if (mData == null) {
            setAdapterData(data);
        } else {
            int start  = mData.size();
            int count = data.size();

            mData.addAll(data);
            notifyItemRangeInserted(start, count);
        }
    }

    public AnnouncementListBean.ContentBean.AnnouncementBean.ContentInfoBean getContentEntity(int position) {
        return mData.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estate_announcement, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AnnouncementListBean.ContentBean.AnnouncementBean.ContentInfoBean entity = mData.get(position);
        holder.title.setText(entity.getTitle());
        holder.timeText.setText(DateUtil.parseTimeMillis(entity.getPublisherTime()));
        switch (entity.getContentSorted()){
            case 1:
                holder.contentSorted.setText("[通知公告]");
                break;
            case 2:
                holder.contentSorted.setText("[政策法规]");
                break;
            case 3:
                holder.contentSorted.setText("[协会信息]");
                break;
        }
        holder.itemView.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewHolder holder = (ViewHolder) v.getTag();
            final AnnouncementListBean.ContentBean.AnnouncementBean.ContentInfoBean ContentEntity = getContentEntity(holder.getAdapterPosition());
            ((BaseActivity) activity).start(EstateAnnouncementInfoActivity.class, new BaseActivity.BaseIntent() {
                @Override
                public void setIntent(Intent intent) {
                    intent.putExtra(activity.getString(R.string.extra_id), ContentEntity.getArticleId());
                }
            });
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView contentSorted;
        public TextView title;
        public TextView timeText;
        public ViewHolder(View itemView) {
            super(itemView);
            contentSorted = (TextView) itemView.findViewById(R.id.contentSorted);
            title = (TextView) itemView.findViewById(R.id.title);
            timeText = (TextView) itemView.findViewById(R.id.timeText);

            itemView.setOnClickListener(onClickListener);
        }
    }
}
