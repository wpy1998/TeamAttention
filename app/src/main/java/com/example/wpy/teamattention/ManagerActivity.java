package com.example.wpy.teamattention;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wpy.teamattention.database.OkhttpAction;

public class ManagerActivity extends AppCompatActivity {
    private String token;
    ImageView image;
    TextView message;
    Button change;
    TextView set;
    TextView remove;
    private int group_id;
    private int user_id;
    int rank;
    TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        image = findViewById(R.id.manager_image);
        message = findViewById(R.id.manager_message);
        change = findViewById(R.id.manager_set_builder);
        set = findViewById(R.id.manager_set_member);
        remove = findViewById(R.id.manager_delete);

        Intent intentfront = getIntent();
        token = intentfront.getStringExtra("token");
        user_id = intentfront.getIntExtra("user_id", 0);
        group_id = intentfront.getIntExtra("group_id", 0);
        rank = intentfront.getIntExtra("rank", 3);
        image.setImageResource(R.drawable.people);
        final int id = intentfront.getIntExtra("id", 0);
        String email = intentfront.getStringExtra("email");
        String name = intentfront.getStringExtra("name");
        final int privilege = intentfront.getIntExtra("privilege", 0);

        message.setText("账户: " + name + "\nemail: " + email);
        show = findViewById(R.id.manager_show);
        final OkhttpAction okhttpAction = new OkhttpAction(ManagerActivity.this);
        Toast.makeText(ManagerActivity.this, "user_id = " + user_id, Toast.LENGTH_SHORT).show();
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (privilege == 0){
                    Toast.makeText(ManagerActivity.this, "您无此权限", Toast.LENGTH_SHORT).show();
                }
                else {
                    okhttpAction.set_rank(token,id,group_id,"builder", show);
                    finish();
                }
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (privilege == 0){
                    Toast.makeText(ManagerActivity.this, "您无此权限", Toast.LENGTH_SHORT).show();
                }
                else {
                    okhttpAction.set_rank(token,id,group_id,"member", show);
                    finish();
                }
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (privilege != 2){
                    Toast.makeText(ManagerActivity.this, "您无此权限", Toast.LENGTH_SHORT).show();
                }
                else {
                    okhttpAction.delete_member(token, group_id, id, show);
                    finish();
                }
            }
        });
    }
}
