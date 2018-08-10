package com.example.wpy.teamattention.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wpy.teamattention.R;
import com.example.wpy.teamattention.database.Message2;
import com.example.wpy.teamattention.database.OkhttpAction;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wpy on 2017/11/25.
 */

@SuppressLint("ValidFragment")
public class Fragment_group5 extends Fragment{
    android.support.v7.widget.RecyclerView recyclerView;
    TextView show;
    private String token;
    private int group_id;
    private int user_id;
    private Context mcontext;
    private String account;
    private List<Message2> messageList = new ArrayList<>();
    MessageAdapter adapter;
    LinearLayoutManager layoutManager;
    public Fragment_group5(Context context, String token, int group_id, int user_id, String account){
        mcontext = context;
        this.group_id = group_id;
        this.user_id = user_id;
        this.token = token;
        this.account = account;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_group5_layout, container, false);
        show = view.findViewById(R.id.fragment_group5_show);
        OkhttpAction okhttpAction = new OkhttpAction(mcontext);
        okhttpAction.get_news(token, group_id, show);
        for (int i = 0; i < 30; i++){
            if (show.getText().toString().equals("")){
                SystemClock.sleep(20);
            }
            else break;
        }
        String action = show.getText().toString();
        deal_action(action);
        recyclerView = view.findViewById(R.id.fragment_group5_recyclerView);
        layoutManager = new LinearLayoutManager(mcontext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessageAdapter(mcontext, messageList, token, account);
        recyclerView.setAdapter(adapter);
        return view;
    }
    private void deal_action(String action){
        try{
            JSONArray jsonArray = new JSONArray(action);
            for (int i = jsonArray.length() - 1 ; i >= 0; i--){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Message2 message = new Message2();
                message.message = jsonObject.getString("message");
                String excutor = jsonObject.getString("excutor");
                JSONObject object = new JSONObject(excutor);
                message.name = object.getString("name");
                String time = jsonObject.getString("create_time");
                String year = time.substring(0, 4);
                String month = time.substring(5, 7);
                String day = time.substring(8, 10);
                String hour = time.substring(11, 13);
                String minute = time.substring(14, 16);
                message.year = Integer.valueOf(year);
                message.month = Integer.valueOf(month);
                message.day = Integer.valueOf(day);
                message.hour = Integer.valueOf(hour);
                message.minute = Integer.valueOf(minute);
                messageList.add(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
