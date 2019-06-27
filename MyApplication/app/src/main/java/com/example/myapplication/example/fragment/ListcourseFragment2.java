package com.example.myapplication.example.fragment;
//加课程
import android.app.Fragment;
import android.content.Context;
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
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class ListcourseFragment2 extends Fragment implements AdapterView.OnItemClickListener {
    private ListView list;
    private Context mContext;
    private CourseListAdapter adapter;
    private List<Course> mList;
    private WodekongFragment wodekongFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        //System.out.println("oncreateview");
        View v=inflater.inflate(R.layout.listkecheng,null);
        findViewByID(v);
        initView();
        return v;
    }

    private void findViewByID(View v) {
        list=(ListView) v.findViewById(R.id.listcourse);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        add(mList.get(position).getCourseId());
    }

    private void add(int couserId) {
        String url = Constants.BASE_URL + "Depend?method=add";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("userId", Constants.USER.getUserId() + "")
                .addParams("courseId",couserId+"")
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
                        //System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇a="+a);
                        Toast.makeText(mContext, "添加课程成功！", Toast.LENGTH_SHORT).show();
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


    private void initView() {
        list.setOnItemClickListener(this);
        mContext=getActivity();
    }
    @Override
    public void onResume()
    {
        super.onResume();
        reLoadNews();
    }
    private void reLoadNews() {
        //System.out.println("reloadnews");
        String url = Constants.BASE_URL + "Course?method=courselist";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .build()
                .execute(new MyStringCallback());
    }


    public class MyStringCallback extends StringCallback{
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
                        adapter = new CourseListAdapter(mContext, mList);
                        list.setAdapter(adapter);
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
}