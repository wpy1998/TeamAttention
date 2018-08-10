package com.example.wpy.teamattention;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wpy.teamattention.database.Group;
import com.example.wpy.teamattention.database.OkhttpAction;
import com.example.wpy.teamattention.database.User;
import com.example.wpy.teamattention.other.GroupAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    android.support.v7.widget.RecyclerView recyclerView;
    private String token;
    private String account;
    private OkhttpAction okhttpAction;
    private ImageView add_group;
    private int user_id;
    private List<Group> groupList = new ArrayList<>();
    private int check;
    TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intentfront = getIntent();
        token = intentfront.getStringExtra("token");
        account = intentfront.getStringExtra("account");
        user_id = intentfront.getIntExtra("user_id", 0);

        LinearLayout add_work = findViewById(R.id.fragment1_add_work);
        add_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddGroup.class);
                intent.putExtra("token", token);
                intent.putExtra("user_id", user_id);
                intent.putExtra("account", account);
                startActivityForResult(intent, 0);
            }
        });

        okhttpAction = new OkhttpAction(MainActivity.this);
        show = findViewById(R.id.fragment1_add_group_show);
        show.setText("");
        okhttpAction.get_group(token,  show);
        for (;;){
            if (show.getText().toString().equals(""))
                SystemClock.sleep(50);
            else break;
        }
        String getShow = show.getText().toString();
        try {
            JSONObject jsonObject = new JSONObject(getShow);
            String action1 = jsonObject.getString("groups");
            deal_action(action1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        TextView mygroup = findViewById(R.id.mygroup);
        mygroup.setText("" + groupList.size());

        recyclerView = findViewById(R.id.fragment1_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        GroupAdapter adapter = new GroupAdapter(MainActivity.this, groupList, user_id, account);
        recyclerView.setAdapter(adapter);
    }
    public void deal_action(String action1) throws Exception {
        JSONArray jsonArray = new JSONArray(action1);
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Group group = new Group();
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
            group.imageId = R.drawable.group;
            groupList.add(group);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        intent.putExtra("token", token);
        intent.putExtra("account", account);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_user_message) {
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            intent.putExtra("token", token);
            intent.putExtra("user_id", user_id);
            intent.putExtra("account", account);
            startActivityForResult(intent, 0);
        }

        return super.onOptionsItemSelected(item);
    }
}
