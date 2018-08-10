package com.example.wpy.teamattention;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wpy.teamattention.database.OkhttpAction;

public class AddMessage extends AppCompatActivity {

    public String token;
    private int group_id;
    public EditText content;
    public AutoCompleteTextView title;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);

        save = findViewById(R.id.add_message);
        title = findViewById(R.id.add_message_name);
        content = findViewById(R.id.add_message_content);
        Intent intentfront = getIntent();
        token = intentfront.getStringExtra("token");
        group_id = intentfront.getIntExtra("group_id", 0);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title1 = title.getText().toString();
                String content1 = content.getText().toString();
                if (title1.equals("")){
                    Toast.makeText(AddMessage.this, "请输入新帖主题", Toast.LENGTH_SHORT).show();
                }else if (content1.equals("")){
                    Toast.makeText(AddMessage.this, "请输入新帖内容", Toast.LENGTH_SHORT).show();
                }else {
                    OkhttpAction okhttpAction = new OkhttpAction(AddMessage.this);
                    okhttpAction.add_new_message(token, title1, content1, group_id);
                    finish();
                }
            }
        });
    }
}
