package com.example.myapplication.example.user;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.entity.Depend;
import com.example.myapplication.example.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class jishi extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private int time;
    private Chronometer chronometer = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yundongjishi);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setFormat("%s");
        findViewByID();
        initView();
    }

    private void initView() {
        mContext=this;
    }

    private void findViewByID() {
        findViewById(R.id.kaishijishi).setOnClickListener(this);
        findViewById(R.id.chongxinjishi).setOnClickListener(this);
        findViewById(R.id.wanchengxunlian).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.kaishijishi:
                onStart(chronometer);
                break;
            case R.id.chongxinjishi:
                onReset(chronometer);
                break;
            case R.id.wanchengxunlian:
                onStop(chronometer);
                int temp0 = Integer.parseInt(chronometer.getText().toString().split(":")[0]);
                int temp1 =Integer.parseInt(chronometer.getText().toString().split(":")[1]);
                time=temp0*60+temp1;
               // System.out.println("sdasdasdadada时间="+time);
                add();
                break;
        }
    }

    private void add() {
        int calories=Integer.parseInt(new java.text.DecimalFormat("0").format(time*Constants.COURSE.getCalories()*Constants.USER.getWeight()/10000));
        if(calories==0)
        {
            Toast.makeText(mContext, "您的运动量太小了！", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            if(Constants.COURSE.getCourseteach()==null)
            {
                String url = Constants.BASE_URL + "Calories?method=add2";
                OkHttpUtils
                        .post()
                        .url(url)
                        .id(1)
                        .addParams("userId", Constants.USER.getUserId()+"")
                        .addParams("fitnessId",Constants.COURSE.getCourseId() + "")
                        .addParams("calories",calories + "")
                        .build()
                        .execute(new MyStringCallback4());
            }
            else{
                String url = Constants.BASE_URL + "Calories?method=add";
                OkHttpUtils
                        .post()
                        .url(url)
                        .id(1)
                        .addParams("userId", Constants.USER.getUserId()+"")
                        .addParams("fitnessId",Constants.COURSE.getCourseId() + "")
                        .addParams("coachId",Constants.COURSE.getCourseteach())
                        .addParams("calories",calories + "")
                        .build()
                        .execute(new MyStringCallback4());
            }
        }

    }
    public class MyStringCallback4 extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            switch (id) {
                case 1:
                    Depend a = null;
                    try {
                        a = gson.fromJson(response, Depend.class);
                    } catch (JsonSyntaxException e) {
                        a = null;
                    }
                    if (a == null) {
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Toast.makeText(mContext, "已记录！", Toast.LENGTH_SHORT).show();
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

    public void onStart(View view)
    {
        chronometer.start();
    }

    public void onStop(View view)
    {
        chronometer.stop();
    }

    public void onReset(View view)
    {
        chronometer.setBase(SystemClock.elapsedRealtime());
    }



}
