package com.example.myapplication.example.teacher;

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
import com.example.myapplication.example.entity.Course;
import com.example.myapplication.example.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class sikeshanchu extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private EditText xuyaoshanchudekechengmingcheng;
    private Button querenshanchu;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kechengshanchu);
        findViewById();
        initView();
    }

    private void initView() {
        mContext = this;
    }

    private void findViewById() {
        findViewById(R.id.querenshanchu).setOnClickListener(this);
        xuyaoshanchudekechengmingcheng = findViewById(R.id.xuyaoshanchudekechengmingcheng);
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
        String mingcheng = xuyaoshanchudekechengmingcheng.getText().toString().trim();
        //d.判断用户名密码是否为空，不为空请求服务器（省略，默认请求成功）i
        if (TextUtils.isEmpty(mingcheng)) {
            Toast.makeText(mContext, "要删除的名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 服务端验证
        String url = Constants.BASE_URL + "Course?method=deletesike";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("mingcheng", mingcheng)
                .addParams("coachId",Constants.USER.getUserId()+"")
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
                    } else {
                        Toast.makeText(mContext, "删除成功！", Toast.LENGTH_SHORT).show();
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
