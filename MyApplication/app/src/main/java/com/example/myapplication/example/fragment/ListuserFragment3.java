package com.example.myapplication.example.fragment;
//加用户
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
import com.example.myapplication.example.adapter.UserListAdapter;
import com.example.myapplication.example.entity.Depend;
import com.example.myapplication.example.entity.User;
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

public class ListuserFragment3 extends Fragment implements AdapterView.OnItemClickListener {
    private ListView list;
    private Context mContext;
    private UserListAdapter adapter;
    private List<User> mList;
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
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
     //   System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇userid="+Constants.USER.getUserId());
     //   System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇courseid="+mList.get(position).getCourseId());
        add(mList.get(position).getUserId());
    }

    private void add(int userId) {
        String url = Constants.BASE_URL + "Depend?method=add2";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("userId", userId+"")
                .addParams("courseId",Constants.COURSE.getCourseId() + "")
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
                        Toast.makeText(mContext, "添加用户成功！", Toast.LENGTH_SHORT).show();
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
        String url = Constants.BASE_URL + "User?method=userlist2";
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
                        adapter = new UserListAdapter(mContext, mList);
                        //System.out.println("哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇哇adapter="+adapter);
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
