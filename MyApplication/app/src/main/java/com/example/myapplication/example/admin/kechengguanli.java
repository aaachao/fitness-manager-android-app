package com.example.myapplication.example.admin;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.example.fragment.ListcourseFragment9;

public class kechengguanli extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    Intent intent=new Intent();
    private ListcourseFragment9 listcourseFragment9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kechengguanli);

        findViewById();
        initView();
    }

    private void findViewById() {
        findViewById(R.id.kechengshanchu).setOnClickListener(this);
        findViewById(R.id.kechengxiugai).setOnClickListener(this);
        findViewById(R.id.kechengtianjia).setOnClickListener(this);
    }

    private void initView() {

        mContext=this;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        listcourseFragment9=new ListcourseFragment9();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //transaction.hide(listcourseFragment9);
        //System.out.println("运行replace");
        transaction.replace(R.id.listkecheng, listcourseFragment9);
        transaction.commit();
        //System.out.println("运行按钮");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.kechengshanchu:
                intent.setClass(getApplicationContext(),kechengshanchu.class);
                startActivity(intent);
                break;
            case R.id.kechengxiugai:
                intent.setClass(getApplicationContext(),kechengxiugai.class);
                startActivity(intent);
                break;
            case R.id.kechengtianjia:
                intent.setClass(getApplicationContext(),kechengtianjia.class);
                startActivity(intent);
                break;
        }
    }
}
