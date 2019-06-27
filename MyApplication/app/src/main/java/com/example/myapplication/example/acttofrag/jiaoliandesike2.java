package com.example.myapplication.example.acttofrag;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.example.fragment.ListcourseFragmentbyuser;
import com.example.myapplication.example.fragment.ListsikeFragmentbyjiaolian2;
import com.example.myapplication.example.fragment.ListuserFragment9;
import com.example.myapplication.example.fragment.WodekongFragment;

public class jiaoliandesike2 extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    Intent intent=new Intent();
    private ListuserFragment9 listuserFragment9;
    private ListcourseFragmentbyuser listcourseFragmentbyuser;
    private ListsikeFragmentbyjiaolian2 listsikeFragmentbyjiaolian2;
    private WodekongFragment wodekongFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yonghugerenkecheng2);
        findViewById();
        initView();
    }

    private void findViewById() {

    }

    private void initView() {
        mContext=this;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

        }
    }
    @Override
    public void onResume(){
        super.onResume();
        listsikeFragmentbyjiaolian2=new ListsikeFragmentbyjiaolian2();
        //listcourseFragmentbyuser=new ListcourseFragmentbyuser();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.listyonghu3, listsikeFragmentbyjiaolian2);
        transaction.commit();
    }

}
