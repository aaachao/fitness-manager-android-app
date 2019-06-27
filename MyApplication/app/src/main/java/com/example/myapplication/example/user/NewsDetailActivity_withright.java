package com.example.myapplication.example.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.adapter.NewsDetailCommnetsAdapter_withright;
import com.example.myapplication.example.entity.Comment;
import com.example.myapplication.example.entity.NewsDetail;
import com.example.myapplication.example.utils.Constants;
import com.example.myapplication.example.widgets.ListViewWithScrollView;
import com.google.gson.Gson;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;


public class NewsDetailActivity_withright extends AppCompatActivity implements View.OnClickListener {

    private NewsDetailCommnetsAdapter_withright adapter;
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
    private com.yanzhenjie.recyclerview.SwipeRecyclerView mRecyclerView;

    private ListViewWithScrollView commentsLV;


    private Context mContext;

    private int newsId;
    private String replyUsername;


    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        newsId = getIntent().getIntExtra("newsId", 0);
        setContentView(R.layout.activity_news_detai_withrightl);
        findViewById();
        initView();
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
    }
    private void initData() {
        LinearLayoutManager manager=new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        //mLayoutManager=new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);

    }
    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int width = getResources().getDimensionPixelSize((R.dimen.dp_70));
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext).setBackground(
                    R.drawable.selector_red)
                    //.setImage(R.drawable.d2bef2ce57765e7b2f06a5806f398ee2)
                    .setText("删除")
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
        }
    };
    protected void findViewById() {

        usernameTV = findViewById(R.id.news_detail_username);
        releaseTimeTV = findViewById(R.id.news_detail_time);
        imageIV = findViewById(R.id.news_detail_image);
        contentTV = findViewById(R.id.news_detail_content);
        titleTV = findViewById(R.id.detail_title);

        commentPane = findViewById(R.id.news_detail_add_commment_pane);
        addCommentET = findViewById(R.id.news_detail_add_commment_text);
        addCommentIV = findViewById(R.id.news_detail_add_commment_btn);
        mRecyclerView=findViewById(R.id.news_detail_comment);
        mRecyclerView.setOnItemMenuClickListener(mItemMenuClickListener);
    }
    private OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                if(menuPosition==0){
                    delete(mList.get(position).getCommentId());
                }
            }
        }
    };
    private void delete(int commentId) {
        String url = Constants.BASE_URL + "Comment?method=delete";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("commentId", commentId + "")
                .build()
                .execute(new MyStringCallback7());
    }
    public class MyStringCallback7 extends StringCallback {
        @Override
        public void onResponse(String response, int id) {

            Gson gson = new Gson();

            switch (id) {
                case 1:
                    Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                    refreshData();
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
    protected void initView() {
        mContext = this;

        //addCommentIV.setOnClickListener(this);

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

        }
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
                        adapter = new NewsDetailCommnetsAdapter_withright(mContext, mList);

                        initData();
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
