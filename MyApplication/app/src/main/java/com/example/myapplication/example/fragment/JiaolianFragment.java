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
import com.example.myapplication.example.adapter.CourseListAdapter;
import com.example.myapplication.example.entity.Course;
import com.example.myapplication.example.entity.User;
import com.example.myapplication.example.teacher.sikeguanli;
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

public class JiaolianFragment extends Fragment implements View.OnClickListener {
    private ListView list;
    private Context mContext;
    private CourseListAdapter adapter;
    private List<Course> mList;
    private ListcourseFragment3 listcourseFragment3;
    private TextView exit;
    private LinearLayout gerenziliao;
    private LinearLayout fabu;
    private LinearLayout sike;
    Intent intent=new Intent();
    private ImageView photo;
    private File imageFile;
    private ArrayList<Uri> path;
    int yesno;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        //System.out.println("oncreateview");
        View v=inflater.inflate(R.layout.jiaolian,null);
        findViewById(v);
        initView();
        return v;
    }

    private void initView() {
        exit.setOnClickListener(this);
        gerenziliao.setOnClickListener(this);
        fabu.setOnClickListener(this);
        sike.setOnClickListener(this);
        this.photo.setOnClickListener(this);
    }

    private void findViewById(View v) {
        exit=(TextView)v.findViewById(R.id.me_item_exit);
        gerenziliao=(LinearLayout)v.findViewById(R.id.gerenziliao);
        fabu=(LinearLayout)v.findViewById(R.id.fabu);
        sike=(LinearLayout)v.findViewById(R.id.sike);
        photo=(ImageView)v.findViewById(R.id.addphoto);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        //course();
        status();
        found();
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
    private void status() {
        checkUser();
    }

    private void checkUser() {
        String url = Constants.BASE_URL + "User?method=found";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("username",Constants.USER.getUsername())
                .build()
                .execute(new MyStringCallback5());
    }
    public class MyStringCallback5 extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            User user = null;
            user=gson.fromJson(response, User.class);
            Constants.USER = user;
        }
        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(mContext, "网络连接出错", Toast.LENGTH_SHORT).show();
        }
    }
    private void course() {
        //System.out.println("Constants.USER.getUsername()="+Constants.USER.getUsername());
        String url = Constants.BASE_URL + "Course?method=found2";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("jiaolian",Constants.USER.getUsername() + "")
                .build()
                .execute(new MyStringCallback2());
    }
    public class MyStringCallback2 extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            Course a=null;
            a = gson.fromJson(response, Course.class);
            //System.out.println("a.getCourseId(()="+a.getCourseId());
            Constants.COURSE=a;
        }


        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {

        }
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
            case R.id.sike:
                startActivity(new Intent(getActivity(), sikeguanli.class));
                break;
            case R.id.fabu:
                startActivity(new Intent(getActivity(), ReleaseNewsActivity.class));
                break;
        }
    }
}