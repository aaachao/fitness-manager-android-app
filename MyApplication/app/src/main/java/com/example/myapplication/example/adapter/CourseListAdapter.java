package com.example.myapplication.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.example.entity.Course;

import java.util.List;

public  class  CourseListAdapter extends BaseAdapter {
    private List<Course>mlist;
    private LayoutInflater inflater;
    private Context mContext;
    public CourseListAdapter(Context mContext,List<Course> mlist){
        this.inflater=LayoutInflater.from(mContext);
        this.mContext=mContext;
        this.mlist=mlist;
    }
    private String imageName;
    private ImageView imageTV;
    @Override
    public int getCount(){
        return mlist.size();
       //
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
    public  View getView(int position, View convertView, ViewGroup parent){
        final ViewHolder holder;
        System.out.println("convertView="+convertView);
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_course,null);
            holder=new ViewHolder();
            holder.bg=(ImageView)convertView.findViewById(R.id.found_list_icon);
            holder.title = (TextView) convertView.findViewById(R.id.found_list_item_title);
            holder.username = (TextView) convertView.findViewById(R.id.found_list_item_username);
            holder.teaname=(TextView) convertView.findViewById(R.id.jiaolianxingming);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        Course news=mlist.get(position);
        holder.title.setText(news.getCoursename());
        if(news.getTeachername()==null)
        {
            holder.teaname.setText("");
        }
        else {
            holder.teaname.setText("教练："+news.getTeachername());
        }
        holder.username.setText(news.getCoursedata());
        holder.bg.setImageBitmap(news.getBitmap());


System.out.println("现在是"+news.getCoursename());

        System.out.println("mlist.size()"+mlist.size());
        return convertView;
    }
    private class ViewHolder{
        public ImageView bg;
        public TextView title;
        public TextView username;
        public TextView teaname;
    }
}
