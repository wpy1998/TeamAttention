package com.example.wpy.teamattention;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wpy.teamattention.database.OkhttpAction;

public class AddResponse extends AppCompatActivity {
    private int group_id;
    private int subject_id;
    private String token;

    private TextView add_response;
    private EditText content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_response);

        add_response = findViewById(R.id.add_response);
        content = findViewById(R.id.add_response_content);

        Intent intentfront = getIntent();
        token = intentfront.getStringExtra("token");
        subject_id = intentfront.getIntExtra("subject_id", 0);
        group_id = intentfront.getIntExtra("group_id", 0);

        add_response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content1 = content.getText().toString();
                if (content1.equals("")){
                    Toast.makeText(AddResponse.this, "请输入您的评论", Toast.LENGTH_SHORT).show();
                }else {
                    OkhttpAction okhttpAction = new OkhttpAction(AddResponse.this);
                    okhttpAction.add_response(token, group_id, subject_id, content1);
                    finish();
                }
            }
        });
    }
}
