package com.example.myapplication.example.fragment;
//点击进入用户所选课程

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.adapter.UserListAdapter;
import com.example.myapplication.example.entity.Course;
import com.example.myapplication.example.entity.User;
import com.example.myapplication.example.teacher.mykecheng2;
import com.example.myapplication.example.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class ListuserFragment9 extends Fragment implements AdapterView.OnItemClickListener {
    private ListView list;
    private Context mContext;
    private UserListAdapter adapter;
    private List<User> mList;
    private ListcourseFragmentbyuser listcourseFragmentbyuser;
    private WodekongFragment wodekongFragment;
    Intent intent =new Intent();
    int j=0;
    int z=0;
    int k=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        //System.out.println("oncreateview");
        View v=inflater.inflate(R.layout.listyonghu,null);
        findViewByID(v);
        initView();
        return v;
    }

    private void findViewByID(View v) {
        list=(ListView) v.findViewById(R.id.listuser);
    }

    private void initView() {
        list.setOnItemClickListener(this);
        mContext=getActivity();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    /*   if (mList.get(position).getRole().equals("admin"))
        {
            Toast.makeText(mContext, "对不起，该用户为管理员！", Toast.LENGTH_SHORT).show();
        }
        else if (mList.get(position).getRole().equals("user"))
        {
            listcourseFragmentbyuser=new ListcourseFragmentbyuser();
            User baa=new User();
            baa.setUserId(mList.get(position).getUserId());
            Constants.USER=baa;
            mContext=getActivity();
            intent.setClass(mContext, yonghugerenkecheng.class);
            startActivity(intent);
        }
        else if (mList.get(position).getRole().equals("teacher"))
        {
            Constants.USER=mList.get(position);
          //  System.out.println("Constants.USER.getUsername()==="+Constants.USER.getUsername());
            if (mList.get(position).getStatus()==0)
            {
                startActivity(new Intent(getActivity(), mykecheng.class));
            }
            else if (mList.get(position).getStatus()==1){
                course();
          //      System.out.println("Constants.COURSE.getCoursename()==="+Constants.COURSE.getCoursename());

            }

        } */
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
                .execute(new MyStringCallback7());
    }
    public class MyStringCallback7 extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            Course a=null;
            a = gson.fromJson(response, Course.class);
            Constants.COURSE=a;
           // System.out.println("22222222222222  Constants.COURSE.getCoursename()==="+Constants.COURSE.getCoursename());
            startActivity(new Intent(getActivity(), mykecheng2.class));
        }


        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {

        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        reLoadNews();
    }

    private void reLoadNews() {
        String url = Constants.BASE_URL + "User?method=userlist";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .build()
                .execute(new MyStringCallback());
    }



    public class MyStringCallback extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            //
            Gson gson = new Gson();
            try {
                Type type = new TypeToken<ArrayList<User>>() {
                }.getType();
                mList = gson.fromJson(response, type);
            } catch (Exception e) {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                mList = null;
            }
            switch (id) {
                case 1:
                    if (mList != null && mList.size() > 0) {
                        forfor(0,mList.size());

                    }
                    else
                    {
                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.listyonghu,wodekongFragment);
                        transaction.commit();
                    }
                    break;
                default:
                    Toast.makeText(mContext, "what！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(mContext, "网络链接出错！", Toast.LENGTH_SHORT).show();
        }
    }
    public synchronized  void forfor(int j,int length)
    {
        z=length;
        k=j;
        if(j<length)
        {
            String url = Constants.BASE_URL + "Download?method=getNewsImage";
            OkHttpUtils
                    .get()//
                    .url(url)//
                    .addParams("imageName", mList.get(j).getImage())
                    .build()//
                    .execute(new BitmapCallback() {
                        @Override
                        public void onError(Call call, Exception e, int i) {
                            Toast.makeText(mContext, "无法获取图片！", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onResponse(Bitmap bitmap, int i) {
                            System.out.println("图片"+k);
                            mList.get(k).setBitmap(bitmap);
                            k++;
                            System.out.println("k++="+k);
                            forfor(k,z);
                            return ;
                            //holder.bg.setImageBitmap(bitmap);
                        }
                    });
        }
        else
        {
            adapter = new UserListAdapter(mContext, mList);
            list.setAdapter(adapter);
        }

    }
}
