package com.example.myapplication.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.example.entity.Calories;

import java.util.List;

public class CaloriesListAdapter extends BaseAdapter {
    private List<Calories>mlist;
    private LayoutInflater inflater;
    private Context mContext;
    public CaloriesListAdapter(Context mContext, List<Calories> mlist){
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
            convertView=inflater.inflate(R.layout.item_calories,null);
            holder=new ViewHolder();
            holder.bg=(ImageView)convertView.findViewById(R.id.found_list_icon3);
            holder.title = (TextView) convertView.findViewById(R.id.found_list_item_title3);
            holder.username = (TextView) convertView.findViewById(R.id.found_list_item_username3);
            holder.time = (TextView) convertView.findViewById(R.id.found_list_item_time3);
            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder)convertView.getTag();
        }
        Calories news=mlist.get(position);
        String strh=news.getTime()+"";
        String strm =strh.substring(0,strh.length()-2);
            holder.title.setText(news.getCoursename());

        holder.username.setText("消耗 的卡路里："+news.getCalories()+"");
        holder.time.setText(strm);
      /*  if(position%5==0){
            holder.bg.setImageResource(R.drawable.png1);
        }
        if(position%5==1){
            holder.bg.setImageResource(R.drawable.png2);
        }
        if(position%5==2){
            holder.bg.setImageResource(R.drawable.png3);
        }
        if(position%5==3){
            holder.bg.setImageResource(R.drawable.png4);
        }
        if(position%5==4){
            holder.bg.setImageResource(R.drawable.png5);
        }*/
        return convertView;
    }
    private class ViewHolder{
        public ImageView bg;
        public TextView title;
        public TextView username;
        private TextView time;
    }
}
