package com.example.wpy.teamattention.other;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wpy.teamattention.AddFile;
import com.example.wpy.teamattention.GroupActivity;
import com.example.wpy.teamattention.R;
import com.example.wpy.teamattention.database.FileT;
import com.example.wpy.teamattention.database.OkhttpAction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wpy on 2017/11/20.
 */

@SuppressLint("ValidFragment")
public class Fragment_group2 extends Fragment{
    TextView show;
    android.support.v7.widget.RecyclerView recyclerView;
    private Context mcontext;
    private String token;
    private String account;
    private int user_id;
    private int privilege;
    private int group_id;
    private OkhttpAction okhttpAction;
    private TextView myfile;
    LinearLayout add_file;
    private List<FileT> fileList = new ArrayList<>();
    FileAdapter adapter;
    LinearLayoutManager layoutManager;
    public Fragment_group2(Context context, String token, String account, int user_id, int group_id, int privilege){
        mcontext = context;
        this.token = token;
        this.account = account;
        this.user_id = user_id;
        this.group_id = group_id;
        this.privilege = privilege;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_group2_layout, container, false);
        show = view.findViewById(R.id.fragment_group2_show);
        myfile = view.findViewById(R.id.myfile);
        show.setText("");
        okhttpAction = new OkhttpAction(mcontext);
        okhttpAction.getFile(token, group_id, show);
        recyclerView = view.findViewById(R.id.fragment_group2_recyclerView);
        for (int i = 0; i < 30; i++){
            if (show.getText().toString().equals("")){
                SystemClock.sleep(20);
            }
            else break;
        }
        try {
            String action = show.getText().toString();
            deal_action(action);
        }catch (Exception e){
            e.printStackTrace();
        }
        myfile.setText("" + fileList.size());
        add_file = view.findViewById(R.id.fragment_group2_add_file);
        add_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, AddFile.class);
                intent.putExtra("token", token);
                intent.putExtra("user_id", user_id);
                intent.putExtra("group_id", group_id);
                intent.putExtra("account", account);
                startActivityForResult(intent, 0);
            }
        });
        layoutManager = new LinearLayoutManager(mcontext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FileAdapter(fileList, mcontext, token, user_id, group_id, account);
        recyclerView.setAdapter(adapter);
        return view;
    }
    public void deal_action(String action){
        try {
            JSONArray jsonArray = new JSONArray(action);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                FileT fileT = new FileT();
                fileT.filename = jsonObject.getString("filename");
                fileT.file_url = jsonObject.getString("file_url");
                String upload = jsonObject.getString("upload_user");
                JSONObject object = new JSONObject(upload);
                fileT.upload_user = object.getString("username");
                fileList.add(fileT);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getActivity().finish();
        Intent intent = new Intent(mcontext, GroupActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("user_id", user_id);
        intent.putExtra("group_id", group_id);
        intent.putExtra("account", account);
        intent.putExtra("choose", 1);
        startActivity(intent);
    }
}
