package com.example.myapplication.example.teacher;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.example.fragment.ListcourseFragment9;
import com.example.myapplication.example.fragment.ListsikeFragment9;

public class sikeguanli extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    Intent intent=new Intent();
    private ListcourseFragment9 listcourseFragment9;
    private ListsikeFragment9 listsikeFragment9;
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
        //listcourseFragment9=new ListcourseFragment9();
        listsikeFragment9=new ListsikeFragment9();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //transaction.replace(R.id.listkecheng, listcourseFragment9);
        transaction.replace(R.id.listkecheng, listsikeFragment9);
        transaction.commit();
        //System.out.println("运行按钮");
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.kechengshanchu:
                intent.setClass(getApplicationContext(),sikeshanchu.class);
                startActivity(intent);
                break;
            case R.id.kechengxiugai:
                intent.setClass(getApplicationContext(),sikexiugai.class);
                startActivity(intent);
                break;
            case R.id.kechengtianjia:
                intent.setClass(getApplicationContext(),siketianjia.class);
                startActivity(intent);
                break;
        }
    }
}
