package com.example.wpy.teamattention;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;

public class FileActivity extends AppCompatActivity {
    private final String TAG = FileActivity.class.getSimpleName();

    private String URL;
    private File FILE_PATH;

    private String token;
    private String account;
    private String url;
    private String filename;
    private int group_id;
    private int user_id;
    String folder_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        Intent intentfront = getIntent();
        token = intentfront.getStringExtra("token");
        group_id = intentfront.getIntExtra("groip_id", 0);
        user_id = intentfront.getIntExtra("user_id", 0);
        filename = intentfront.getStringExtra("filename");
        url = intentfront.getStringExtra("file_url");
        folder_main = "TeamAttention";
        FILE_PATH = new File(Environment.getExternalStorageDirectory() + "/" + folder_main, filename);
        URL = "http://10.4.21.216:5000" + url;

        if (ContextCompat.checkSelfPermission(FileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(FileActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

        boolean result = this.doesSDCardAccessible();
    }

    public boolean doesSDCardAccessible(){
        try {
            return(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return false;
    }
    public void startDownload(View view) {
        final String basic = Credentials.basic(token,"");
        Request request = new Request.Builder()
                .url(URL)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                return response.request().newBuilder().header("Authorization", basic).build();
            }
        }).build();
        Call mDownloadCall = okHttpClient.newCall(request);
        mDownloadCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败的时候回调，打印异常信息
                Toast.makeText(FileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                ResponseBody body = response.body();
                long totleBytes = body.contentLength();//得到文件的总大小
                InputStream is = body.byteStream();//得到一个流文件

                File f = new File(Environment.getExternalStorageDirectory(), folder_main);
                if (!f.exists()) {
                    f.mkdirs();
                }

                File file = FILE_PATH;
                if (file.exists()){
                    file.delete();
                }
                try {
                    //创建文件
                    file.createNewFile();
                    //给一个吐司提示，显示创建成功
                    Toast.makeText(getApplicationContext(), "文件创建成功", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[2048];
                long currentBytes = 0L;
                int len = 0;
                //将文件写入本地（再次友情提示：记得权限问题）
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    currentBytes += len;//每次写入的大小累加
                    int progress = (int) ((currentBytes * 100) / totleBytes);//转换得到当前下载的进度%
                    Toast.makeText(FileActivity.this, "onResponse: --------- 下载进度:" + progress + "%", Toast.LENGTH_SHORT).show();
                }
                fos.flush();
                fos.close();
                is.close();
            }
        });
        Toast.makeText(FileActivity.this, "下载完毕", Toast.LENGTH_SHORT).show();
        SystemClock.sleep(200);
        finish();
    }
    @Override

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "拒绝权限将无法使用该程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
}
