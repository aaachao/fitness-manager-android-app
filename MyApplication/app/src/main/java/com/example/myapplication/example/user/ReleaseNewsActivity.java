package com.example.myapplication.example.user;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.utils.Constants;
import com.google.gson.Gson;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.Call;



public class ReleaseNewsActivity extends AppCompatActivity implements View.OnClickListener {

    private View title_back;
    private TextView titleText;

    private Context mContext;

    private EditText title;
    private EditText content;
    private ImageView photo;
    private Button release;

    private ArrayList<Uri> path;

    private File imageFile;

    @Override
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_add_news);
        findViewById();
        initView();
    }


    protected void findViewById() {
        this.title = findViewById(R.id.add_news_et_share_title);
        this.content = findViewById(R.id.add_news_et_share_content);
        this.photo =findViewById(R.id.add_news_iv_photo);
        this.release = findViewById(R.id.add_news_btn_release);
    }

    protected void initView() {
        mContext = this;

        this.photo.setOnClickListener(this);
        this.release.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_news_iv_photo:
                getPhoto();
                break;
            case R.id.add_news_btn_release:
                checkInfo();
                break;
        }
    }


    private void checkInfo() {
        String titleStr = title.getText().toString();
        String contentStr = content.getText().toString();
        if (TextUtils.isEmpty(titleStr)) {
            Toast.makeText(mContext, "请输入一个标题", Toast.LENGTH_SHORT).show();
            title.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(contentStr)) {
            Toast.makeText(mContext, "请输入内容", Toast.LENGTH_SHORT).show();
            content.requestFocus();
            return;
        }
        releaseNews();
    }

    private void releaseNews() {
        String titleStr = title.getText().toString();
        String contentStr = content.getText().toString();
        String url;
        if (imageFile != null && imageFile.exists()) {
            url = Constants.BASE_URL + "News?method=releaseNewsWithImage";
            OkHttpUtils
                    .post()
                    .addFile("image", imageFile.getName(), imageFile)
                    .url(url)
                    .id(1)
                    .addHeader("content-Type", "multipart/form-data; boundary=" + UUID.randomUUID().toString())
                    .addParams("title", titleStr)
                    .addParams("content", contentStr)
                    .addParams("userId", Constants.USER.getUserId() + "")
                    .build()
                    .execute(new MyStringCallback());
        } else {
            url = Constants.BASE_URL + "News?method=releaseNewsWithoutImage";
            OkHttpUtils
                    .post()
                    .url(url)
                    .id(1)
                    .addParams("title", titleStr)
                    .addParams("content", contentStr)
                    .addParams("userId", Constants.USER.getUserId() + "")
                    .build()
                    .execute(new MyStringCallback());
        }
    }

    private void getPhoto() {
        FishBun.with(ReleaseNewsActivity.this)
                .setPickerCount(5) //Deprecated
                .setMaxCount(1)
                .setMinCount(1)
                .setPickerSpanCount(4)
                .setActionBarColor(Color.parseColor("#DB4A37"), Color.parseColor("#DB4A37"), false)
                .setActionBarTitleColor(Color.parseColor("#ffffff"))
                .setAlbumSpanCount(2, 4)
                .setButtonInAlbumActivity(false)
                .setCamera(true)
                .setReachLimitAutomaticClose(true)
                .setAllViewTitle("所有图片")
                .setActionBarTitle("图片库")
                .textOnImagesSelectionLimitReached("已选数量受限！")
                .textOnNothingSelected("你还没有选择图片哟~")
                .startAlbum();
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);
        switch (requestCode) {
            case Define.ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
//                    path = imageData.getStringArrayListExtra(Define.INTENT_PATH);

                    //You can get image path(ArrayList<String>) Under version 0.6.2

                    path = imageData.getParcelableArrayListExtra(Define.INTENT_PATH);

                    Uri uri = path.get(0);
                    System.out.println("uri="+uri);

                    photo.setImageURI(uri);

                    String[] proj = {MediaStore.Images.Media.DATA};

                    Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);

                    int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                    actualimagecursor.moveToFirst();

                    String img_path = actualimagecursor.getString(actual_image_column_index);

                    imageFile = new File(img_path);

                    //You can get image path(ArrayList<Uri>) Version 0.6.2 or later
                    break;
                }
        }
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            switch (id) {
                case 1:
                    if (response.contains("success")) {
                        Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(mContext, "请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    Toast.makeText(mContext, "what", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onError(Call arg0, Exception arg1, int arg2) {
            Toast.makeText(mContext, "网络连接出错", Toast.LENGTH_SHORT).show();
        }
    }
}
