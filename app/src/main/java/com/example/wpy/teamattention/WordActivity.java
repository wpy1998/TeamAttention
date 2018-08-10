package com.example.wpy.teamattention;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wpy.teamattention.database.OkhttpAction;
import com.example.wpy.teamattention.database.Word;
import com.example.wpy.teamattention.other.ResponseAdapter;
import com.example.wpy.teamattention.other.WorkAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WordActivity extends AppCompatActivity {
    android.support.v7.widget.RecyclerView recyclerView;

    private String token;
    private int group_id;
    private int user_id;
    private int privilege;
    private Word word = new Word();
    private List<Word> responseList = new ArrayList<>();

    private TextView title;
    private TextView content;
    private TextView owner;
    private TextView delete;
    private TextView show;
    private TextView add_response;

    ResponseAdapter adapter;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        title = findViewById(R.id.word_show_title);
        content = findViewById(R.id.word_show_content);
        owner = findViewById(R.id.word_show_owner);
        delete = findViewById(R.id.word_show_delete);
        show = findViewById(R.id.word_show);
        add_response = findViewById(R.id.add_response);

        Intent intentfront = getIntent();
        token = intentfront.getStringExtra("token");
        group_id = intentfront.getIntExtra("group_id", 0);
        user_id = intentfront.getIntExtra("user_id", 0);
        word.id = intentfront.getIntExtra("subject_id", 0);
        word.title = intentfront.getStringExtra("title");
        word.content = intentfront.getStringExtra("content");
        word.owner = intentfront.getStringExtra("owner");
        word.owner_id = intentfront.getIntExtra("owner_id", 0);
        word.owner_email = intentfront.getStringExtra("owner_email");
        word.details = intentfront.getStringExtra("details");
        privilege = intentfront.getIntExtra("privilege", 0);

        title.setText(word.title);
        content.setText(word.content);
        owner.setText("楼主" + word.owner);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (privilege == 0 && (user_id != word.owner_id)){
                    Toast.makeText(WordActivity.this, "您无此权限", Toast.LENGTH_SHORT).show();
                }
                else {
                    OkhttpAction okhttpAction = new OkhttpAction(WordActivity.this);
                    okhttpAction.delete_message(token, group_id, word.id);
                    finish();
                }
            }
        });
        add_response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WordActivity.this, AddResponse.class);
                intent.putExtra("token", token);
                intent.putExtra("group_id", group_id);
                intent.putExtra("subject_id", word.id);
                startActivityForResult(intent, 0);

            }
        });
        OkhttpAction okhttpAction = new OkhttpAction(WordActivity.this);
        okhttpAction.get_response(token, group_id, word.id, show);
        for (int i = 0 ; i < 30; i++){
            if (show.getText().toString().equals("")){
                SystemClock.sleep(20);
            }
            else break;
        }
        String action = show.getText().toString();
        try{
            JSONObject jsonObject = new JSONObject(action);
            String action1 = jsonObject.getString("posts");
            deal_action(action1);
        }catch (Exception e){
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.word_show_response);
        layoutManager = new LinearLayoutManager(WordActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ResponseAdapter(responseList, WordActivity.this, token, user_id, group_id, privilege);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = new Intent(WordActivity.this, WordActivity.class);
        intent.putExtra("group_id",group_id);
        intent.putExtra("token", token);
        intent.putExtra("subject_id", word.id);
        intent.putExtra("user_id", user_id);
        intent.putExtra("title", word.title);
        intent.putExtra("content", word.content);
        intent.putExtra("owner", word.owner);
        intent.putExtra("owner_id", word.owner_id);
        intent.putExtra("owner_email", word.owner_email);
        intent.putExtra("details", word.details);
        intent.putExtra("privilege", privilege);
        startActivity(intent);
    }

    public void deal_action(String action){
        try {
            JSONArray jsonArray = new JSONArray(action);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Word word = new Word();
                word.content = jsonObject.getString("content");
                word.title = jsonObject.getString("title");
                word.id = jsonObject.getInt("id");

                String create_user = jsonObject.getString("create_user");
                JSONObject object = new JSONObject(create_user);
                word.owner = object.getString("name");
                word.owner_email = object.getString("email");
                word.owner_id = object.getInt("id");

                String time = jsonObject.getString("create_time");
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
                responseList.add(word);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
