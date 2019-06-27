package com.example.myapplication.example.acttofrag;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.entity.Depend;
import com.example.myapplication.example.fragment.ListcoachFragmentbyfitness2;
import com.example.myapplication.example.fragment.ListuserFragment9;
import com.example.myapplication.example.fragment.WodekongFragment;
import com.example.myapplication.example.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class xiangmudejiaolian extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    Intent intent=new Intent();
    private ListuserFragment9 listuserFragment9;
    private ListcoachFragmentbyfitness2 listcoachFragmentbyfitness2;
    private WodekongFragment wodekongFragment;
    int j=0;
    int z=0;
    int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiangmudejiaolian);
        findViewById();
        initView();
    }

    private void findViewById() {
        findViewById(R.id.adduser).setOnClickListener(this);
    }

    private void initView() {
        mContext=this;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.adduser:
                add();
                break;
        }
    }
    private void add() {
        String url = Constants.BASE_URL + "Depend?method=add";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("userId", Constants.USER.getUserId()+"")
                .addParams("fitnessId",Constants.COURSE.getCourseId() + "")
                .build()
                .execute(new MyStringCallback4());
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
                        Toast.makeText(mContext, "添加成功！", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onResume(){
        super.onResume();
        listcoachFragmentbyfitness2=new ListcoachFragmentbyfitness2();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.listyonghu12, listcoachFragmentbyfitness2);
        transaction.commit();
    }

}
