package com.example.wpy.teamattention;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wpy.teamattention.database.OkhttpAction;
import com.example.wpy.teamattention.database.Work;

import org.json.JSONArray;
import org.json.JSONObject;

public class WorkActivity extends AppCompatActivity {
    private String token;
    private int user_id;
    private int group_id;
    private int work_id;
    private String account;

    private TextView titleT;
    private TextView contentT;
    private TextView timeT;
    private Button change;
    private TextView delete;
    private TextView edit;
    OkhttpAction okhttpAction;
    private TextView show;

    private Work work = new Work();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        Intent intentfront = getIntent();
        token = intentfront.getStringExtra("token");
        user_id = intentfront.getIntExtra("user_id", 0);
        group_id = intentfront.getIntExtra("group_id", 0);
        work_id = intentfront.getIntExtra("work_id", 0);
        account = intentfront.getStringExtra("account");

        show = findViewById(R.id.work_show);
        okhttpAction = new OkhttpAction(WorkActivity.this);
        okhttpAction.get_work(token, group_id, show);
        for(int i = 0; i < 30; i++){
            if (show.getText().toString().equals("")){
                SystemClock.sleep(20);
            }
            else break;
        }
        String action = show.getText().toString();
        try{
            JSONObject jsonObject = new JSONObject(action);
            String action1 = jsonObject.getString("tasks");
            deal_action(action1);
        }catch (Exception e){
            e.printStackTrace();
        }

        titleT = findViewById(R.id.work_name);
        contentT = findViewById(R.id.work_content);
        timeT = findViewById(R.id.work_time);

        titleT.setText(work.title);
        contentT.setText(work.content);
        timeT.setText(work.year + "年" + work.month + "月" + work.day + "日" + work.hour + "时" + work.minute + "分提醒");
        edit = findViewById(R.id.work_edit);
        delete = findViewById(R.id.work_delete);
        change = findViewById(R.id.work_change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WorkActivity.this, "waiting for coming ture", Toast.LENGTH_SHORT).show();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkActivity.this, EditWork.class);
                intent.putExtra("account", work.account);
                intent.putExtra("user_id", user_id);
                intent.putExtra("group_id", group_id);
                intent.putExtra("work_id", work_id);
                intent.putExtra("title", work.title);
                intent.putExtra("content", work.content);
                intent.putExtra("year", work.year);
                intent.putExtra("month", work.month);
                intent.putExtra("day", work.day);
                intent.putExtra("hour", work.hour);
                intent.putExtra("minute",work.minute);
                intent.putExtra("token", token);
                startActivity(intent);
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okhttpAction.work_delete(token, work_id, group_id);
                finish();
            }
        });
    }
    private void deal_action(String action) {
        try{
            JSONArray jsonArray = new JSONArray(action);
            for (int i = 0 ; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                work.id = jsonObject.getInt("id");
                if (work.id == work_id){
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
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
