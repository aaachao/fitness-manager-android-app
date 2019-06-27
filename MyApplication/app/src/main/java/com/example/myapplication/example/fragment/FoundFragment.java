package com.example.myapplication.example.fragment;

import android.app.Fragment;
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
import com.example.myapplication.example.adapter.FoundNewsAdapter;
import com.example.myapplication.example.entity.NewsListForFound;
import com.example.myapplication.example.user.NewsDetailActivity;
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


public class FoundFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView foundList;
    private FoundNewsAdapter adapter;
    private Context mContext;
    int j=0;
    int z=0;
    int k=0;
    private List<NewsListForFound> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_found, null);
        findViewById(v);
        initView();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoadNews();
    }

    private void reLoadNews() {
        String url = Constants.BASE_URL + "News?method=getNewsList";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .build()
                .execute(new MyStringCallback());
    }

    public void findViewById(View v) {
        foundList = (ListView) v.findViewById(R.id.found_list);
    }

    public void initView() {
        mContext = getActivity();
        foundList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NewsDetailActivity.class);
        intent.putExtra("newsId", mList.get(position).getNewsId());
        startActivity(intent);
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onResponse(String response, int id) {

            Gson gson = new Gson();
            try {
                Type type = new TypeToken<ArrayList<NewsListForFound>>() {
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
            System.out.println("333333333333333"+mList.get(j).getImage());
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
                        }
                    });
        }
        else
        {
            adapter = new FoundNewsAdapter(mContext, mList);
            foundList.setAdapter(adapter);
        }

    }
}
