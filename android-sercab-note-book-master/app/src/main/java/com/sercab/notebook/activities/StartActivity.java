package com.sercab.notebook.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.sercab.notebook.R;
import com.sercab.notebook.utils.AppUtils;


public class StartActivity extends AppCompatActivity {

    private AppUtils appUtils = new AppUtils(this);


    //读写权限
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏

        new Thread(() -> {
            try {
                Thread.sleep(2500);
                appUtils.startActivity(this,LoginActivity.class);
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("Start", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
        }
    }



}