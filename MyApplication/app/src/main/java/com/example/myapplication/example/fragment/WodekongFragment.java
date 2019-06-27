package com.example.myapplication.example.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.example.adapter.CourseListAdapter;
import com.example.myapplication.example.entity.Course;

import java.util.List;

public class WodekongFragment extends Fragment {
    private ListView list;
    private Context mContext;
    private CourseListAdapter adapter;
    private List<Course> mList;
    private ListcourseFragment3 listcourseFragment3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        //System.out.println("oncreateview");
        View v=inflater.inflate(R.layout.kong,null);
        return v;
    }
}
