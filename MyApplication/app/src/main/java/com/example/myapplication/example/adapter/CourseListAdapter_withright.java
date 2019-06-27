package com.example.myapplication.example.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.example.entity.Course;

import java.util.List;

public  class CourseListAdapter_withright extends RecyclerView.Adapter<CourseListAdapter_withright.ViewHolder> {

    private final Context mContext;
    private final ViewHolder2 mholder=new ViewHolder2();
    private List<Course> mlist;
    public  CourseListAdapter_withright(Context context, List<Course>mlist){
        this.mContext=context;
        this.mlist=mlist;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view =LayoutInflater.from(mContext).inflate(R.layout.item_course,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mlist.get(position);
        holder.setData(mlist);
    }

    @Override
    public int getItemCount() {
        if(mlist!=null){
            return mlist.size();
        }

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mholder.bg=(ImageView)itemView.findViewById(R.id.found_list_icon);
            mholder.title = (TextView) itemView.findViewById(R.id.found_list_item_title);
            mholder.username = (TextView) itemView.findViewById(R.id.found_list_item_username);
            mholder.teaname=(TextView) itemView.findViewById(R.id.jiaolianxingming);
        }

        public void setData(List<Course> mlist) {
            Course news=mlist.get(getPosition());
            mholder.title.setText(news.getCoursename());
            if(news.getTeachername()==null)
            {
                mholder.teaname.setText("");
            }
            else {
                mholder.teaname.setText("教练："+news.getTeachername());
            }
            mholder.username.setText(news.getCoursedata());
            mholder.bg.setImageBitmap(news.getBitmap());
        }
    }
    private class ViewHolder2{
        public ImageView bg;
        public TextView title;
        public TextView username;
        public TextView teaname;
    }
}
