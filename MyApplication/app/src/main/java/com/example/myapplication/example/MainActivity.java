package com.example.myapplication.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.admin.activity_admin_main;
import com.example.myapplication.example.entity.User;
import com.example.myapplication.example.kechengbiao.kechengbiao;
import com.example.myapplication.example.teacher.teacher_admin;
import com.example.myapplication.example.user.user_admin;
import com.example.myapplication.example.utils.Constants;
import com.example.myapplication.example.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private EditText et_username;
    private EditText et_password;
    Intent intent=new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext=this;
        findViewById();
        initView();
    }

    private void findViewById() {
        findViewById(R.id.login_bt_register).setOnClickListener(this);
        findViewById(R.id.login_bt_login).setOnClickListener(this);
        et_username=findViewById(R.id.login_et_username);
        et_password=findViewById(R.id.login_et_password);
    }

    private void initView() {
        getConfig();
        echo();
    }

    private void getConfig() {
        Map<String, String> ipConfig = SharedPreferencesUtils.getIPConfig(this);
        if (TextUtils.isEmpty(ipConfig.get("ip"))||TextUtils.isEmpty(ipConfig.get("port")))
        {
            return;
        }
        else {
            Constants.BASE_URL="http://"+ipConfig.get("ip")+":"+ipConfig.get("port")+"/server/";
        }
    }

    private void echo() {
        Map<String, String> map = SharedPreferencesUtils.getUserInfo(mContext);//获取用户名密码
        if (map != null) {
            String username = map.get("username");
            String password = map.get("password");
            et_username.setText(username);
            et_password.setText(password);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.login_bt_login:
                login();
            break;
            case R.id.login_bt_register:
                //intent.setClass(getApplicationContext(), kechengbiao.class);
                intent.setClass(getApplicationContext(), activity_register.class);
                startActivity(intent);
            break;
        }
    }

    private void login() {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        //d.判断用户名密码是否为空，不为空请求服务器（省略，默认请求成功）
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "不可留空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 服务端验证
        checkUser();
    }

    private void checkUser() {

        String url = Constants.BASE_URL + "User?method=login";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("username", et_username.getText().toString().trim())
                .addParams("password", et_password.getText().toString().trim())
                .build()
                .execute(new MyStringCallback());
    }
    public class MyStringCallback extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            switch (id) {
                case 1:
                    User user = gson.fromJson(response, User.class);

                    String a="admin";
                    String b="user";
                    String c="teacher";

                    if (user.getUserId() == 0) {
                        Toast.makeText(mContext, "账号或密码错误", Toast.LENGTH_SHORT).show();
                        return;
                    }else if (user.getRole().equals(a)){

                        // 存储用户
                        Constants.USER = user;
                        boolean result = SharedPreferencesUtils.saveUserInfo(mContext, user);
                        if (result) {
                            Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "用户存储失败", Toast.LENGTH_SHORT).show();
                        }
                        intent.setClass(getApplicationContext(), activity_admin_main.class);
                        startActivity(intent);
                    }
                    else if (user.getRole().equals(b))
                    {
                        // 存储用户
                        Constants.USER = user;
                        boolean result = SharedPreferencesUtils.saveUserInfo(mContext, user);
                        if (result) {
                            Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "用户存储失败", Toast.LENGTH_SHORT).show();
                        }
                        intent.setClass(getApplicationContext(),user_admin.class);
                        startActivity(intent);
                    }
                    else if (user.getRole().equals(c))
                    {
                        // 存储用户
                        Constants.USER = user;
                        boolean result = SharedPreferencesUtils.saveUserInfo(mContext, user);
                        if (result) {
                            Toast.makeText(mContext, "登录成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "用户存储失败", Toast.LENGTH_SHORT).show();
                        }
                        intent.setClass(getApplicationContext(), teacher_admin.class);
                        startActivity(intent);
                    }
                    finish();
                    break;

                default:
                    Toast.makeText(mContext, "default???", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(mContext, "网络连接出错", Toast.LENGTH_SHORT).show();
        }

        }


    }
