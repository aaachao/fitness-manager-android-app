package com.example.myapplication.example.acttofrag;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.example.fragment.ListcourseFragment2;
import com.example.myapplication.example.fragment.ListuserFragment9;
import com.example.myapplication.example.fragment.WodekongFragment;

public class yonghugerenkechengtianjia extends AppCompatActivity  {

    private Context mContext;
    Intent intent=new Intent();
    private ListuserFragment9 listuserFragment9;
    private ListcourseFragment2 listcourseFragment2;
    private WodekongFragment wodekongFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yonghugerenkecheng2);
        listcourseFragment2=new ListcourseFragment2();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.listyonghu3, listcourseFragment2);
        transaction.commit();
        initView();
    }


    private void initView() {
        mContext=this;
    }

}
