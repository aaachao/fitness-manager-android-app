package com.example.myapplication.example.admin;

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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.entity.User;
import com.example.myapplication.example.utils.Constants;
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

public class yonghutianjia extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private EditText yonghuzhanghao;
    private EditText yonghumima;
    private RadioGroup radiorole;
    private EditText yonghuxingming;
    private EditText yonghudianhua;
    private EditText weight2;
    private ImageView photo;
    private File imageFile;
    private ArrayList<Uri> path;
    private Button querentianjia;
    Intent intent=new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yonghutianjia);
        findViewById();
        initView();
    }
    private void initView() {
        mContext=this;
        this.photo.setOnClickListener(this);
    }

    private void findViewById() {
        findViewById(R.id.querentianjia).setOnClickListener(this);
        yonghuzhanghao=findViewById(R.id.yonghuzhanghao);
        yonghumima=findViewById(R.id.yonghumima);
        yonghudianhua=findViewById(R.id.yonghudianhua);
        yonghuxingming=findViewById(R.id.yonghuxingming);
        weight2=findViewById(R.id.weight);
        this.radiorole = (RadioGroup) findViewById(R.id.yonghuleixing);
        photo=findViewById(R.id.addphoto);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.querentianjia:
                regiter();
                break;
            case R.id.addphoto:
                getPhoto();
                break;
        }
    }
    private void getPhoto() {
        FishBun.with(yonghutianjia.this)
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
        String username =yonghuzhanghao.getText().toString().trim();
        String password = yonghumima.getText().toString().trim();
        String name = yonghuxingming.getText().toString().trim();
        String phone = yonghudianhua.getText().toString().trim();
        String weight=weight2.getText().toString().trim();
        String role = "admin";
        if (radiorole.getCheckedRadioButtonId() == R.id.user) {
            role = "user";
        }
        else if (radiorole.getCheckedRadioButtonId() == R.id.teacher)
        {
            role = "teacher";
        }
        //d.判断用户名密码是否为空，不为空请求服务器（省略，默认请求成功）i
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)|| TextUtils.isEmpty(name) || TextUtils.isEmpty(phone)|| TextUtils.isEmpty(weight)   ) {
            Toast.makeText(mContext, "信息不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(role.equals("admin"))
        {

        }
        else
        {
            if (imageFile == null) {
                Toast.makeText(mContext, "请为用户选择一张图片作为头像", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        // 服务端验证
        String url = Constants.BASE_URL + "User?method=add";
        OkHttpUtils
                .post()
                .addFile("image", imageFile.getName(), imageFile)
                .url(url)
                .addHeader("content-Type", "multipart/form-data; boundary=" + UUID.randomUUID().toString())
                .addParams("username", username)
                .addParams("password", password)
                .addParams("phone", phone)
                .addParams("name", name)
                .addParams("role", role)
                .addParams("weight", weight)
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
                        Toast.makeText(mContext, "添加用户成功！", Toast.LENGTH_SHORT).show();
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
