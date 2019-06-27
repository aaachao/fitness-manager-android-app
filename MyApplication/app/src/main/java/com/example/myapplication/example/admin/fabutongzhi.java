package com.example.myapplication.example.admin;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.entity.Notice;
import com.example.myapplication.example.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


public class fabutongzhi extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;

    private EditText content;
    private Button release;
    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.fabutongzhi);
        findViewById();
        initView();
        found();
    }
    protected void findViewById() {
        this.content = findViewById(R.id.add_news_et_share_content);
        this.release = findViewById(R.id.add_news_btn_release);
    }

    protected void initView() {
        mContext = this;
        this.release.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_news_btn_release:
                checkInfo();
                break;
        }
    }


    private void checkInfo() {
        String contentStr = content.getText().toString();
        if (TextUtils.isEmpty(contentStr)) {
            Toast.makeText(mContext, "请输入通知内容", Toast.LENGTH_SHORT).show();
            content.requestFocus();
            return;
        }
        releaseNews();
    }

    private void found() {
        String url;
        url = Constants.BASE_URL + "Notice?method=found";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .build()
                .execute(new MyStringCallback2());
    }
    public class MyStringCallback2 extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            switch (id) {
                case 1:
                    Notice notice=gson.fromJson(response, Notice.class);
                    if(notice==null){

                    }else{
                        content.setHint(notice.getNotice());
                    }

                    break;
                default:
                    Toast.makeText(mContext, "what", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(mContext, "网络连接出错", Toast.LENGTH_SHORT).show();
        }
    }
    private void releaseNews() {
        String contentStr = content.getText().toString();
        String url;
            url = Constants.BASE_URL + "Notice?method=update";
            OkHttpUtils
                    .post()
                    .url(url)
                    .id(1)
                    .addParams("content", contentStr)
                    .build()
                    .execute(new MyStringCallback());
        }


    public class MyStringCallback extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            switch (id) {
                case 1:
                    if (response.contains("success")) {
                        Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(mContext, "请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    Toast.makeText(mContext, "what", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(mContext, "网络连接出错", Toast.LENGTH_SHORT).show();
        }
    }
}
