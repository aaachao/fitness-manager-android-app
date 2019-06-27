package com.example.myapplication.example.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.entity.User;
import com.example.myapplication.example.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class yonghushanchu extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private EditText xuyaoshanchudeyonghuzhanghao;
    private Button querenshanchu;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yonghushanchu);
        findViewById();
        initView();
    }

    private void initView() {
        mContext = this;
    }

    private void findViewById() {
        findViewById(R.id.querenshanchu).setOnClickListener(this);
        xuyaoshanchudeyonghuzhanghao = findViewById(R.id.xuyaoshanchudeyonghuzhanghao);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.querenshanchu:
                delete();
                break;
        }
    }


    private void delete() {
        String username =xuyaoshanchudeyonghuzhanghao.getText().toString().trim();
        //d.判断用户名密码是否为空，不为空请求服务器（省略，默认请求成功）i
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(mContext, "要删除的用户账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.equals("admin")) {
            Toast.makeText(mContext, "不可以删除admin管理员", Toast.LENGTH_SHORT).show();
            return;
        }
        // 服务端验证
        String url = Constants.BASE_URL + "User?method=delete";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("username", username)
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
                    User a = null;
                    try {
                        a = gson.fromJson(response, User.class);
                    } catch (JsonSyntaxException e) {
                        a = null;
                    }
                    if (a == null) {
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Toast.makeText(mContext, "删除用户成功！", Toast.LENGTH_SHORT).show();
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
