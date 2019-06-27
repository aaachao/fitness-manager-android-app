package com.example.myapplication.example.fragment;
//删项目

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.example.myapplication.example.entity.Depend;
import com.example.myapplication.example.user.jishi;
import com.example.myapplication.example.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.yanzhenjie.recyclerview.OnItemClickListener;
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

public class ListfitnessFragment3_withright extends Fragment implements AdapterView.OnItemClickListener, OnItemClickListener {
    private com.yanzhenjie.recyclerview.SwipeRecyclerView list;
    private Context mContext;
    private CourseListAdapter_withright adapter;
    private List<Course> mList;
    Intent intent=new Intent();
    //RecyclerView.ItemDecoration itemDecoration=new DefaultItemDecoration(color);
    private com.yanzhenjie.recyclerview.SwipeRecyclerView mRecyclerView;
    //private com.yanzhenjie.recyclerview.SwipeRecyclerView.Adapter mAdapter;

    //private com.yanzhenjie.recyclerview.SwipeRecyclerView.LayoutManager mLayoutManager;
    private WodekongFragment wodekongFragment;

    int j=0;
    int z=0;
    int k=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        //System.out.println("oncreateview");
        super.onCreate(savedInstanceState);
        View v=inflater.inflate(R.layout.listkecheng_withright,null);
        findViewByID(v);
        //initData();
        initView();
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        return v;
    }

    private void initData() {
        LinearLayoutManager manager=new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        //mLayoutManager=new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);

    }

    private void findViewByID(View v) {
        //list=(com.yanzhenjie.recyclerview.SwipeRecyclerView) v.findViewById(R.id.listcourse2);
        mRecyclerView=(com.yanzhenjie.recyclerview.SwipeRecyclerView) v.findViewById(R.id.listcourse2);

        /*mRecyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int adapterPosition) {

            }
        });*/

        /*OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge, int position) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();

                // 左侧还是右侧菜单：
                int direction = menuBridge.getDirection();
                // 菜单在Item中的Position：
                int menuPosition = menuBridge.getPosition();
            }
        };*/
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
                else if(menuPosition==1)
                {
                    Course baa=new Course();
                    baa.setCourseId(mList.get(position).getCourseId());
                    baa.setTeachername(mList.get(position).getTeachername());
                    baa.setCourseteach(mList.get(position).getCourseteach());
                    baa.setCalories(mList.get(position).getCalories());
                    Constants.COURSE=baa;
                    mContext=getActivity();
                    intent.setClass(mContext, jishi.class);
                    startActivity(intent);
                }
            }
        }
    };

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
                SwipeMenuItem addItem = new SwipeMenuItem(mContext).setBackground(
                        R.drawable.selector_yellow)
                        .setImage(R.drawable.d555)
                        .setText("训练")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
        }
    };


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        delete(mList.get(position).getCourseId());
    }
    private void delete(int couserId) {
        String url = Constants.BASE_URL + "Depend?method=delete";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("userId", Constants.USER.getUserId() + "")
                .addParams("fitnessId",couserId+"")
                .build()
                .execute(new MyStringCallback2());
    }

    @Override
    public void onItemClick(View view, int adapterPosition) {

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
                        Toast.makeText(mContext, "删除项目成功！", Toast.LENGTH_SHORT).show();
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

    private void initView() {
       // list.setOnItemClickListener(this);

        mContext=getActivity();
    }
    @Override
    public void onResume()
    {
        super.onResume();

        reLoadNews();
    }

    private void reLoadNews() {
        //String url = Constants.BASE_URL + "Depend?method=mydepend";
        String url = Constants.BASE_URL + "Depend?method=mydepend2";
        OkHttpUtils
                .post()
                .url(url)
                .id(1)
                .addParams("userId", Constants.USER.getUserId() + "")
                .build()
                .execute(new MyStringCallback());
    }


    public class MyStringCallback extends StringCallback {
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
                    else if (mList.size()==0)
                    {
                        wodekongFragment=new WodekongFragment();
                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.yonghujiemian,wodekongFragment);
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
