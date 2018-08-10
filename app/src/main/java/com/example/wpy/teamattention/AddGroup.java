package com.example.wpy.teamattention;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wpy.teamattention.database.OkhttpAction;

public class AddGroup extends AppCompatActivity {
    private Button add_group;
    private EditText add_group_name;
    private String token;
    private OkhttpAction okhttpAction;
    TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        add_group = (Button)findViewById(R.id.add_group);
        add_group_name = (EditText)findViewById(R.id.add_group_name);

        Intent intentfront = getIntent();
        token = intentfront.getStringExtra("token");
        okhttpAction = new OkhttpAction(AddGroup.this);
        show = (TextView)findViewById(R.id.add_group_show);
        add_group.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name = add_group_name.getText().toString();
                if (name.equals("")){
                    Toast.makeText(AddGroup.this, "请输入项目名", Toast.LENGTH_SHORT).show();
                }else {
                    show.setText("");
                    okhttpAction.put_group(token, name, show);
                    for (;;){
                        if (show.getText().toString().equals(""))
                            SystemClock.sleep(200);
                        else break;
                    }
                    if (show.getText().toString().equals("")){
                        Toast.makeText(AddGroup.this, "System error", Toast.LENGTH_SHORT).show();
                    }else{
                        finish();
                    }
                }
            }
        });
    }
}
