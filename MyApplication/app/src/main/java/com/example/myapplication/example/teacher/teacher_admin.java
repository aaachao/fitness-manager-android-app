package com.example.myapplication.example.teacher;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.entity.User;
import com.example.myapplication.example.fragment.FoundFragment;
import com.example.myapplication.example.fragment.JiaolianFragment;
import com.example.myapplication.example.fragment.ListcourseFragment2;
import com.example.myapplication.example.fragment.WodeFragment;
import com.example.myapplication.example.fragment.jiaolianFragment1;
import com.example.myapplication.example.fragment.jiaolianFragment3;
import com.example.myapplication.example.utils.AppManager;
import com.example.myapplication.example.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class teacher_admin extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    Intent intent = new Intent();
    private ListcourseFragment2 listcourseFragment;
    private WodeFragment wodeFragment;
    private jiaolianFragment1 jiaolianFragment1;
    private FoundFragment foundFragment;
    private jiaolianFragment3 jiaolianFragment3;
    private JiaolianFragment jiaolianFragment;
    private long mExitTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        listcourseFragment=new ListcourseFragment2();
        wodeFragment=new WodeFragment();
        jiaolianFragment1 =new jiaolianFragment1();
        jiaolianFragment=new JiaolianFragment();
        foundFragment=new FoundFragment();
        jiaolianFragment3=new jiaolianFragment3();
        refeachFragment(R.id.wodexueyuan);
        findViewById();
        initView();
    }



    private void findViewById() {
        findViewById(R.id.found).setOnClickListener(this);
        findViewById(R.id.wodexueyuan).setOnClickListener(this);
        findViewById(R.id.wode2).setOnClickListener(this);
    }

    private void initView() {
        mContext = this;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //course();
        status();
    }
    private void status() {
        checkUser();
    }

    private void checkUser() {
        String url = Constants.BASE_URL + "User?method=found";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("username",Constants.USER.getUsername())
                .build()
                .execute(new MyStringCallback5());
    }
    public class MyStringCallback5 extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            User user = null;
            user=gson.fromJson(response, User.class);
            Constants.USER = user;
        }
        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(mContext, "网络连接出错", Toast.LENGTH_SHORT).show();
        }
    }
    /*private void course() {
        //System.out.println("Constants.USER.getUsername()="+Constants.USER.getUsername());
        String url = Constants.BASE_URL + "Course?method=found2";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("jiaolian",Constants.USER.getUsername() + "")
                .build()
                .execute(new MyStringCallback2());
    }
    public class MyStringCallback2 extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            Course a=null;
            a = gson.fromJson(response, Course.class);
            //System.out.println("a.getCourseId(()="+a.getCourseId());
            Constants.COURSE=a;
        }


        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {

        }
    }*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.found:
                refeachFragment(R.id.found);
                break;
            case R.id.wode2:
                refeachFragment(R.id.wode2);
                break;
            case R.id.wodexueyuan:
                refeachFragment(R.id.wodexueyuan);
                break;
        }
    }

    private void refeachFragment(int btnId) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (btnId) {
            case R.id.wode2:
               transaction.replace(R.id.xueyuanjiemian,jiaolianFragment);
                break;
            case R.id.wodexueyuan:
                transaction.replace(R.id.xueyuanjiemian,jiaolianFragment3);
                break;
            case R.id.found:
                transaction.replace(R.id.xueyuanjiemian,foundFragment);
                break;
        }
        transaction.commit();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 800) {
                Toast.makeText(mContext, "再按一次退出APP", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
                return true;
            } else {
                AppManager.getInstance().killAllActivity();
                AppManager.getInstance().AppExit(this);
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
