package com.example.myapplication.example.fragment;
//原生态

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.adapter.CaloriesListAdapter;
import com.example.myapplication.example.entity.Calories;
import com.example.myapplication.example.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class ListcaloriesFragment extends Fragment  {
    private ListView list;
    private Context mContext;
    private CaloriesListAdapter adapter;
    private List<Calories> mList;
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

    private void initView() {
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
        String url = Constants.BASE_URL + "Calories?method=calorieslist";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("userId", Constants.USER.getUserId()+"")
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            //
            Gson gson = new Gson();
            try {
                Type type = new TypeToken<ArrayList<Calories>>() {
                }.getType();
                mList = gson.fromJson(response, type);
            } catch (Exception e) {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                mList = null;
            }
            switch (id) {
                case 1:
                    if (mList != null && mList.size() > 0) {
                        adapter = new CaloriesListAdapter(mContext, mList);
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
