package com.example.wpy.teamattention.database;

/**
 * Created by wpy on 2017/11/23.
 */

public class Group {
    public int imageId;
    public int id;
    public String token;
    public String group_name;
    public String[] group_member;
    public String[] group_manager;
    public String builder;

    public int group_member_number;
    public int group_manager_number;

    public int[] group_member_id;
    public int[] group_manager_id;
    public int builder_id;

    public String[] group_member_email;
    public String[] group_manager_email;
    public String group_builder_email;
}
