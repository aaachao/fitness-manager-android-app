package com.example.myapplication.example.user;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.fragment.FoundFragment;
import com.example.myapplication.example.fragment.ListfitnessFragment2;
import com.example.myapplication.example.fragment.ListfitnessFragment3;
import com.example.myapplication.example.fragment.ListfitnessFragment3_withright;
import com.example.myapplication.example.fragment.ListfitnessFragment4;
import com.example.myapplication.example.fragment.WodeFragment;
import com.example.myapplication.example.utils.AppManager;

public class user_admin extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    Intent intent = new Intent();
    private ListfitnessFragment2 listfitnessFragment2;
    private ListfitnessFragment3 listfitnessFragment3;
    private ListfitnessFragment3_withright listfitnessFragment3_withright;
    private ListfitnessFragment4 listfitnessFragment4;
    private FoundFragment foundFragment;
    private WodeFragment wodeFragment;
    private long mExitTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        listfitnessFragment2=new ListfitnessFragment2();
        listfitnessFragment3=new ListfitnessFragment3();
        listfitnessFragment3_withright=new ListfitnessFragment3_withright();
        listfitnessFragment4=new ListfitnessFragment4();
        foundFragment=new FoundFragment();
        wodeFragment=new WodeFragment();
        refeachFragment(R.id.jianshenkecheng);
        findViewById();
        initView();
    }

    private void findViewById() {
        findViewById(R.id.jianshenkecheng).setOnClickListener(this);
        findViewById(R.id.sirenjiaolian).setOnClickListener(this);
        //findViewById(R.id.kaishixunlian).setOnClickListener(this);
        findViewById(R.id.wode).setOnClickListener(this);
        findViewById(R.id.found).setOnClickListener(this);
    }

    private void initView() {
        mContext = this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jianshenkecheng:
                refeachFragment(R.id.jianshenkecheng);
                break;
            case R.id.sirenjiaolian:
                refeachFragment(R.id.sirenjiaolian);
                break;
            /*case R.id.kaishixunlian:
                refeachFragment(R.id.kaishixunlian);
                break;*/
            case R.id.wode:
                refeachFragment(R.id.wode);
                break;
            case R.id.found:
                refeachFragment(R.id.found);
                break;
        }
    }

    private void refeachFragment(int btnId) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (btnId) {
            case R.id.jianshenkecheng:
                transaction.replace(R.id.yonghujiemian, listfitnessFragment2);
                break;
            case R.id.sirenjiaolian:
                //transaction.replace(R.id.yonghujiemian,listfitnessFragment3 );
                transaction.replace(R.id.yonghujiemian,listfitnessFragment3_withright );
                break;
            /*case R.id.kaishixunlian:
                transaction.replace(R.id.yonghujiemian,listfitnessFragment4 );
                break;*/
            case R.id.wode:
                transaction.replace(R.id.yonghujiemian,wodeFragment);
                break;
            case R.id.found:
                transaction.replace(R.id.yonghujiemian,foundFragment);
                break;
        }
        transaction.commit();
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
