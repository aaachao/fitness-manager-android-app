package com.example.myapplication.example;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.entity.User;
import com.example.myapplication.example.user.user_admin;
import com.example.myapplication.example.utils.AppManager;
import com.example.myapplication.example.utils.Constants;
import com.example.myapplication.example.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.Call;

public class activity_register extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private EditText et_username;
    private EditText et_password;
    private EditText et_repassword;
    private EditText et_phone;
    private EditText et_name;
    private EditText weight;
    private ImageView photo;
    private File imageFile;
    private ArrayList<Uri> path;
    private Button register_login;
    private RadioGroup radio_sex;
    Intent intent=new Intent();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById();
        initView();
    }

    private void initView() {
        this.photo.setOnClickListener(this);
        mContext=this;
    }

    private void findViewById() {
        findViewById(R.id.reg_btn_register).setOnClickListener(this);
        et_username=findViewById(R.id.reg_et_username);
        et_password=findViewById(R.id.reg_et_password);
        et_repassword = findViewById(R.id.reg_et_repassword);
        et_phone = findViewById(R.id.reg_et_phone);
        et_name = findViewById(R.id.reg_et_name);
        weight=findViewById(R.id.weight);
        photo=findViewById(R.id.addphoto);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg_btn_register:
                regiter();
            break;
            case R.id.addphoto:
                getPhoto();
                break;
        }
    }
    private void getPhoto() {
        FishBun.with(activity_register.this)
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
    private void regiter() {
        String username =et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String repassword = et_repassword.getText().toString().trim();
        String phone = et_phone.getText().toString().trim();
        String name = et_name.getText().toString().trim();
        String weight2=weight.getText().toString().trim();
        //d.判断用户名密码是否为空，不为空请求服务器（省略，默认请求成功）i
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(name)|| TextUtils.isEmpty(weight2)) {
            Toast.makeText(mContext, "信息不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (imageFile == null) {
            Toast.makeText(mContext, "请选择一张图片作为您的头像", Toast.LENGTH_SHORT).show();
            return;
        }
        // 判断两次密码
        if (!password.equals(repassword)) {
            Toast.makeText(mContext, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        // 服务端验证
        String url = Constants.BASE_URL + "User?method=register";
        OkHttpUtils
                .post()
                .addFile("image", imageFile.getName(), imageFile)
                .url(url)
                .addHeader("content-Type", "multipart/form-data; boundary=" + UUID.randomUUID().toString())
                .addParams("username", username)
                .addParams("password", password)
                .addParams("phone", phone)
                .addParams("name", name)
                .addParams("weight", weight2)
                .id(1)
                .build()
                .execute(new MyStringCallback());
    }
    public class MyStringCallback extends StringCallback {

        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            switch (id) {
                case 1:
                    User user = null;
                    try {
                        user = gson.fromJson(response, User.class);
                    } catch (JsonSyntaxException e) {
                        user = null;
                    }
                    if (user == null) {
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        // 存储用户
                        Constants.USER = user;
                        boolean result = SharedPreferencesUtils.saveUserInfo(mContext, user);
                        if (result) {
                            Toast.makeText(mContext, "注册成功，已经自动登录！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "用户名密码保存失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    intent.setClass(getApplicationContext(),user_admin.class);
                    startActivity(intent);
                    AppManager.getInstance().killAllActivity();
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
