package com.example.myapplication.example.user;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.entity.User;
import com.example.myapplication.example.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.Call;

public class myziliao extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private EditText mymima;
    private EditText myxingming;
    private EditText mydianhua;
    private EditText data;
    private EditText weight2;
    private ImageView photo;
    private File imageFile;
    private ArrayList<Uri> path;
    int yesno;
    Intent intent=new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myziliaoi);
        findViewById();
        initView();
       found();
    }
    private void initView() {
        this.photo.setOnClickListener(this);
        mContext=this;
    }

    private void findViewById() {
        findViewById(R.id.querenxiugai).setOnClickListener(this);
        data=findViewById(R.id.data);
        mymima=findViewById(R.id.mymima);
        myxingming = findViewById(R.id.myxingming);
       mydianhua = findViewById(R.id.mydianhua);
       weight2=findViewById(R.id.weight);
        photo=findViewById(R.id.addphoto);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.querenxiugai:
                update();
                break;
            case R.id.addphoto:
                yesno=1;
                getPhoto();
                break;
        }
    }
    private void getPhoto() {
        FishBun.with(myziliao.this)
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
    private void found() {
        String username=Constants.USER.getUsername();
        //d.判断用户名密码是否为空，不为空请求服务器（省略，默认请求成功）i

        // 服务端验证
        String url = Constants.BASE_URL + "User?method=found";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("username", username)
                .id(1)
                .build()
                .execute(new MyStringCallback2());
    }
    public class MyStringCallback2 extends StringCallback {

        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            switch (id) {
                case 1:
                    User a = null;
                    try {
                        a = gson.fromJson(response, User.class);
                    } catch (JsonSyntaxException e) {
                        a = null;
                    }
                    if (a == null) {
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        mymima.setText(a.getPassword());
                        mydianhua.setText(a.getPhone());
                        myxingming.setText(a.getName());
                        weight2.setText(a.getWeight()+"");
                        data.setText(a.getData());
                        String url = Constants.BASE_URL + "Download?method=getNewsImage";
                        OkHttpUtils
                                .get()//
                                .url(url)//
                                .addParams("imageName", a.getImage())
                                .build()//
                                .execute(new BitmapCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int i) {
                                        Toast.makeText(mContext, "无法获取图片！", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(Bitmap bitmap, int i) {
                                        photo.setImageBitmap(bitmap);
                                        //holder.bg.setImageBitmap(bitmap);
                                    }
                                });
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


    private void update() {
        String username =Constants.USER.getUsername();
        String password = mymima.getText().toString().trim();
        String phone = mydianhua.getText().toString().trim();
        String name = myxingming.getText().toString().trim();
        String weight=weight2.getText().toString().trim();
        String data2=data.getText().toString();
        if ( TextUtils.isEmpty(password)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(name) ||TextUtils.isEmpty(weight)||TextUtils.isEmpty(data2) ) {
            Toast.makeText(mContext, "新的信息不能留空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 服务端验证
        if (yesno==1)
        {
            String url = Constants.BASE_URL + "User?method=update2";
            OkHttpUtils
                    .post()
                    .addFile("image", imageFile.getName(), imageFile)
                    .url(url)
                    .addHeader("content-Type", "multipart/form-data; boundary=" + UUID.randomUUID().toString())
                    .addParams("username", username)
                    .addParams("password", password)
                    .addParams("phone", phone)
                    .addParams("name", name)
                    .addParams("weight", weight)
                    .addParams("data", data2)
                    .id(1)
                    .build()
                    .execute(new MyStringCallback());
        }
        else {
            String url = Constants.BASE_URL + "User?method=update22";
            OkHttpUtils
                    .post()
                    .url(url)
                    .addParams("username", username)
                    .addParams("password", password)
                    .addParams("phone", phone)
                    .addParams("name", name)
                    .addParams("weight", weight)
                    .addParams("data", data2)
                    .id(1)
                    .build()
                    .execute(new MyStringCallback());
        }
    }


    public class MyStringCallback extends StringCallback {

        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            switch (id) {
                case 1:
                    User a = null;
                    try {
                        a = gson.fromJson(response, User.class);
                    } catch (JsonSyntaxException e) {
                        a = null;
                    }
                    if (a == null) {
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Toast.makeText(mContext, "修改成功！", Toast.LENGTH_SHORT).show();
                        finish();
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
}
