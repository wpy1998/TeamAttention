package com.example.wpy.teamattention;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wpy.teamattention.database.OkhttpAction;

public class AddWork extends AppCompatActivity implements View.OnClickListener{
    private Button btnDate, btnTime, store;
    private int year1, month1, day1, hour1, minute1;
    private EditText theme, content;
    private String token;
    private int user_id;
    private int group_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work);
        btnDate = (Button) findViewById(R.id.btnDatePickerDialog);
        btnTime = (Button) findViewById(R.id.btnTimePickerDialog);
        store = (Button)findViewById(R.id.add_work_store);
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        theme = (EditText)findViewById(R.id.add_work_name);
        content = (EditText)findViewById(R.id.add_work_content);
        final TextView show = findViewById(R.id.add_work_show);

        Intent intentfront = getIntent();
        token = intentfront.getStringExtra("token");
        user_id = intentfront.getIntExtra("user_id", 0);
        group_id = intentfront.getIntExtra("group_id", 0);

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String theme1 = theme.getText().toString();
                String content1 = content.getText().toString();
                if(theme1.equals("")){
                    Toast.makeText(AddWork.this, "主题为空", Toast.LENGTH_SHORT).show();
                }
                else if (year1 < 0||month1 == 0||day1 == 0||hour1 == 0||minute1 == 0){
                    Toast.makeText(AddWork.this, "请输入正确时间", Toast.LENGTH_SHORT).show();
                }
                else {
                    String remind_time = year1 + "";
                    if (month1 < 10){
                        remind_time = remind_time + "-0" + month1;
                    }else remind_time = remind_time + "-" + month1;
                    if (day1 < 10){
                        remind_time = remind_time + "-0" + day1;
                    }else remind_time = remind_time + "-" + day1;
                    remind_time = remind_time + "T";
                    if (hour1 < 10){
                        remind_time = remind_time + "0" + hour1;
                    }else remind_time = remind_time  + hour1;
                    if (minute1 < 10){
                        remind_time = remind_time + ":0" + minute1;
                    }else remind_time = remind_time + ":" + minute1;
                    remind_time = remind_time + ":01";
                    OkhttpAction okhttpAction = new OkhttpAction(AddWork.this);
                    okhttpAction.add_new_work(token, user_id, group_id, theme1, content1, remind_time, show);
                    for (;;){
                        if (show.getText().toString().equals("")){
                            SystemClock.sleep(20);
                        }
                        else break;
                    }
                    finish();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDatePickerDialog:
                DatePickerDialog datePicker=new DatePickerDialog(AddWork.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        Toast.makeText(AddWork.this, year+"年 "+(monthOfYear+1)+"月 "+dayOfMonth+"日", Toast.LENGTH_SHORT).show();
                        year1 = year;
                        month1 = monthOfYear + 1;
                        day1 = dayOfMonth;
                    }
                }, 2017, 10, 1);
                datePicker.show();
                break;

            case R.id.btnTimePickerDialog:
                TimePickerDialog time=new TimePickerDialog(AddWork.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // TODO Auto-generated method stub
                        Toast.makeText(AddWork.this, hourOfDay+"时 "+minute+"分", Toast.LENGTH_SHORT).show();
                        hour1 = hourOfDay;
                        minute1 = minute;
                    }
                }, 18, 25, true);
                time.show();
                break;
        }
    }
}
