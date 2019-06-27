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

public class mykecheng2 extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private EditText xinkechengmingcheng;
    private EditText xinkechengjiaolian;
    private EditText xinkechengneirong;
    private EditText xuyaoxiugaidexinkechengmingcheng;
    Intent intent=new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mykecheng);
        findViewById();
        initView();
        found();

    }
    private void initView() {
        mContext=this;
    }

    private void findViewById() {
        findViewById(R.id.querenshanchu).setOnClickListener(this);
        findViewById(R.id.querenxiugai).setOnClickListener(this);
        xinkechengmingcheng=findViewById(R.id.kechengmingcheng);
        xinkechengneirong = findViewById(R.id.kechengneirong);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.querenxiugai:
                update();
                break;
            case R.id.querenshanchu:
                delete();
                break;
        }
    }

    private void found() {
        //System.out.println("第二个程序2   Constants.USER.getUsername()="+Constants.USER.getUsername());
        String mingcheng=Constants.COURSE.getCoursename();
        //System.out.println("1231212441414  mingcheng==="+mingcheng);
        //d.判断用户名密码是否为空，不为空请求服务器（省略，默认请求成功）i
        if (TextUtils.isEmpty(mingcheng)  ) {
            Toast.makeText(mContext, "要查询的课程名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 服务端验证
        String url = Constants.BASE_URL + "Course?method=found";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("mingcheng", mingcheng)
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
                       // System.out.println("333333333333  a.getCoursename()==="+a.getCoursename());
                        Constants.COURSE=a;
                        xinkechengmingcheng.setText(a.getCoursename());
                        xinkechengneirong.setText(a.getCoursedata());
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


    private void update() {
        String oldmingcheng=Constants.COURSE.getCoursename();
        String mingcheng =xinkechengmingcheng.getText().toString().trim();
        String jiaolian = Constants.USER.getUsername();
        String neirong =xinkechengneirong.getText().toString().trim();
        if (TextUtils.isEmpty(mingcheng)  || TextUtils.isEmpty(neirong) ) {
            Toast.makeText(mContext, "新的信息不能留空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 服务端验证
        String url = Constants.BASE_URL + "Course?method=update";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("oldmingcheng",oldmingcheng)
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
                        Toast.makeText(mContext, "修改课程成功！", Toast.LENGTH_SHORT).show();
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
    private void delete() {
        String mingcheng = Constants.COURSE.getCoursename();
        // 服务端验证
        String url = Constants.BASE_URL + "Course?method=delete";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("mingcheng", mingcheng)
                .id(1)
                .build()
                .execute(new MyStringCallback3());
    }


    public class MyStringCallback3 extends StringCallback {

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
                    } else {
                        Toast.makeText(mContext, "删除课程成功！", Toast.LENGTH_SHORT).show();
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
