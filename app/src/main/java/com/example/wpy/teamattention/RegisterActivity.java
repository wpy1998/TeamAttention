package com.example.wpy.teamattention;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wpy.teamattention.database.OkhttpAction;

public class RegisterActivity extends AppCompatActivity {
    private Button register;
    private EditText username;
    private EditText password;
    private EditText email;
    private TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button)findViewById(R.id.register);
        username = (EditText)findViewById(R.id.register_username);
        password = (EditText)findViewById(R.id.register_password);
        email = (EditText)findViewById(R.id.register_email);
        show = (TextView)findViewById(R.id.register_show);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username1 = username.getText().toString();
                String password1 = password.getText().toString();
                String email1 = email.getText().toString();
                Toast.makeText(RegisterActivity.this, "carry", Toast.LENGTH_SHORT).show();
                if (username1.equals("")){
                    Toast.makeText(RegisterActivity.this, "请输入账户", Toast.LENGTH_SHORT).show();
                }
                else if (password1.equals("")){
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }
                else if (email1.equals("")){
                    Toast.makeText(RegisterActivity.this, "请输入邮箱", Toast.LENGTH_SHORT).show();
                }else {
                    OkhttpAction okhttpAction = new OkhttpAction(RegisterActivity.this);
                    try {
                        okhttpAction.RegisterAccount(username1, password1, email1, show);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (;;){
                        if (show.getText().toString().equals(""))
                            SystemClock.sleep(200);
                        else break;
                    }
                    String end = show.getText().toString();
                    if (end.equals("注册成功")){
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this, "发生错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
