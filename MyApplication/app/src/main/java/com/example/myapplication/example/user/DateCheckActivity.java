package com.example.myapplication.example.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Calendar;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import okhttp3.Call;



public class DateCheckActivity extends AppCompatActivity implements View.OnClickListener {

    private String TITLE_NAME = "每日打卡";
    private View title_back;
    private TextView titleText;

    private Context mContext;
    private DatePicker picker;
    private Button btnPick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_date_check);
        findViewById();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // getDailyCheck();
        echoChecked();
    }

    /**
     * 获取签到记录
     */
    private void getDailyCheck() {
        String url = Constants.BASE_URL + "DailyCheck?method=getCheckedList";
        OkHttpUtils
                .post()
                .url(url)
                .id(2)
                .addParams("userId", Constants.USER.getUserId() + "")
                .build()
                .execute(new MyStringCallback());
    }

    protected void findViewById() {
        picker = (DatePicker) findViewById(R.id.date_date_picker);
        btnPick = (Button) findViewById(R.id.date_btn_check);
    }

    protected void initView() {
        mContext = this;
        btnPick.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_btn_check:
                todayCheck();
                echoChecked();
                break;
        }
    }

    /**
     * 今日打卡
     */
    private void todayCheck() {
        String url = Constants.BASE_URL + "DailyCheck?method=check";
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
            switch (id) {
                case 1:
                    if (response.contains("success")) {
                        Toast.makeText(mContext, "今日打卡成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    if (response.contains("error")) {
                        Toast.makeText(mContext, "无法获取数据", Toast.LENGTH_SHORT).show();
                    } else {
                        String[] dates = response.split(",");
                        for (String s: dates) {
                        }
                    }
                    break;
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(mContext, "网络连接出错", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 已经打卡数据展示
     */
    public void echoChecked() {
        for(int i=0;i<Constants.DAILYCHECKEDLIST.size();i++)
        {
            System.out.println(" 值="+ Constants.DAILYCHECKEDLIST.get(i));
        }
        System.out.println(" 长度长度size="+ Constants.DAILYCHECKEDLIST.size());
        DPCManager.getInstance().setDecorTR(Constants.DAILYCHECKEDLIST);
        Calendar today = Calendar.getInstance();
       // today=today.getInstance();
        picker.setDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1);
        picker.setFestivalDisplay(false);
        picker.setTodayDisplay(true);
        picker.setHolidayDisplay(false);
        picker.setDeferredDisplay(false);
        picker.setMode(DPMode.NONE);
        picker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorTL(Canvas canvas, Rect rect, Paint paint, String data) {
                super.drawDecorTL(canvas, rect, paint, data);


                switch (data) {

                    case "2015-10-5":
                    case "2015-10-7":
                    case "2015-10-9":
                    case "2015-10-11":
                        paint.setColor(Color.GREEN);
                        // canvas.drawRect(rect, paint);
                        BitmapDrawable bmpDraw = (BitmapDrawable) getResources().getDrawable(R.drawable.icon_location_checked);
                        Bitmap bmp = bmpDraw.getBitmap();
                        canvas.drawBitmap(bmp, 10, 10, paint);
                        break;
                    default:
                        paint.setColor(Color.RED);

                        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
                        break;
                }

            }
            @Override
            public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
                paint.setColor(Color.RED);
                System.out.println("canvas="+canvas+"    rect="+rect+"   paint="+paint);
                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
            }
            @Override
            public void drawDecorTR(Canvas canvas, Rect rect, Paint paint, String data) {
                super.drawDecorTR(canvas, rect, paint, data);
                paint.setColor(Color.RED);
                System.out.println("canvas="+canvas+"    rect="+rect+"   paint="+paint+"    data="+data);
                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
            }
        });

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
           // finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
