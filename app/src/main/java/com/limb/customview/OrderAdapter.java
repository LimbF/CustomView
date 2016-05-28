package com.limb.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.limb.customview.bean.ContactsInfo;
import com.limb.customview.utils.HanziToPinyin;

import java.util.ArrayList;

/**
 * Created by JiaJi on 2015/12/4.
 */
public class OrderAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private ArrayList<ContactsInfo> cInfoData;

    private Context context;
    public OrderAdapter(Context context, ArrayList<ContactsInfo> cInfoData) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.cInfoData = cInfoData;
    }

    @Override
    public int getCount() {
        return cInfoData.size();
    }

    @Override
    public ContactsInfo getItem(int position) {
        return cInfoData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_express, parent, false);
            holder = new ViewHolder();
            holder.tvLetter = (TextView)convertView.findViewById(R.id.tvLetter_item_express);
            holder.tvCompanyName = (TextView)convertView.findViewById(R.id.tvCompanyName_item_express);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.tvCompanyName.setText(cInfoData.get(position).getConName());

        if(position != 0 &&(HanziToPinyin.toPinYin(cInfoData.get(position).getConName().charAt(0)).charAt(0)+"")
                .equals(HanziToPinyin.toPinYin(cInfoData.get(position-1).getConName().charAt(0)).charAt(0) + "")){
            holder.tvLetter.setVisibility(View.GONE);
        }else {
            holder.tvLetter.setVisibility(View.VISIBLE);
            holder.tvLetter.setText(HanziToPinyin.toPinYin(cInfoData.get(position).getConName().charAt(0)).charAt(0) + "");
        }
        return convertView;
    }

    public class ViewHolder {

        TextView tvLetter;

        TextView tvCompanyName;

    }
}
