package com.example.myapplication.example.teacher;

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
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.example.entity.Course;
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

public class siketianjia extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private EditText kechengmingcheng;
    private EditText kaluli;
    private EditText kechengneirong;
    private Button querentianjia;
    private ImageView photo;
    private File imageFile;
    private ArrayList<Uri> path;
    Intent intent=new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kechengtianjia);
        findViewById();
        initView();
    }
    private void initView() {
        mContext=this;
        this.photo.setOnClickListener(this);
    }

    private void findViewById() {
        findViewById(R.id.querentianjia).setOnClickListener(this);
        kechengmingcheng=findViewById(R.id.kechengmingcheng);
        kaluli=findViewById(R.id.kaluli);
        kechengneirong = findViewById(R.id.kechengneirong);
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
        FishBun.with(siketianjia.this)
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
        String mingcheng =kechengmingcheng.getText().toString().trim();
        String kalulia = kaluli.getText().toString().trim();
        String neirong = kechengneirong.getText().toString().trim();
        //d.判断用户名密码是否为空，不为空请求服务器（省略，默认请求成功）i
        if (TextUtils.isEmpty(mingcheng) || TextUtils.isEmpty(kalulia) || TextUtils.isEmpty(neirong) ) {
            Toast.makeText(mContext, "信息不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (imageFile == null) {
            Toast.makeText(mContext, "请先选择一张图片", Toast.LENGTH_SHORT).show();
            return;
        }
        // 服务端验证

        String url;
            url = Constants.BASE_URL + "Course?method=addsike";
            OkHttpUtils
                    .post()
                    .addFile("image", imageFile.getName(), imageFile)
                    .url(url)
                    .addHeader("content-Type", "multipart/form-data; boundary=" + UUID.randomUUID().toString())
                    .addParams("coachId",Constants.USER.getUserId()+"")
                    .addParams("mingcheng", mingcheng)
                    .addParams("kaluli", kalulia)
                    .addParams("neirong", neirong)
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
                    Course a = null;
                    try {
                        a = gson.fromJson(response, Course.class);
                    } catch (JsonSyntaxException e) {
                        a = null;
                    }
                    if (a == null) {
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        Toast.makeText(mContext, "添加项目成功！", Toast.LENGTH_SHORT).show();
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
