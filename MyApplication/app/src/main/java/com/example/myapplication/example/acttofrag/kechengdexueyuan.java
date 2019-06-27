package com.example.myapplication.example.acttofrag;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.example.fragment.ListuserFragment9;
import com.example.myapplication.example.fragment.ListxueyuanFragmentbyfitness;
import com.example.myapplication.example.fragment.WodekongFragment;

public class kechengdexueyuan extends AppCompatActivity  {

    private Context mContext;
    Intent intent=new Intent();
    private ListuserFragment9 listuserFragment9;
    private ListxueyuanFragmentbyfitness listxueyuanFragmentbyfitness;
    private WodekongFragment wodekongFragment;
    int j=0;
    int z=0;
    int k=0;
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
        listxueyuanFragmentbyfitness=new ListxueyuanFragmentbyfitness();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.listyonghu12, listxueyuanFragmentbyfitness);
        transaction.commit();
    }

}
