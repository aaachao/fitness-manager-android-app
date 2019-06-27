package com.example.myapplication.example.admin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.MainActivity;
import com.example.myapplication.example.utils.AppManager;

public class activity_admin_main extends AppCompatActivity implements View.OnClickListener {
    Intent intent=new Intent();
    private long mExitTime;
    private Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        findViewById();
        initView();
    }

    private void findViewById() {
        findViewById(R.id.kechengguanli).setOnClickListener(this);
        findViewById(R.id.yonghuguanli).setOnClickListener(this);
        findViewById(R.id.me_item_exit).setOnClickListener(this);
        findViewById(R.id.fabutongzhi).setOnClickListener(this);
        findViewById(R.id.jingyanguanli).setOnClickListener(this);
        findViewById(R.id.sijiaoguanli).setOnClickListener(this);
    }
    private void initView() {
        mContext=this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kechengguanli:
                intent.setClass(getApplicationContext(),kechengguanli.class);
                startActivity(intent);
                break;
            case R.id.yonghuguanli:
                intent.setClass(getApplicationContext(),yonghuguanli.class);
                startActivity(intent);
                break;
            case R.id.sijiaoguanli:
                intent.setClass(getApplicationContext(),sijiaoguanli.class);
                startActivity(intent);
                break;
            case R.id.jingyanguanli:
                intent.setClass(getApplicationContext(),jingyanguanli.class);
                startActivity(intent);
                break;
            case R.id.fabutongzhi:
                intent.setClass(getApplicationContext(),fabutongzhi.class);
                startActivity(intent);
                break;
            case R.id.me_item_exit:
                SystemClock.sleep(500);
                AppManager.getInstance().killAllActivity();
                intent.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 800) {
                Toast.makeText(mContext, "再按一次退出APP", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
                return true;
            } else {
                AppManager.getInstance().killAllActivity();
                AppManager.getInstance().AppExit(this);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


}
