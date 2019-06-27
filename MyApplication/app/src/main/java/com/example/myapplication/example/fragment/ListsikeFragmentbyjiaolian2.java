package com.example.myapplication.example.fragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.adapter.CourseListAdapter_withright;
import com.example.myapplication.example.entity.Course;
import com.example.myapplication.example.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class ListsikeFragmentbyjiaolian2 extends Fragment implements AdapterView.OnItemClickListener {
    private com.yanzhenjie.recyclerview.SwipeRecyclerView list;
    private Context mContext;
    private CourseListAdapter_withright adapter;
    private List<Course> mList;
    private com.yanzhenjie.recyclerview.SwipeRecyclerView mRecyclerView;
    private WodekongFragment wodekongFragment;
    int j=0;
    int z=0;
    int k=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
       // System.out.println("listcourseFragmentbyuser    已经运行");
        View v=inflater.inflate(R.layout.listkecheng2_withright,null);
        findViewByID(v);
        initView();
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        return v;
    }
    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int width = getResources().getDimensionPixelSize((R.dimen.dp_70));
            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 1. 根据ViewType来决定哪一个item该如何添加菜单。
            // 2. 更多的开发者需要的是根据position，因为不同的ViewType之间不会有缓存优化效果。
            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext).setBackground(
                    R.drawable.selector_red)
                    .setImage(R.drawable.d2bef2ce57765e7b2f06a5806f398ee2)
                    .setText("删除")
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
        }
    };
    private void initData() {
        LinearLayoutManager manager=new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        //mLayoutManager=new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);

    }
    private void findViewByID(View v) {
        mRecyclerView=(com.yanzhenjie.recyclerview.SwipeRecyclerView) v.findViewById(R.id.listcourse2);
        mRecyclerView.setOnItemMenuClickListener(mItemMenuClickListener);
    }
    private OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                if(menuPosition==0){
                    delete(mList.get(position).getCourseId());
                }
            }
        }
    };
    private void delete(int courseId) {
        // 服务端验证
        String url = Constants.BASE_URL + "Course?method=delete2";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("mingcheng", courseId+"")
                .id(1)
                .build()
                .execute(new MyStringCallback99());
    }


    public class MyStringCallback99 extends StringCallback {

        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            switch (id) {
                case 1:
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        reLoadNews();
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
        //list.setOnItemClickListener(this);
        mContext=getActivity();
        wodekongFragment=new WodekongFragment();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //add(mList.get(position).getCourseId());
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
            adapter = new CourseListAdapter_withright(mContext, mList);
            //System.out.println("adapter="+adapter);
            initData();
        }

    }
}
