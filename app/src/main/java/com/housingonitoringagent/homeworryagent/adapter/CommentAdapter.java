package com.housingonitoringagent.homeworryagent.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.housingonitoringagent.homeworryagent.R;
import com.housingonitoringagent.homeworryagent.beans.CommentListBean;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/14 0014.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>  {

    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 3;// 默认展示最大行数3行
    private static final int SHOW_CONTENT_NONE_STATE = 0;// 扩充
    private static final int SHRINK_UP_STATE = 1;// 收起状态
    private static final int SPREAD_STATE = 2;// 展开状态
    private static int mState = SHRINK_UP_STATE;//默认收起状态
    private Activity mActivity;

    private List<CommentListBean.ContentBean.EvaluationListBean.Bean> mData;

    public void setAdapterData(List<CommentListBean.ContentBean.EvaluationListBean.Bean> data){
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addAdapterData(List<CommentListBean.ContentBean.EvaluationListBean.Bean> data) {
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_comment, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CommentListBean.ContentBean.EvaluationListBean.Bean contentEntity = mData.get(position);
//            holder.mShowMore.setTag(holder);
        holder.mName.setText(contentEntity.getCreator());
        holder.expandableTextView.setText(contentEntity.getContent());
        holder.mTime.setText(contentEntity.getPublishTime());
//        holder.mContent.getLineCount();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

//    View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//                 ViewHolder holder = (ViewHolder) v.getTag();
//                 if (mState == SPREAD_STATE) {
//                     holder.mContent.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
//                     holder.mContent.requestLayout();
//                     holder.mImageShrinkUp.setVisibility(View.GONE);
//                     holder.mImageSpread.setVisibility(View.VISIBLE);
//                     mState = SHRINK_UP_STATE;
//                 } else if (mState == SHRINK_UP_STATE) {
//                     holder.mContent.setMaxLines(Integer.MAX_VALUE);
//                     holder.mContent.requestLayout();
//                     holder.mImageShrinkUp.setVisibility(View.VISIBLE);
//                     holder.mImageSpread.setVisibility(View.GONE);
//                     mState = SPREAD_STATE;
//                 }
//        }
//    };
    protected class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mTime;
        public ExpandableTextView expandableTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView)itemView.findViewById(R.id.name);
            mTime = (TextView) itemView.findViewById(R.id.time);
            expandableTextView = (ExpandableTextView) itemView.findViewById(R.id.expand_text_view);
        }
    }
}
