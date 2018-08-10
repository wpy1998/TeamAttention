package com.example.wpy.teamattention;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wpy.teamattention.database.OkhttpAction;

import org.json.JSONException;
import org.json.JSONObject;

public class UserActivity extends AppCompatActivity {

    private String token;
    private int user_id;
    private String account;
    TextView show;

    private TextView Ename;
    private TextView Eemail;
    private TextView Eregister;
    private TextView Eage;
    private TextView Esex;
    private TextView Edepartment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intentfront = getIntent();
        token = intentfront.getStringExtra("token");
        user_id = intentfront.getIntExtra("user_id", 0);
        account = intentfront.getStringExtra("account");
        show = findViewById(R.id.user_show);
        OkhttpAction okhttpAction = new OkhttpAction(UserActivity.this);
        okhttpAction.get_user_message(token, user_id, show);
        for (int i = 0; i < 30; i++){
            if (show.getText().toString().equals("")){
                SystemClock.sleep(20);
            }
            else break;
        }
        Ename = findViewById(R.id.user_account);
        Eemail = findViewById(R.id.user_email);
        Eregister = findViewById(R.id.user_register_time);
        Eage = findViewById(R.id.user_age);
        Esex = findViewById(R.id.user_sex);
        Edepartment = findViewById(R.id.user_department);

        String action = show.getText().toString();
        String account1 = null, email = null, register_time, detail, sex = null, department = null, year = "0", month = "0", day = "0", hour = "0", minute = "0";
        int age = 0;
        try{
            JSONObject jsonObject = new JSONObject(action);
            account1 = jsonObject.getString("name");
            email = jsonObject.getString("email");
            register_time = jsonObject.getString("register_time");
            detail = jsonObject.getString("detail");

            JSONObject object = new JSONObject(detail);
            sex = object.getString("sex");
            department = object.getString("department");
            age = object.getInt("age");

            Toast.makeText(UserActivity.this, detail, Toast.LENGTH_SHORT).show();
            year = register_time.substring(0, 4);
            month = register_time.substring(5, 7);
            day = register_time.substring(8, 10);
            hour = register_time.substring(11, 13);
            minute = register_time.substring(14, 16);
        }catch (Exception e){
            e.printStackTrace();
        }


        Ename.setText(account1 + "");
        Eemail.setText(email + "");
        Edepartment.setText(department + "");
        Eage.setText(age + "");
        Esex.setText(sex + "");
        Eregister.setText(year + "年" + month + "月" + day + "日" + hour + "时" + minute + "分");
    }
}
