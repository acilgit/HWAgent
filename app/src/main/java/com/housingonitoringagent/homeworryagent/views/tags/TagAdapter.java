package com.housingonitoringagent.homeworryagent.views.tags;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.housingonitoringagent.homeworryagent.R;

import java.util.List;

/**
 * Created by XY on 2016/6/21.
 */
public class TagAdapter extends BaseAdapter {
    private List<String> mDataList;

    public void setDataList(List<String> datas) {
        this.mDataList = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tags, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvLabel = (TextView) convertView.findViewById(R.id.tvTag);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvLabel.setText(mDataList.get(position));
        return convertView;
    }

    class ViewHolder {
        public TextView tvLabel;
    }
}
