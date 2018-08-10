package com.example.wpy.teamattention;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.example.wpy.teamattention.database.Group;
import com.example.wpy.teamattention.database.OkhttpAction;
import com.example.wpy.teamattention.database.User;
import com.example.wpy.teamattention.other.PeopleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PeopleActivity extends AppCompatActivity {
    int user_id;
    int group_id;
    private String account;
    String token;
    android.support.v7.widget.RecyclerView recyclerView_builder;
    android.support.v7.widget.RecyclerView recyclerView_manager;
    android.support.v7.widget.RecyclerView recyclerView_member;
    private List<User> builderList = new ArrayList<>();
    private List<User> managerList = new ArrayList<>();
    private List<User> memberList = new ArrayList<>();
    Group group = new Group();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        TextView show = findViewById(R.id.people_show);
        Intent intentfront = getIntent();
        token = intentfront.getStringExtra("token");
        group_id = intentfront.getIntExtra("group_id", 0);
        user_id = intentfront.getIntExtra("user_id", 0);
        OkhttpAction okhttpAction = new OkhttpAction(PeopleActivity.this);
        show.setText("");
        okhttpAction.get_group(token, show);
        for(;;){
            if (show.getText().toString().equals("")){
                SystemClock.sleep(20);
            }
            else break;
        }
        String action = show.getText().toString();
        try {
            JSONObject jsonObject = new JSONObject(action);
            String action1 = jsonObject.getString("groups");
            deal_action(action1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        User builder = new User();
        builder.name = group.builder;
        builder.id = group.builder_id;
        builder.email = group.group_builder_email;
        builderList.add(builder);
        int privilege = 0;
        if (builder.id == user_id){
            privilege = 1;
        }

        for (int i = 0; i < group.group_manager_number; i++){
            User user = new User();
            user.email = group.group_manager_email[i];
            user.name = group.group_manager[i];
            user.id = group.group_manager_id[i];
            managerList.add(user);
        }

        for (int i = 0; i < group.group_member_number; i++){
            User user = new User();
            user.email = group.group_member_email[i];
            user.name = group.group_member[i];
            user.id = group.group_member_id[i];
            memberList.add(user);
        }

        recyclerView_builder = findViewById(R.id.people_builder_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(PeopleActivity.this);
        recyclerView_builder.setLayoutManager(layoutManager);
        PeopleAdapter adapter = new PeopleAdapter(PeopleActivity.this, builderList, token, group_id, user_id,1, privilege);
        recyclerView_builder.setAdapter(adapter);

        recyclerView_manager = findViewById(R.id.people_manager_recyclerView);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(PeopleActivity.this);
        recyclerView_manager.setLayoutManager(layoutManager2);
        PeopleAdapter adapter2 = new PeopleAdapter(PeopleActivity.this, managerList, token, group_id, user_id,2, privilege);
        recyclerView_manager.setAdapter(adapter2);

        recyclerView_member = findViewById(R.id.people_member_recyclerView);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(PeopleActivity.this);
        recyclerView_member.setLayoutManager(layoutManager3);
        PeopleAdapter adapter3 = new PeopleAdapter(PeopleActivity.this, memberList, token, group_id, user_id,3, privilege);
        recyclerView_member.setAdapter(adapter3);
    }
    public void deal_action(String action1) throws Exception {
        JSONArray jsonArray = new JSONArray(action1);
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            group.id = jsonObject.getInt("id");
            if (group.id != group_id){
                continue;
            }
            group.token = this.token;
            group.group_manager_number = 0;
            group.group_manager_number = 0;
            String group_name = jsonObject.getString("name");
            group.group_name = group_name;

            String builder = jsonObject.getString("builder");
            JSONObject object_builder = new JSONObject(builder);
            group.builder = object_builder.getString("name");
            group.builder_id = object_builder.getInt("id");
            group.group_builder_email = object_builder.getString("email");

            String managers = jsonObject.getString("managers");
            JSONArray array = new JSONArray(managers);
            int length = array.length();
            group.group_manager = new String[length];
            group.group_manager_email = new String[length];
            group.group_manager_id = new int[length];
            for (int j = 0; j < array.length(); j++){
                JSONObject jsonObject2 = array.getJSONObject(j);
                group.group_manager[group.group_manager_number] = jsonObject2.getString("name");
                group.group_manager_id[group.group_manager_number] = jsonObject2.getInt("id");
                group.group_manager_email[group.group_manager_number] = jsonObject2.getString("email");
                (group.group_manager_number)++;
            }

            String members = jsonObject.getString("members");
            JSONArray array1 = new JSONArray(members);
            length = array1.length();
            group.group_member = new String[length];
            group.group_member_email = new String[length];
            group.group_member_id = new int[length];
            for (int j = 0; j < array1.length(); j++){
                JSONObject jsonObject2 = array1.getJSONObject(j);
                group.group_member[group.group_member_number] = jsonObject2.getString("name");
                group.group_member_id[group.group_member_number] = jsonObject2.getInt("id");
                group.group_member_email[group.group_member_number] = jsonObject2.getString("email");
                (group.group_member_number)++;
            }

            group.imageId = R.drawable.group;
        }
    }
}
