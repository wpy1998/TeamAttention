package com.example.wpy.teamattention.database;

import android.content.Context;
import android.os.RecoverySystem;
import android.util.Log;
import android.widget.TextView;

import com.example.wpy.teamattention.FileActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

import static java.lang.String.valueOf;

/**
 * Created by wpy on 2017/11/23.
 */

public class OkhttpAction {
    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
    private Context mcontext;
    public String token;
    public OkhttpAction(Context context){
        mcontext = context;
        token = "null";
    }
    public void setToken(String token){
        this.token = token;
    }
    public void getAuthorization(final String username, final String password, final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String basic = Credentials.basic(username,password);
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/token").build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    //show.setText(action);
                    JSONObject jsonObject = new JSONObject(action);
                    String token1 = jsonObject.getString("token");
                    show.setText(token1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void RegisterAccount(final String username, final String password, final String email, final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String json = "{\"account\":\""+username+"\",\"password\":\""+password+"\",\"email\":\""+email+"\"}";
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/user").post(body).build();
                    Response response = client.newCall(request).execute();
                    int action = response.code();
                    if (action == 201){
                        show.setText("注册成功");
                    }
                    else {
                        show.setText("注册失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void get_user(final String token, final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/user").build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    show.setText(action);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void get_user_message(final String token, final int user_id, final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/user/" + user_id).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    show.setText(action);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //group put(add), get, delete
    public void put_group(final String token, final String group_name, final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    String json = "{\"group_name\":\""+group_name+"\"}";
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/user/group").post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    JSONObject jsonObject = new JSONObject(action);
                    String message = jsonObject.getString("message");
                    show.setText(message);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void get_group(final String token, final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/user/group").build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    show.setText(action);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void delete_group(final String token, final int id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String basic = Credentials.basic(token, "");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/group/" + valueOf(id)).delete().build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void add_member(final String token, final int user_id, final int group_id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String basic = Credentials.basic(token, "");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    String json = "{\"user_id\":"+user_id+",\"group_id\":" + group_id + ",\"job\":\"member\"}";
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/user/group").put(body).build();
                    Response response = client.newCall(request).execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void delete_member(final String token, final int group_id, final int user_id, final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String basic = Credentials.basic(token, "");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    String json = "{\"group_id\":" + group_id + ",\"user_id\":" + user_id + "}";
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/user/group").delete(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    show.setText(action);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void search_member(final String token, final String message, final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/user?keyword=" + message).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    show.setText(action);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void set_rank(final String token, final int user_id, final int group_id, final String rank, final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    String json = "{\"user_id\":"+ user_id + ",\"group_id\":" + group_id + ",\"job\":\"" + rank + "\"}";
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/user/group").put(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    show.setText(action + "1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void get_single_group(final String token, final int group_id, final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/group/" + group_id).get().build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    show.setText(action);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //work
    public void add_new_work(final String token, final int user_id, final int group_id, final String title, final String content, final String remind_time,final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    String json = "{\"title\":\""+ title +"\",\"content\":\"" + content + "\",\"remind_time\":\"" + remind_time + "\",\"members\":[" + user_id + "],\"user_id\":" + user_id + "}";
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/group/" + group_id + "/task").post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    show.setText(action + "\ngroup_id = " + group_id);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void get_work(final String token, final int group_id, final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/group/" + group_id + "/task").build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    show.setText(action);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void work_delete(final String token, final int task_id, final int group_id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/group/" + group_id + "/task/" + task_id).delete().build();
                    Response response = client.newCall(request).execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void work_update(final String token, final int task_id, final int user_id, final int group_id, final String title, final String content, final String remind_time){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    String json = "{\"title\":\""+ title +"\",\"content\":\"" + content + "\",\"remind_time\":\"" + remind_time + "\",\"members\":[" + user_id + "],\"user_id\":" + user_id + "}";
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/group/" + group_id + "/task/" + task_id).put(body).build();
                    Response response = client.newCall(request).execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }


    //message
    public void add_new_message(final String token, final String title, final String content, final int group_id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    String json = "{\"title\":\"" + title +"\",\"content\":\"" + content + "\"}";
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/group/" + group_id + "/subject").post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void get_message(final String token, final int group_id, final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/group/" + group_id + "/subject").build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    show.setText(action);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void get_response(final String token, final int group_id, final int subject_id, final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/group/" + group_id + "/subject/" + subject_id).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    show.setText(action);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void delete_message(final String token, final int group_id, final int subject_id){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/group/" + group_id + "/subject/" + subject_id).delete().build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void add_response(final String token, final int group_id, final int subject_id, final String content){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    String json = "{\"content\":\"" + content + "\"}";
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/group/" + group_id + "/subject/" + subject_id).post(body).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void get_news(final String token, final int group_id, final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/group/" + group_id + "/message").build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    show.setText(action);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void upload_image(final String token, final String filename, final File file, final TextView show){

    }
    public void download_image(final String token){

    }


    //file
    public void uploadFile(final String token, final String url, final File file){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).connectTimeout(10, TimeUnit.SECONDS)
                            .writeTimeout(180, TimeUnit.SECONDS)
                            .readTimeout(180, TimeUnit.SECONDS)
                            .build();
                    RequestBody formBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file", file.getName(),
                                    RequestBody.create(MediaType.parse("*/*"), file.getPath()))
                            .build();
                    Request request = new Request.Builder().url(url).post(formBody).build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void getFile(final String token, final int group_id, final TextView show){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    Request request = new Request.Builder().url("http://10.4.21.216:5000/api/1.0/group/" + group_id + "/file").build();
                    Response response = client.newCall(request).execute();
                    String action = response.body().string();
                    show.setText(action);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void downloadFile(final String token, final String url, final String filepath, final String filename){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String basic = Credentials.basic(token,"");
                    OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
                        @Override
                        public Request authenticate(Route route, Response response) throws IOException {
                            return response.request().newBuilder().header("Authorization", basic).build();
                        }
                    }).build();
                    File file = new File(filepath, filename);
                    Request request = new Request.Builder().url(url).build();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
