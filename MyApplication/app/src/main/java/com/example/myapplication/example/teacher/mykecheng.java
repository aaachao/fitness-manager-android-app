package com.example.myapplication.example.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.entity.Course;
import com.example.myapplication.example.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class mykecheng extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private EditText kechengmingcheng;
    private EditText kechengjiaolian;
    private EditText kechengneirong;
    Intent intent=new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mykecheng2);
        findViewById();
        initView();
    }
    private void initView() {
        mContext=this;
    }

    private void findViewById() {
        findViewById(R.id.querentianjia).setOnClickListener(this);
        kechengmingcheng=findViewById(R.id.kechengmingcheng);
        kechengneirong = findViewById(R.id.kechengneirong);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.querentianjia:
                regiter();
                break;
        }
    }



    private void regiter() {
        String mingcheng =kechengmingcheng.getText().toString().trim();
        String jiaolian = Constants.USER.getUsername();
        String neirong = kechengneirong.getText().toString().trim();
        if (TextUtils.isEmpty(mingcheng) || TextUtils.isEmpty(jiaolian) || TextUtils.isEmpty(neirong) ) {
            Toast.makeText(mContext, "信息不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 服务端验证
        String url = Constants.BASE_URL + "Course?method=add";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("mingcheng", mingcheng)
                .addParams("jiaolian", jiaolian)
                .addParams("neirong", neirong)
                .id(1)
                .build()
                .execute(new MyStringCallback());
    }
    public class MyStringCallback extends StringCallback {

        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            switch (id) {
                case 1:
                    Course a = null;
                    try {
                        a = gson.fromJson(response, Course.class);
                    } catch (JsonSyntaxException e) {
                        a = null;
                    }
                    if (a == null) {
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Toast.makeText(mContext, "添加课程成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    break;
                default:
                    Toast.makeText(mContext, "what?", Toast.LENGTH_SHORT).show();
                    break;
            }
        }



        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(mContext, "网络链接出错！", Toast.LENGTH_SHORT).show();
        }
    }
}
