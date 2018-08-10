package com.example.wpy.teamattention.other;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.example.wpy.teamattention.AddWork;
import com.example.wpy.teamattention.R;
import com.example.wpy.teamattention.database.Group;
import com.example.wpy.teamattention.database.OkhttpAction;
import com.example.wpy.teamattention.database.Work;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wpy on 2017/11/20.
 */

@SuppressLint("ValidFragment")
public class Fragment_group1 extends Fragment{
    android.support.v7.widget.RecyclerView recyclerView;
    private Context mcontext;
    private String token;
    private String account;
    private int user_id;
    private List<Work> workList = new ArrayList<>();
    private int privilege;
    private int group_id;
    private Group group;

    private TextView show;
    private TextView refresh;
    private LinearLayout add_work;
    private TextView account_work;
    WorkAdapter adapter;
    LinearLayoutManager layoutManager;
    public Fragment_group1(Context context, String token, String account, int user_id, int group_id, int privilege) {
        mcontext = context;
        this.account = account;
        this.token = token;
        this.user_id = user_id;
        this.privilege = privilege;
        this.group_id = group_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_group1_layout, container, false);
        show = view.findViewById(R.id.fragment_group1_add_work_show);
        add_work = view.findViewById(R.id.fragment_group1_add_work);
        account_work = view.findViewById(R.id.mywork);
        add_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (privilege == 0){
                    Toast.makeText(mcontext, "您无此权限", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(mcontext, AddWork.class);
                    intent.putExtra("token", token);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("group_id", group_id);
                    intent.putExtra("account", account);
                    startActivityForResult(intent, 0);
                }
            }
        });
        refresh = view.findViewById(R.id.fragment_group1_show);
        recyclerView = view.findViewById(R.id.fragment_group1_recyclerView);
        show = view.findViewById(R.id.fragment_group1_add_work_show);
        OkhttpAction okhttpAction = new OkhttpAction(mcontext);
        okhttpAction.get_work(token, group_id, show);
        for (;;){
            if (show.getText().toString().equals(""))
                SystemClock.sleep(50);
            else break;
        }
        String action = show.getText().toString();
        try {
            JSONObject jsonObject = new JSONObject(action);
            String tasks = jsonObject.getString("tasks");
            deal_action(tasks, show);
            int l = workList.size();
            account_work.setText("" + l);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        layoutManager = new LinearLayoutManager(mcontext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WorkAdapter(workList, mcontext, token, user_id, group_id, account, refresh);
        recyclerView.setAdapter(adapter);
        return view;
    }
    public void deal_action(String action, TextView show){
        try{
            JSONArray jsonArray = new JSONArray(action);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Work work = new Work();
                work.group_id = group_id;
                work.user_id = user_id;
                work.title = jsonObject.getString("title");
                work.content = jsonObject.getString("content");
                String remind_time = jsonObject.getString("remind_time");
                String year = remind_time.substring(0, 4);
                String month = remind_time.substring(5, 7);
                String day = remind_time.substring(8, 10);
                String hour = remind_time.substring(11, 13);
                String minute = remind_time.substring(14, 16);
                work.year = Integer.valueOf(year);
                work.month = Integer.valueOf(month);
                work.day = Integer.valueOf(day);
                work.hour = Integer.valueOf(hour);
                work.minute = Integer.valueOf(minute);
                work.id = jsonObject.getInt("id");
                work.account = account;
                work.imageId = R.drawable.work;
                workList.add(work);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getActivity().finish();
        Intent intent = new Intent(mcontext, mcontext.getClass());
        intent.putExtra("token", token);
        intent.putExtra("group_id", group_id);
        intent.putExtra("user_id", user_id);
        intent.putExtra("account", account);
        mcontext.startActivity(intent);
    }
}
