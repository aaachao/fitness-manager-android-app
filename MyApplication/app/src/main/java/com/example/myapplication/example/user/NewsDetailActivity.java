package com.example.myapplication.example.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.adapter.NewsDetailCommnetsAdapter;
import com.example.myapplication.example.entity.Comment;
import com.example.myapplication.example.entity.NewsDetail;
import com.example.myapplication.example.utils.Constants;
import com.example.myapplication.example.utils.DateUtils;
import com.example.myapplication.example.widgets.ListViewWithScrollView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;



public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private NewsDetailCommnetsAdapter adapter;
    private NewsDetail newsDetail;
    private List<Comment> mList;

    private TextView usernameTV;
    private TextView titleTV;
    private TextView releaseTimeTV;
    private ImageView imageIV;
    private TextView contentTV;

    private LinearLayout commentPane;
    private EditText addCommentET;
    private ImageView addCommentIV;
    private boolean isShowCommentPane;


    private ListViewWithScrollView commentsLV;
    private LinearLayout commentLL;
    private LinearLayout favorLL;

    private Context mContext;

    private int newsId;
    private String replyUsername;


    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        newsId = getIntent().getIntExtra("newsId", 0);
        setContentView(R.layout.activity_news_detail);
        findViewById();
        initView();
    }

    protected void findViewById() {

        usernameTV = findViewById(R.id.news_detail_username);
        releaseTimeTV = findViewById(R.id.news_detail_time);
        imageIV = findViewById(R.id.news_detail_image);
        contentTV = findViewById(R.id.news_detail_content);
        titleTV = findViewById(R.id.detail_title);

        commentLL = findViewById(R.id.news_detail_add_comment);
        favorLL = findViewById(R.id.news_detail_add_favor);

        commentPane = findViewById(R.id.news_detail_add_commment_pane);
        addCommentET = findViewById(R.id.news_detail_add_commment_text);
        addCommentIV = findViewById(R.id.news_detail_add_commment_btn);

        commentsLV = findViewById(R.id.news_detail_comment);
    }


    protected void initView() {
        mContext = this;

        commentLL.setOnClickListener(this);
        favorLL.setOnClickListener(this);
        addCommentIV.setOnClickListener(this);

        refreshData();
    }

    private void refreshData() {
        String url = Constants.BASE_URL + "News?method=getNewsDetail";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("newsId", newsId + "")
                .build()
                .execute(new MyStringCallback());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.news_detail_add_comment:
                showCommemtPane();
                break;
            /*case R.id.news_detail_add_favor:
                addNewFavor();
                break;*/
            case R.id.news_detail_add_commment_btn:
                addNewComment();
                break;
        }
    }

    private void showCommemtPane() {
        isShowCommentPane = !isShowCommentPane;
        if (isShowCommentPane) {
            commentPane.setVisibility(View.VISIBLE);
            addCommentET.setHint("发表新评论");
            replyUsername = "";
            addCommentET.requestFocus();
        } else {
            commentPane.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(addCommentET.getWindowToken(), 0);
            // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // 添加新评论
    private void addNewComment() {
        String commentText = addCommentET.getText().toString().trim();
        if (TextUtils.isEmpty(commentText)) {
            Toast.makeText(mContext, "请先输入内容", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = Constants.BASE_URL + "Comment?method=addNewComment";
        OkHttpUtils
                .post()
                .url(url)
                .id(3)
                .addParams("newsId", newsId + "")
                .addParams("userId", Constants.USER.getUserId() + "")
                .addParams("comment", commentText)
                .addParams("replyUser", replyUsername)
                .build()
                .execute(new MyStringCallback());
    }

    private void addNewFavor() {
        String url = Constants.BASE_URL + "Favor?method=addNewFavor";
        OkHttpUtils
                .post()
                .url(url)
                .id(2)
                .addParams("newsId", newsId + "")
                .addParams("userId", Constants.USER.getUserId() + "")
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 1:
                    Gson gson = new Gson();
                    try {
                        NewsDetail newsDetail = gson.fromJson(response, NewsDetail.class);
                        if (newsDetail != null) {
                            usernameTV.setText(newsDetail.getName());
                            releaseTimeTV.setText(newsDetail.getReleaseTime());
                            titleTV.setText(newsDetail.getTitle());
                            contentTV.setText(newsDetail.getContent());
                            // 加载图片
                            if (!TextUtils.isEmpty(newsDetail.getImage())) {
                                imageIV.setVisibility(View.VISIBLE);
                                imageIV.setImageResource(R.drawable.default_image);
                                getNewsImage(newsDetail.getImage());
                            } else {
                                imageIV.setVisibility(View.GONE);
                            }
                        }
                        mList = newsDetail.getComments();
                    } catch (Exception e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        mList = null;
                    }
                    if (mList != null && mList.size() > 0) {
                        adapter = new NewsDetailCommnetsAdapter(mContext, mList);
                        adapter.setOnCommentButtonClickListner(new NewsDetailCommnetsAdapter.OnCommentButtonClickListner() {

                            @Override
                            public void OnCommentButtonClicked(String replyUser) {
                                commentPane.setVisibility(View.VISIBLE);
                                addCommentET.setHint("回复 " + replyUser + " 的评论");
                                replyUsername = replyUser;
                            }
                        });
                        commentsLV.setAdapter(adapter);
                    }
                    break;
                case 2:
                    Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    if (response.contains("error")) {
                        Toast.makeText(mContext, "请稍后再试", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        Comment comment = new Comment();
                        comment.setCommentTime(DateUtils.getCurrentDatetime());
                        comment.setComment(addCommentET.getText().toString());
                        comment.setReplyUser(replyUsername);
                        comment.setUsername(Constants.USER.getUsername());
                        if (mList == null) {
                            mList = new ArrayList<Comment>();
                        }
                        mList.add(0, comment);
                        if (adapter == null) {
                            adapter = new NewsDetailCommnetsAdapter(mContext, mList);
                            commentsLV.setAdapter(adapter);
                        }
                        adapter.notifyDataSetChanged();
                        replyUsername = "";
                        addCommentET.setText("");
                        commentPane.setVisibility(View.GONE);
                    }
                    break;
                default:
                    Toast.makeText(mContext, "what！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(mContext, "网络链接出错！", Toast.LENGTH_SHORT).show();
        }
    }

    private void getNewsImage(String imageName) {
        String url = Constants.BASE_URL + "Download?method=getNewsImage";

        OkHttpUtils
                .get()//
                .url(url)//
                .addParams("imageName", imageName)
                .build()//
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Toast.makeText(mContext,"无法获取图片", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int i) {
                        imageIV.setImageBitmap(bitmap);
                    }
                });
    }
}
