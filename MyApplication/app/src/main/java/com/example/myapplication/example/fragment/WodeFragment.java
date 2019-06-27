package com.example.myapplication.example.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.MainActivity;
import com.example.myapplication.example.acttofrag.sijiaosijiao;
import com.example.myapplication.example.acttofrag.xunlianjilu;
import com.example.myapplication.example.adapter.CourseListAdapter;
import com.example.myapplication.example.entity.Course;
import com.example.myapplication.example.entity.User;
import com.example.myapplication.example.user.BeforeDateCheckActivity;
import com.example.myapplication.example.user.ReleaseNewsActivity;
import com.example.myapplication.example.user.myziliao;
import com.example.myapplication.example.utils.AppManager;
import com.example.myapplication.example.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class WodeFragment extends Fragment implements View.OnClickListener {
    private ListView list;
    private Context mContext;
    private CourseListAdapter adapter;
    private List<Course> mList;
    private ListcourseFragment3 listcourseFragment3;
    private TextView exit;
    private LinearLayout gerenziliao;
    private LinearLayout xunlianjilu;
    private LinearLayout sijiao;
    private LinearLayout fabu;
    private LinearLayout daka;
    private TextView exerciseTimeTextView;
    private TextView recordDaysTextView;
    private ImageView photo;
    private File imageFile;
    private ArrayList<Uri> path;
    int yesno;
    Intent intent=new Intent();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        //System.out.println("oncreateview");
        View v=inflater.inflate(R.layout.wode,null);
        findViewById(v);
        initView();
        return v;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        getRecords();
        fou();
        found();
    }

    private void fou() {
        String url = Constants.BASE_URL + "User?method=found2";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("username",Constants.USER.getUserId()+"" )
                .build()
                .execute(new MyStringCallback2());

    }
    public class MyStringCallback2 extends StringCallback {

        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            switch (id) {
                case 1:
                    User user = gson.fromJson(response, User.class);
                    Constants.USER = user;
                    break;
                default:
                    Toast.makeText(getActivity(), "what?", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(getActivity(), "网络链接出错！", Toast.LENGTH_SHORT).show();
        }
    }
    private void found() {
        //System.out.println( "GGJHGJHGJHGJHGHJG"+Constants.USER.getImage());
        String url = Constants.BASE_URL + "Download?method=getNewsImage";
        OkHttpUtils
                .get()//
                .url(url)//
                .addParams("imageName", Constants.USER.getImage())
                .build()//
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        Toast.makeText(mContext, "无法获取图片！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Bitmap bitmap, int i) {
                        photo.setImageBitmap(bitmap);
                        //holder.bg.setImageBitmap(bitmap);
                    }
                });
    }
    private void initView() {
        exit.setOnClickListener(this);
        gerenziliao.setOnClickListener(this);
        xunlianjilu.setOnClickListener(this);
        fabu.setOnClickListener(this);
        daka.setOnClickListener(this);
        sijiao.setOnClickListener(this);
        this.photo.setOnClickListener(this);
    }
    private void getRecords() {
        String url = Constants.BASE_URL + "DailyCheck?method=getHomepageTotalRecord";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", Constants.USER.getUserId() + "")
                .id(1)
                .build()
                .execute(new MyStringCallback());
    }
    public class MyStringCallback extends StringCallback {

        @Override
        public void onResponse(String response, int id) {
            switch (id) {
                case 1:
                    String[] items = response.split(":");
                    exerciseTimeTextView.setText(items[1]);
                    recordDaysTextView.setText(items[0]);
                    break;

                default:
                    Toast.makeText(getActivity(), "what?", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(getActivity(), "网络链接出错！", Toast.LENGTH_SHORT).show();
        }
    }

    private void findViewById(View v) {
        exit=(TextView)v.findViewById(R.id.me_item_exit);
        gerenziliao=(LinearLayout)v.findViewById(R.id.gerenziliao);
        xunlianjilu=(LinearLayout)v.findViewById(R.id.xunlianjilu);
        fabu=(LinearLayout)v.findViewById(R.id.fabu);
        daka=(LinearLayout)v.findViewById(R.id.daka);
        exerciseTimeTextView = (TextView) v.findViewById(R.id.me_exercise_time);
        recordDaysTextView = (TextView) v.findViewById(R.id.me_record_days);
        photo=(ImageView)v.findViewById(R.id.addphoto);
        sijiao=(LinearLayout)v.findViewById(R.id.sijiao);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gerenziliao:
                startActivity(new Intent(getActivity(), myziliao.class));
                break;
            case R.id.me_item_exit:
                SystemClock.sleep(500);
                AppManager.getInstance().killAllActivity();
                startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            case R.id.sijiao:
                startActivity(new Intent(getActivity(), sijiaosijiao.class));
                break;
            case R.id.xunlianjilu:
                startActivity(new Intent(getActivity(), xunlianjilu.class));
                break;
            case R.id.fabu:
                startActivity(new Intent(getActivity(), ReleaseNewsActivity.class));
                break;
            case R.id.daka:
                startActivity(new Intent(getActivity(), BeforeDateCheckActivity.class));
                break;
        }
    }
}
