package com.housingonitoringagent.homeworryagent.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.housingonitoringagent.homeworryagent.Const;
import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.beans.HousingAssessmentBean;
import com.housingonitoringagent.homeworryagent.utils.net.FrescoFactory;

import java.util.List;

/**
 * Created by Administrator on 2016/4/27 0027.
 */
public class HousingAssessmentAdapter extends RecyclerView.Adapter<HousingAssessmentAdapter.ViewHolder> {

    private List<HousingAssessmentBean.HouseEvaluateListEntity.ContentEntity> mData;

    public void setAdapterData(List<HousingAssessmentBean.HouseEvaluateListEntity.ContentEntity> data){
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addAdapterData(List<HousingAssessmentBean.HouseEvaluateListEntity.ContentEntity> data) {
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



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_housing_assessment, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HousingAssessmentBean.HouseEvaluateListEntity.ContentEntity entity = mData.get(position);
        holder.mName.setText(entity.getUserName());
        holder.mCompany.setText(entity.getIntermediaryCompanyName());
        holder.mTelephone.setText(entity.getUserTelephone());
        holder.mComment.setText(entity.getContent());
        holder.sdv_head.setController(FrescoFactory.newAutoPlayAnimationsDraweeController(Const.SERVER + entity.getUserAvatar(), holder.sdv_head));
    }

    @Override
    public int getItemCount() {
        return  mData == null ? 0 : mData.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mCompany;
        public TextView mTelephone;
        public TextView mComment;
        public SimpleDraweeView sdv_head;
        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mCompany = (TextView) itemView.findViewById(R.id.company);
            mTelephone = (TextView) itemView.findViewById(R.id.telephone);
            mComment = (TextView) itemView.findViewById(R.id.comment);
            sdv_head = (SimpleDraweeView) itemView.findViewById(R.id.sdv_head);
        }
    }
}
