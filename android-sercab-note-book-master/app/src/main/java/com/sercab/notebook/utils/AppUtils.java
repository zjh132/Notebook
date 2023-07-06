package com.sercab.notebook.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Vibrator;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 * 残月
 */
public class AppUtils extends Activity {

    public Context app;

    public AppUtils(Context app) {
        this.app = app;
    }

    /**
     * 消息弹出
     * @param msg
     */
    public void showToast(final String msg) {
        runOnUiThread(() -> Toast.makeText(app, msg, Toast.LENGTH_SHORT).show());
    }

    /**
     * 启动一个Activity
     * @param context
     * @param activity
     */
    public void startActivity(Context context, Class activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    /**
     * 启动一个Activity,且带数据
     * @param context
     * @param activity
     */
    public void startActivityTakeString(Context context, Class activity,String name,String data) {
        Intent intent = new Intent(context, activity);
        intent.putExtra(name,data);
        context.startActivity(intent);
    }

    /**
     * 震动反馈
     * @param context
     * @param duration
     */
    public void vibrate(Context context, int duration) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(duration);
    }


    /**
     * 检查该路径文件是否存在
     * @param filePath
     * @return
     */
    public boolean isFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }


    /**
     * 读取文件内容
     * @param filePath
     * @return
     */
    public String readTextFile(String filePath) {
        String fileContent = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line = bufferedReader.readLine();
            while (line != null) {
                fileContent = line;
                line = bufferedReader.readLine();
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    /**
     * 保存文件内容
     */
    public void saveTextFile(String filePath, String fileContent) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(fileContent);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 返回程序文件目录
     * @param path
     * @return
     */
    public String appFilePath(String path) {
        String basePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SerCab";
        String filePath = basePath + path;

        File directory = new File(basePath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                showToast("创建失败");
            }
        }

        return filePath;
    }


}
