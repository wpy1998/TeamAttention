package com.example.wpy.teamattention;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

public class EditWork extends AppCompatActivity {
    private EditText ymd;
    private EditText hm;
    private EditText content;
    private TextView theme;
    private Button btnDatePickerDialog;
    private Button btnTimePickerDialog;
    private String token;
    private int user_id;
    private int group_id;
    private int work_id;
    private int year1;
    private int month1;
    private int day1;
    private int hour1;
    private int minute1;
    private String account1;
    private String content1;
    private String title1;
    private Button save;
    private Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_work);

        ymd = (EditText)findViewById(R.id.edit_ymd);
        hm = (EditText)findViewById(R.id.edit_hm);
        content = (EditText)findViewById(R.id.edit_content);
        theme = (TextView)findViewById(R.id.edit_theme);
        btnDatePickerDialog = (Button)findViewById(R.id.edit_btnDatePickerDialog);
        btnTimePickerDialog = (Button)findViewById(R.id.edit_btnTimePickerDialog);
        save = (Button)findViewById(R.id.edit_save);
        delete = (Button)findViewById(R.id.edit_delete);

        Intent intentfront = getIntent();
        account1 = intentfront.getStringExtra("account");
        title1 = intentfront.getStringExtra("title");
        content1 = intentfront.getStringExtra("content");
        year1 = intentfront.getIntExtra("year", 0);
        month1 = intentfront.getIntExtra("month", 0);
        day1 = intentfront.getIntExtra("day", 0);
        hour1 = intentfront.getIntExtra("hour", 0);
        minute1 = intentfront.getIntExtra("minute", 0);
        token = intentfront.getStringExtra("token");
        user_id = intentfront.getIntExtra("user_id", 0);
        group_id = intentfront.getIntExtra("group_id", 0);
        work_id = intentfront.getIntExtra("work_id", 0);

        theme.setText("任务：" + title1);
        ymd.setText(year1 + "年" + month1 + "月" + day1 + "日");
        hm.setText(hour1 + "时" + minute1 + "分");
        content.setText(content1);

        btnDatePickerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker=new DatePickerDialog(EditWork.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        Toast.makeText(EditWork.this, year+"年 "+(monthOfYear+1)+"月 "+dayOfMonth+"日", Toast.LENGTH_SHORT).show();
                        year1 = year;
                        month1 = monthOfYear + 1;
                        day1 = dayOfMonth;
                        ymd.setText(year1 + "年" + month1 + "月" + day1 + "日");
                    }
                }, 2017, 10, 1);
                datePicker.show();
            }
        });
        btnTimePickerDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog time=new TimePickerDialog(EditWork.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // TODO Auto-generated method stub
                        Toast.makeText(EditWork.this, hourOfDay+"时 "+minute+"分", Toast.LENGTH_SHORT).show();
                        hour1 = hourOfDay;
                        minute1 = minute;
                        hm.setText(hour1 + "时" + minute1 + "分");
                    }
                }, 18, 25, true);
                time.show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (year1 < 0||month1 == 0||day1 == 0||hour1 == 0||minute1 == 0){
                    Toast.makeText(EditWork.this, "请输入正确时间", Toast.LENGTH_SHORT).show();
                }
                else {
                    OkhttpAction okhttpAction = new OkhttpAction(EditWork.this);
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
                    okhttpAction.work_update(token, work_id, user_id, group_id, title1, content1, remind_time);
                    finish();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkhttpAction okhttpAction = new OkhttpAction(EditWork.this);
                okhttpAction.work_delete(token, work_id, group_id);
                finish();
            }
        });
    }
}
