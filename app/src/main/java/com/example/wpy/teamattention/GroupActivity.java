package com.example.wpy.teamattention;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wpy.teamattention.database.Group;
import com.example.wpy.teamattention.database.OkhttpAction;
import com.example.wpy.teamattention.other.Fragment_group1;
import com.example.wpy.teamattention.other.Fragment_group2;
import com.example.wpy.teamattention.other.Fragment_group3;
import com.example.wpy.teamattention.other.Fragment_group4;
import com.example.wpy.teamattention.other.Fragment_group5;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by wpy on 2017/11/23.
 */

public class GroupActivity extends AppCompatActivity {
    private Button fragment_group1;
    private Button fragment_group2;
    private Button fragment_group3;
    private Button fragment_group4;
    private Button fragment_group5;

    Group group = new Group();
    String token;
    private String account;
    int user_id;
    int group_id;
    int privilege = 0;
    private TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Intent intentfront = getIntent();
        token = intentfront.getStringExtra("token");
        account = intentfront.getStringExtra("account");
        group_id = intentfront.getIntExtra("group_id", 0);
        user_id = intentfront.getIntExtra("user_id", 0);
        int choose = intentfront.getIntExtra("choose", 0);
        OkhttpAction okhttpAction = new OkhttpAction(GroupActivity.this);
        show = findViewById(R.id.group_show);
        okhttpAction.get_single_group(token, group_id, show);
        for (int i = 0; i < 30; i++){
            if (show.getText().toString().equals("")){
                SystemClock.sleep(20);
            }
            else break;
        }
        final String action = show.getText().toString();
        deal_action(action);

        for (int i = 0; i < group.group_manager_number; i++){
            if (group.group_manager_id[i] == user_id){
                privilege = 1;
                break;
            }
        }
        if (group.builder_id == user_id){
            privilege = 2;
        }

        if (choose == 0){
            Fragment_group1 fragmentMain = new Fragment_group1(GroupActivity.this, token, account, user_id, group_id, privilege);
            replaceFragment(fragmentMain);
        }
        else if (choose == 1){
            Fragment_group2 fragment = new Fragment_group2(GroupActivity.this, token, account, user_id, group_id, privilege);
            replaceFragment(fragment);
        }
        else if (choose == 2){
            Fragment_group3 fragment = new Fragment_group3(GroupActivity.this, token, user_id, group_id, privilege, account);
            replaceFragment(fragment);
        }
        else if (choose == 3){
            Fragment_group4 fragment = new Fragment_group4(GroupActivity.this, token,  user_id, group_id, account, privilege);
            replaceFragment(fragment);
        }
        else{
            Fragment_group5 fragment = new Fragment_group5(GroupActivity.this, token, group_id, user_id, account);
            replaceFragment(fragment);
        }

        fragment_group1 = findViewById(R.id.group_fragment1);
        fragment_group2 = findViewById(R.id.group_fragment2);
        fragment_group3 = findViewById(R.id.group_fragment3);
        fragment_group4 = findViewById(R.id.group_fragment4);
        fragment_group5 = findViewById(R.id.group_fragment5);
        initView();

        fragment_group1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_group1 fragment = new Fragment_group1(GroupActivity.this, token, account, user_id, group_id, privilege);
                replaceFragment(fragment);
            }
        });
        fragment_group2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_group2 fragment = new Fragment_group2(GroupActivity.this, token, account, user_id, group_id, privilege);
                replaceFragment(fragment);
            }
        });
        fragment_group3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_group3 fragment = new Fragment_group3(GroupActivity.this, token, user_id, group_id, privilege, account);
                replaceFragment(fragment);
            }
        });
        fragment_group4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_group4 fragment = new Fragment_group4(GroupActivity.this, token,  user_id, group_id, account, privilege);
                replaceFragment(fragment);
            }
        });
        fragment_group5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_group5 fragment = new Fragment_group5(GroupActivity.this, token, group_id, user_id, account);
                replaceFragment(fragment);
            }
        });
    }
    public void deal_action(String action){
        try{
            JSONObject jsonObject = new JSONObject(action);
            group = new Group();
            group.token = this.token;
            group.group_manager_number = 0;
            group.group_manager_number = 0;
            String group_name = jsonObject.getString("name");
            group.group_name = group_name;
            group.id = jsonObject.getInt("id");

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
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void initView(){
        Drawable fragment_task = getResources().getDrawable(R.drawable.task);
        fragment_task.setBounds(0, 0, 75, 75);
        fragment_group1.setCompoundDrawables(null, fragment_task, null, null);

        Drawable fragment_file = getResources().getDrawable(R.drawable.file);
        fragment_file.setBounds(0, 0, 75, 75);
        fragment_group2.setCompoundDrawables(null, fragment_file, null, null);

        Drawable fragment_communicate = getResources().getDrawable(R.drawable.communicate);
        fragment_communicate.setBounds(0, 0, 75, 75);
        fragment_group3.setCompoundDrawables(null, fragment_communicate, null, null);

        Drawable fragment_manage = getResources().getDrawable(R.drawable.manage);
        fragment_manage.setBounds(0, 0, 75, 75);
        fragment_group4.setCompoundDrawables(null, fragment_manage, null, null);

        Drawable fragment_dynamic = getResources().getDrawable(R.drawable.dynamic);
        fragment_dynamic.setBounds(0, 0, 75, 75);
        fragment_group5.setCompoundDrawables(null, fragment_dynamic, null, null);
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.group_layout, fragment);
        transaction.commit();
    }
}
