package com.example.myapplication.example.kechengbiao;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.R;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.OnSlideBuildAdapter;
import com.zhuangfei.timetable.view.WeekView;

public class kechengbiao extends AppCompatActivity {
    TimetableView mtimetableView;
    WeekView mweekView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.kechengbiao);
        initView();
    }
    protected void hideWeekView(){
        mweekView.isShow(false);
    }
    private void showWeekends() {
        mtimetableView.isShowWeekends(true).updateView();
    }
    protected void setMaxItem(int num) {
        mtimetableView.maxSlideItem(num).updateSlideView();
    }
    protected void showTime() {
        String[] times = new String[]{
                "08:00-10:00", "10:00-12:00", "13:00-15:00", "15:00-17:00",
                "17:00-19:00"
        };
        OnSlideBuildAdapter listener= (OnSlideBuildAdapter) mtimetableView.onSlideBuildListener();
        listener.setTimes(times)
                .setTimeTextColor(Color.BLACK);
        mtimetableView.updateSlideView();
    }
    protected void hideDateView() {
        mtimetableView.hideDateView();
    }
    private void initView() {
        mweekView=findViewById(R.id.id_weekview);
        mtimetableView=findViewById(R.id.id_timetablview);
        hideWeekView();
        showWeekends();
        showTime();
        setMaxItem(5);
        //hideDateView();
    }
}

