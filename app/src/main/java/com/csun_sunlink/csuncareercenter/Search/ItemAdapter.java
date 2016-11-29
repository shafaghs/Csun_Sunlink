package com.csun_sunlink.csuncareercenter.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import com.csun_sunlink.csuncareercenter.R;

public class ItemAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public ItemAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(ItemInfo object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        ItemHolder itemHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            itemHolder = new ItemHolder();
            itemHolder.jobTitle = (TextView)row.findViewById(R.id.search_job_title);
            itemHolder.companyName = (TextView)row.findViewById(R.id.search_company_name);
            itemHolder.companyArea = (TextView)row.findViewById(R.id.event_location);
            itemHolder.postedDate = (TextView)row.findViewById(R.id.search_posted_date);
            itemHolder.jobId = (TextView)row.findViewById(R.id.search_job_id);
            row.setTag(itemHolder);
        }
        else
        {
            itemHolder =(ItemHolder) row.getTag();
        }
        ItemInfo itemInfo =(ItemInfo) this.getItem(position);
        itemHolder.jobTitle.setText(itemInfo.getJobTitle());
        itemHolder.companyName.setText(itemInfo.getCompanyName());
        itemHolder.companyArea.setText(itemInfo.getCompanyAddress());
        itemHolder.postedDate.setText(itemInfo.getPostedDate());
        itemHolder.jobId.setText(itemInfo.getJobId());
        return row;
    }

    static class ItemHolder{
        TextView jobTitle,companyName,companyArea,postedDate,jobId;
    }
}
