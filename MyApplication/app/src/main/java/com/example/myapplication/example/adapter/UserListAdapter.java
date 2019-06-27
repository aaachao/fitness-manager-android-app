package com.example.myapplication.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.example.entity.User;

import java.util.List;

public class UserListAdapter extends BaseAdapter {
    private List<User>mlist;
    private LayoutInflater inflater;
    private Context mContext;
    public UserListAdapter(Context mContext, List<User> mlist){
        this.inflater=LayoutInflater.from(mContext);
        this.mContext=mContext;
        this.mlist=mlist;
    }
    @Override
    public int getCount(){
        return mlist.size();
    }
    @Override
    public Object getItem(int position){
        return mlist.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_user,null);
            holder=new ViewHolder();
            holder.bg=(ImageView)convertView.findViewById(R.id.found_list_icon2);
            holder.title = (TextView) convertView.findViewById(R.id.found_list_item_title2);
            holder.username = (TextView) convertView.findViewById(R.id.found_list_item_username2);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);
            holder.weight2 = (TextView) convertView.findViewById(R.id.weight);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        User news=mlist.get(position);
        if (news.getRole().equals("admin")){
            holder.title.setText("管理员");
        }
        else if(news.getRole().equals("teacher")){
            holder.title.setText("教练");
        }
        else if(news.getRole().equals("user")){
            holder.title.setText("会员");
        }
        holder.username.setText("姓名："+news.getName());
        holder.phone.setText("电话："+news.getPhone());
        holder.weight2.setText("体重："+news.getWeight()+"Kg");
        holder.bg.setImageBitmap(news.getBitmap());
        return convertView;
    }
    private class ViewHolder{
        public ImageView bg;
        public TextView title;
        public TextView username;
        public TextView phone;
        public TextView weight2;
    }
}
