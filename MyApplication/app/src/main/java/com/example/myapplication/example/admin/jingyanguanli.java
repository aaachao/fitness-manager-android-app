package com.example.myapplication.example.admin;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.example.fragment.FoundFragment_withright;
import com.example.myapplication.example.fragment.WodeFragment;

public class jingyanguanli extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    Intent intent = new Intent();
    private FoundFragment_withright foundFragment;
    private WodeFragment wodeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jingyanguanli);
        foundFragment=new FoundFragment_withright();
        refeachFragment(R.id.found);
        findViewById();
        initView();
    }

    private void findViewById() {
        //findViewById(R.id.found).setOnClickListener(this);
    }

    private void initView() {
        mContext = this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.found:
                refeachFragment(R.id.found);
                break;
        }
    }

    private void refeachFragment(int btnId) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (btnId) {
            case R.id.found:
                transaction.replace(R.id.yonghujiemian,foundFragment);
                break;
        }
        transaction.commit();
    }

}
