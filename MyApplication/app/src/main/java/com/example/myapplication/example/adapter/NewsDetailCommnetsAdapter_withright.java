package com.example.myapplication.example.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.example.entity.Comment;

import java.util.List;
public class NewsDetailCommnetsAdapter_withright extends RecyclerView.Adapter<NewsDetailCommnetsAdapter_withright.ViewHolder> {
    private final Context mContext;
    private final ViewHolder2 mholder=new ViewHolder2();
    private List<Comment> mList;

    private LayoutInflater inflater;
    public NewsDetailCommnetsAdapter_withright(Context mContext, List<Comment> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.inflater = LayoutInflater.from(mContext);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mholder.username = (TextView) itemView.findViewById(R.id.news_detail_comment_username);
            mholder.commentTime = (TextView) itemView.findViewById(R.id.news_detail_comment_time);
            mholder.replyUser = (TextView) itemView.findViewById(R.id.news_detail_comment_reply_user);
            mholder.replyContainer = (LinearLayout) itemView.findViewById(R.id.news_detail_reply_info);
            mholder.content = (TextView) itemView.findViewById(R.id.news_detail_commment_content);
            mholder.addComment = (ImageView) itemView.findViewById(R.id.news_detail_comment_add_reply);

        }
        public void setData(List<Comment> mlist) {
            Comment comment=mlist.get(getPosition());
            mholder.username.setText(comment.getName());

            if (TextUtils.isEmpty(comment.getReplyUser())) {
                mholder.replyContainer.setVisibility(View.INVISIBLE);
            } else {
                mholder.replyContainer.setVisibility(View.VISIBLE);
                mholder.replyUser.setText(comment.getReplyUser());
            }
            mholder.commentTime.setText(comment.getCommentTime());
            mholder.content.setText(comment.getComment());
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(mContext).inflate(R.layout.item_news_detail_comment,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
    private static class ViewHolder2 {
        private TextView username;
        private TextView commentTime;
        private TextView replyUser;
        private TextView content;
        private ImageView addComment;
        private LinearLayout replyContainer;
    }
}
