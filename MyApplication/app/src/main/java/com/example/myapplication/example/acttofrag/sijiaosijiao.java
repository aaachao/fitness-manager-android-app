package com.example.myapplication.example.acttofrag;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.example.fragment.ListcoachFragment_for_sike;
import com.example.myapplication.example.fragment.ListuserFragment9;
import com.example.myapplication.example.fragment.ListyundongjiluFragmentbyxueyuan;
import com.example.myapplication.example.fragment.WodekongFragment;

public class sijiaosijiao extends AppCompatActivity  {

    private Context mContext;
    Intent intent=new Intent();
    private ListuserFragment9 listuserFragment9;
    private ListcoachFragment_for_sike listcoachFragment_for_sike;
    private ListyundongjiluFragmentbyxueyuan listyundongjiluFragmentbyxueyuan;
    private WodekongFragment wodekongFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.kechengdexueyuan);
        initView();
    }
    private void initView() {
        mContext=this;
    }
    @Override
    public void onResume(){
        super.onResume();
        listcoachFragment_for_sike=new ListcoachFragment_for_sike();
        //listyundongjiluFragmentbyxueyuan=new ListyundongjiluFragmentbyxueyuan();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.listyonghu12, listcoachFragment_for_sike);
        transaction.commit();
    }

}
