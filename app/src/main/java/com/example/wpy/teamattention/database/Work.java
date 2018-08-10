package com.example.wpy.teamattention.database;

/**
 * Created by wpy on 2017/11/23.
 */

public class Work {
    public int imageId;
    public String account;
    public int group_id;
    public int user_id;
    public String content;
    public String title;
    public int day;
    public int month;
    public int year;
    public int hour;
    public int minute;
    public int id;

    public int getImageId() {
        return imageId;
    }

    public int getDay() {
        return day;
    }

    public int getGroup_id() {
        return group_id;
    }

    public int getMonth() {
        return month;
    }

    public int getHour() {
        return hour;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getMinute() {
        return minute;
    }

    public int getYear() {
        return year;
    }

    public String getAccount() {
        return account;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}
