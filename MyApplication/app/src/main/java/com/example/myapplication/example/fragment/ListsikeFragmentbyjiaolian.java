package com.example.myapplication.example.fragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
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
import com.example.myapplication.example.adapter.CourseListAdapter;
import com.example.myapplication.example.entity.Course;
import com.example.myapplication.example.entity.Depend;
import com.example.myapplication.example.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class ListsikeFragmentbyjiaolian extends Fragment implements AdapterView.OnItemClickListener {
    private ListView list;
    private Context mContext;
    private CourseListAdapter adapter;
    private List<Course> mList;
    private WodekongFragment wodekongFragment;
    int j=0;
    int z=0;
    int k=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
       // System.out.println("listcourseFragmentbyuser    已经运行");
        View v=inflater.inflate(R.layout.listkecheng2,null);
        findViewByID(v);
        initView();
        return v;
    }

    private void findViewByID(View v) {

        list=(ListView) v.findViewById(R.id.listcourse2);
    }
    private void initView() {
        list.setOnItemClickListener(this);
        mContext=getActivity();
        wodekongFragment=new WodekongFragment();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        add(mList.get(position).getCourseId());
    }
    private void add(int couserId) {
        String url = Constants.BASE_URL + "Depend?method=add3";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("userId", Constants.USER.getUserId() + "")
                .addParams("fitnessId",couserId+"")
                .addParams("coachId", Constants.COURSE.getCoachId() + "")
                .build()
                .execute(new MyStringCallback2());
    }
    public class MyStringCallback2 extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            switch (id) {
                case 1:
                    Depend a = null;
                    try {
                        a = gson.fromJson(response, Depend.class);
                    } catch (JsonSyntaxException e) {
                        a = null;
                    }
                    if (a == null) {
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Toast.makeText(mContext, "添加成功！", Toast.LENGTH_SHORT).show();
                        reLoadNews();
                    }

                    break;
                default:
                    Toast.makeText(mContext, "what?", Toast.LENGTH_SHORT).show();
                    break;
            }
        }


        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(mContext, "网络链接出错！", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();

        reLoadNews();
    }

    private void reLoadNews() {
        //System.out.println("reloadnews");
        String url = Constants.BASE_URL + "Course?method=sikelist";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("coachId",Constants.COURSE.getCoachId()+"")
                .build()
                .execute(new MyStringCallback77());
    }



    public class MyStringCallback77 extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            //
            Gson gson = new Gson();
            try {
                Type type = new TypeToken<ArrayList<Course>>() {
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
                        transaction.replace(R.id.listyonghu3,wodekongFragment);
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
            System.out.println("循环"+mList.get(j).getCoursename());

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
            adapter = new CourseListAdapter(mContext, mList);
            //System.out.println("adapter="+adapter);
            list.setAdapter(adapter);
        }

    }
}
