package com.example.wpy.teamattention.other;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wpy.teamattention.AddMessage;
import com.example.wpy.teamattention.R;
import com.example.wpy.teamattention.database.OkhttpAction;
import com.example.wpy.teamattention.database.Word;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wpy on 2017/11/20.
 */

@SuppressLint("ValidFragment")
public class Fragment_group3 extends Fragment{
    android.support.v7.widget.RecyclerView recyclerView;
    private Context mcontext;
    private String token;
    private String account;
    private int user_id;
    private int privilege;
    private int group_id;
    TextView show;
    android.support.design.widget.FloatingActionButton add_message;
    WordAdapter adapter;
    LinearLayoutManager layoutManager;
    private List<Word> wordList = new ArrayList<>();
    public Fragment_group3(Context context, String token, int user_id, int group_id, int privilege, String account){
        this.token = token;
        this.account = account;
        this.user_id = user_id;
        this.group_id = group_id;
        this.privilege = privilege;
        this.mcontext = context;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_group3_layout, container, false);

        show = view.findViewById(R.id.fragment_group3_show);
        add_message = view.findViewById(R.id.fab);
        add_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, AddMessage.class);
                intent.putExtra("token", token);
                intent.putExtra("group_id", group_id);
                startActivityForResult(intent, 0);
            }
        });
        OkhttpAction okhttpAction = new OkhttpAction(mcontext);
        okhttpAction.get_message(token, group_id, show);
        for (int i = 0; i < 30; i++){
            if (show.getText().toString().equals("")){
                SystemClock.sleep(20);
            }
            else break;
        }
        String action = show.getText().toString();
        deal_action(action);
        recyclerView = view.findViewById(R.id.fragment_group3_recyclerView);
        layoutManager = new LinearLayoutManager(mcontext);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WordAdapter(wordList, mcontext, token, user_id, group_id, account, privilege);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getActivity().finish();
        Intent intent = new Intent(mcontext, mcontext.getClass());
        intent.putExtra("token", token);
        intent.putExtra("account", account);
        intent.putExtra("group_id", group_id);
        intent.putExtra("user_id",user_id);
        intent.putExtra("choose", 2);
        mcontext.startActivity(intent);
    }

    public void deal_action(String action){
        try {
            JSONArray jsonArray = new JSONArray(action);
            for (int i = jsonArray.length() - 1; i >= 0 ; i--){
                Word word = new Word();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String subject = jsonObject.getString("subject");
                word.details = jsonObject.getString("details");

                JSONObject object = new JSONObject(subject);
                word.title = object.getString("title");
                word.content = object.getString("content");
                word.id = jsonObject.getInt("id");

                String owner = object.getString("create_user");
                JSONObject jsonObject1 = new JSONObject(owner);
                word.owner = jsonObject1.getString("name");
                word.owner_email = jsonObject1.getString("email");
                word.owner_id = jsonObject1.getInt("id");

                String time = object.getString("create_time");
                String year = time.substring(0, 4);
                String month = time.substring(5, 7);
                String day = time.substring(8, 10);
                String hour = time.substring(11, 13);
                String minute = time.substring(14, 16);
                word.year = Integer.valueOf(year);
                word.month = Integer.valueOf(month);
                word.day = Integer.valueOf(day);
                word.hour = Integer.valueOf(hour);
                word.minute = Integer.valueOf(minute);
                wordList.add(word);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
