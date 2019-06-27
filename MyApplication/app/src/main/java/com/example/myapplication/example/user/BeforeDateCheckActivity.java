package com.example.myapplication.example.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by djzhao on 17/05/04.
 */

public class BeforeDateCheckActivity extends AppCompatActivity {
    private Context mContext;
    Intent intent=new Intent();
    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_before_date_check);
        findViewById();
        initView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        getCheckedList();
    }

    protected void findViewById() {

    }


    protected void initView() {

    }

    private void getCheckedList() {
        String url = Constants.BASE_URL + "DailyCheck?method=getCheckedList";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("userId", Constants.USER.getUserId() + "")
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            SystemClock.sleep(1000);
            switch (id) {
                case 1:
                    if (response.contains("ERROR")) {
                        Toast.makeText(mContext, "暂时无法获取数据", Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                    else {
                        String[] dates = response.split(",");
                        if (Constants.DAILYCHECKEDLIST == null) {
                            Constants.DAILYCHECKEDLIST = new ArrayList<String>();
                        }
                        else {
                            Constants.DAILYCHECKEDLIST.clear();
                        }
                        //if ()
                        System.out.println("???????????s="+dates.length);
                        if (dates.length-1==0)
                        {

                        }
                        else
                        {
                            Constants.DAILYCHECKEDLIST.clear();
                            System.out.println(" Constants.DAILYCHECKEDLIST.size()="+ Constants.DAILYCHECKEDLIST.size());
                            for (String s: dates) {
                                String[] split = s.split("-");
                                System.out.println("???????????s="+s);
                                s = split[0] + "-" + removeHeadingZero(split[1]) + "-" + removeHeadingZero(split[2]);
                                Constants.DAILYCHECKEDLIST.add(s);
                            }
                            System.out.println(" Constants.DAILYCHECKEDLIST.size()="+ Constants.DAILYCHECKEDLIST.size());
                        }

                        intent.setClass(getApplicationContext(),DateCheckActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    break;
            }
        }

        /**
         * 去除头部的0
         * @param str
         * @return
         */
        public String removeHeadingZero(String str) {
            if (str.startsWith("0")) {
                return str.substring(1);
            } else {
                return str;
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(mContext, "网络连接出错！", Toast.LENGTH_SHORT).show();
        }
    }
}
