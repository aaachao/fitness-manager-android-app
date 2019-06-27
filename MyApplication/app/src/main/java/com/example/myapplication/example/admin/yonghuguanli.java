package com.example.myapplication.example.admin;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.example.fragment.ListcourseFragmentbyuser;
import com.example.myapplication.example.fragment.ListuserFragment9;
import com.example.myapplication.example.fragment.WodekongFragment;

public class yonghuguanli extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    Intent intent=new Intent();
    private ListuserFragment9 listuserFragment9;
    private ListcourseFragmentbyuser listcourseFragmentbyuser;
    private WodekongFragment wodekongFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yonghuguanli);

        findViewById();
        initView();
    }

    private void findViewById() {
        findViewById(R.id.yonghushanchu).setOnClickListener(this);
        findViewById(R.id.yonghuxiugai).setOnClickListener(this);
        findViewById(R.id.yonghutianjia).setOnClickListener(this);
    }

    private void initView() {
        mContext=this;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        listuserFragment9 =new ListuserFragment9();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //transaction.hide(listuserFragment9);
        transaction.replace(R.id.listyonghu, listuserFragment9);
        transaction.commit();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.yonghushanchu:
                intent.setClass(getApplicationContext(),yonghushanchu.class);
                startActivity(intent);
                break;
            case R.id.yonghuxiugai:
                intent.setClass(getApplicationContext(),yonghuxiugai.class);
                startActivity(intent);
                break;
            case R.id.yonghutianjia:
                intent.setClass(getApplicationContext(),yonghutianjia.class);
                startActivity(intent);
                break;
        }
    }

}
