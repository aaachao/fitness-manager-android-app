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
import com.example.myapplication.example.entity.NewsListForFound;

import java.util.List;

public class FoundNewsAdapter_withright extends RecyclerView.Adapter<FoundNewsAdapter_withright.ViewHolder> {

    private final Context mContext;
    private final ViewHolder2 mholder=new ViewHolder2();
    private LayoutInflater inflater;
    private List<NewsListForFound> mList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mholder.bg = (ImageView) itemView.findViewById(R.id.found_list_icon);
            mholder.title = (TextView) itemView.findViewById(R.id.found_list_item_title);
            mholder.username = (TextView) itemView.findViewById(R.id.found_list_item_username);
        }

        public void setData(List<NewsListForFound> mlist) {
            NewsListForFound news=mlist.get(getPosition());
            mholder.title.setText(news.getTitle());
            mholder.username.setText(news.getName());
            mholder.bg.setImageBitmap(news.getBitmap());
        }
    }
    public FoundNewsAdapter_withright(Context mContext, List<NewsListForFound> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(mContext).inflate(R.layout.item_found_news,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoundNewsAdapter_withright.ViewHolder holder, int position) {
        mList.get(position);
        holder.setData(mList);
    }

    @Override
    public int getItemCount() {
        if(mList!=null){
            return mList.size();
        }
        return 0;
    }





/*public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
        convertView = inflater.inflate(R.layout.item_found_news, null);
        holder = new ViewHolder();
        holder.bg = (ImageView) convertView.findViewById(R.id.found_list_icon);
        holder.title = (TextView) convertView.findViewById(R.id.found_list_item_title);
        holder.username = (TextView) convertView.findViewById(R.id.found_list_item_username);
        convertView.setTag(holder);
    } else {
        holder = (ViewHolder) convertView.getTag();
    }
    NewsListForFound news = mList.get(position);
    holder.title.setText(news.getTitle());
    holder.username.setText(news.getName());
    holder.bg.setImageBitmap(news.getBitmap());
    return convertView;
}*/

    private class  ViewHolder2 {
        public ImageView bg;
        public TextView title;
        public TextView username;
    }
}
