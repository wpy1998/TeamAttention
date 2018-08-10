package com.example.wpy.teamattention.other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wpy.teamattention.AddMember;
import com.example.wpy.teamattention.MainActivity;
import com.example.wpy.teamattention.R;
import com.example.wpy.teamattention.database.Group;
import com.example.wpy.teamattention.database.OkhttpAction;
import com.example.wpy.teamattention.database.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wpy on 2017/11/20.
 */

@SuppressLint("ValidFragment")
public class Fragment_group4 extends Fragment{
    private String token;
    int user_id;
    int group_id;
    private Context mcontext;
    private String account;
    private int privilege;
    private TextView add_people;
    private TextView delete_group;
    android.support.v7.widget.RecyclerView recyclerView_builder;
    android.support.v7.widget.RecyclerView recyclerView_manager;
    android.support.v7.widget.RecyclerView recyclerView_member;
    private List<User> builderList = new ArrayList<>();
    private List<User> managerList = new ArrayList<>();
    private List<User> memberList = new ArrayList<>();
    Group group = new Group();
    public Fragment_group4(Context context, String token, int user_id, int group_id, String account, int privilege){
        mcontext = context;
        this.token = token;
        this.user_id = user_id;
        this.group_id = group_id;
        this.account = account;
        this.privilege = privilege;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.fragment_group4_layout, container, false);
        TextView show = view.findViewById(R.id.people_show);
        final OkhttpAction okhttpAction = new OkhttpAction(mcontext);
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

        add_people = view.findViewById(R.id.fragment_group4_add_people);
        add_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (privilege == 0){
                    Toast.makeText(mcontext, "您无此权限", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(mcontext, AddMember.class);
                    intent.putExtra("token", token);
                    intent.putExtra("group_id", group_id);
                    startActivity(intent);
                }
            }
        });
        delete_group = view.findViewById(R.id.fragment_group4_delete_group);
        delete_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (privilege != 2){
                    Toast.makeText(mcontext, "您无此权限", Toast.LENGTH_SHORT).show();
                }else {
                    okhttpAction.delete_group(token, group_id);
                    getActivity().finish();
                    Intent intent = new Intent(mcontext, MainActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("account", account);
                    intent.putExtra("user_id", user_id);
                    startActivity(intent);
                }
            }
        });

        User builder = new User();
        builder.name = group.builder;
        builder.id = group.builder_id;
        builder.email = group.group_builder_email;
        builderList.add(builder);

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

        recyclerView_builder = view.findViewById(R.id.people_builder_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext);
        recyclerView_builder.setLayoutManager(layoutManager);
        PeopleAdapter adapter = new PeopleAdapter(mcontext, builderList, token, group_id, user_id,1, privilege);
        recyclerView_builder.setAdapter(adapter);

        recyclerView_manager = view.findViewById(R.id.people_manager_recyclerView);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(mcontext);
        recyclerView_manager.setLayoutManager(layoutManager2);
        PeopleAdapter adapter2 = new PeopleAdapter(mcontext, managerList, token, group_id, user_id,2, privilege);
        recyclerView_manager.setAdapter(adapter2);

        recyclerView_member = view.findViewById(R.id.people_member_recyclerView);
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(mcontext);
        recyclerView_member.setLayoutManager(layoutManager3);
        PeopleAdapter adapter3 = new PeopleAdapter(mcontext, memberList, token, group_id, user_id,3, privilege);
        recyclerView_member.setAdapter(adapter3);
        return view;
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
