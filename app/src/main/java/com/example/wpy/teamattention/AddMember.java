package com.example.wpy.teamattention;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.wpy.teamattention.database.OkhttpAction;
import com.example.wpy.teamattention.database.User;
import com.example.wpy.teamattention.other.UserAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddMember extends AppCompatActivity {
    private AutoCompleteTextView id;
    private Button save;
    private TextView show;
    private RecyclerView choose;
    private Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        id = (AutoCompleteTextView)findViewById(R.id.add_member_id);
        save = (Button)findViewById(R.id.add_member);
        Intent intentfront = getIntent();
        final int group_id = intentfront.getIntExtra("group_id", 0);
        final String token = intentfront.getStringExtra("token");
        show = findViewById(R.id.add_member_show);
        choose = findViewById(R.id.add_member_recyclerView);
        search = findViewById(R.id.add_member_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkhttpAction okhttpAction = new OkhttpAction(AddMember.this);
                String message = id.getText().toString();
                okhttpAction.search_member(token, message, show);
                for (;;){
                    if (show.getText().toString().equals(""))
                        SystemClock.sleep(200);
                    else break;
                }
                String action = show.getText().toString();
                try {
                    JSONObject jsonObject = new JSONObject(action);
                    String users = jsonObject.getString("users");
                    JSONArray jsonArray = new JSONArray(users);
                    List<User> userList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        User user = new User();
                        user.name = object.getString("name");
                        user.id = object.getInt("id");
                        user.email = object.getString("email");
                        userList.add(user);
                    }
                    LinearLayoutManager layoutManager = new LinearLayoutManager(AddMember.this);
                    choose.setLayoutManager(layoutManager);
                    UserAdapter adapter = new UserAdapter(AddMember.this, userList, token, group_id, 0, show);
                    choose.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
