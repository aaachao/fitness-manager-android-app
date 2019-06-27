package com.example.myapplication.example.acttofrag;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.example.fragment.ListcoachFragmentbyfitness;
import com.example.myapplication.example.fragment.ListuserFragment9;
import com.example.myapplication.example.fragment.WodekongFragment;

public class kechengdeyonghu extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    Intent intent=new Intent();
    private ListuserFragment9 listuserFragment9;
    private ListcoachFragmentbyfitness listcoachFragmentbyfitness;
    private WodekongFragment wodekongFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.kechengdeyonghu);
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
                intent.setClass(getApplicationContext(),kechengdeyonghutianjia.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        listcoachFragmentbyfitness=new ListcoachFragmentbyfitness();
        FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.listyonghu12, listcoachFragmentbyfitness);
        transaction.commit();
    }

}
