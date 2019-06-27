package com.example.myapplication.example.acttofrag;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.example.fragment.ListcoachFragment3;
import com.example.myapplication.example.fragment.ListuserFragment9;
import com.example.myapplication.example.fragment.WodekongFragment;

public class kechengdeyonghutianjia extends AppCompatActivity  {

    private Context mContext;
    Intent intent=new Intent();
    private ListuserFragment9 listuserFragment9;
    private ListcoachFragment3 listcoachFragment3;
    private WodekongFragment wodekongFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kechengdeyonghu2);
        listcoachFragment3=new ListcoachFragment3();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.listyonghu122, listcoachFragment3);
        transaction.commit();
        initView();
    }


    private void initView() {
        mContext=this;
    }

}
