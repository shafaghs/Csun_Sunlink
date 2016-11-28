package com.csun_sunlink.csuncareercenter.Fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import com.csun_sunlink.csuncareercenter.R;

public class EventAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public EventAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(EventInfo object) {
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
            itemHolder.eventTitle = (TextView)row.findViewById(R.id.search_job_title);
            itemHolder.eventDate= (TextView)row.findViewById(R.id.search_company_name);
            itemHolder.eventTime = (TextView)row.findViewById(R.id.search_company_area);
            itemHolder.eventInfo = (TextView)row.findViewById(R.id.search_posted_date);
            itemHolder.eventId = (TextView)row.findViewById(R.id.search_job_id);
            row.setTag(itemHolder);
        }
        else
        {
            itemHolder =(ItemHolder) row.getTag();
        }
        EventInfo itemInfo =(EventInfo) this.getItem(position);
        itemHolder.eventTitle.setText(itemInfo.geteventTitle());
        itemHolder.eventDate.setText(itemInfo.geteventDate());
        itemHolder.eventTime.setText(itemInfo.geteventLocation());
        itemHolder.eventInfo.setText(itemInfo.geteventInfo());
        itemHolder.eventId.setText(itemInfo.geteventId());
        return row;
    }

    static class ItemHolder{
        TextView eventTitle,eventDate,eventTime,eventInfo,eventId;
    }
}