package com.example.wpy.teamattention.other;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wpy.teamattention.GroupActivity;
import com.example.wpy.teamattention.R;
import com.example.wpy.teamattention.database.Group;

import java.util.List;

/**
 * Created by wpy on 2017/11/15.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder>{
    private List<Group> mGroupList;
    private Context mcontext;
    private int user_id;
    private String account;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View GroupView;
        ImageView groupImage;
        TextView groupname;
        TextView groupContent;
        public ViewHolder(View view) {
            super(view);
            GroupView = view;
            groupname = view.findViewById(R.id.group_name);
            groupImage = (ImageView) view.findViewById(R.id.group_image);
            groupContent = (TextView)view.findViewById(R.id.group_content);
        }
    }
    public GroupAdapter(Context context, List<Group> groupList, int user_id, String account){
        mcontext = context;
        mGroupList = groupList;
        this.user_id = user_id;
        this.account = account;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.GroupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Group group = mGroupList.get(position);
                Intent intent = new Intent(mcontext, GroupActivity.class);
                intent.putExtra("token", group.token);
                intent.putExtra("group_id", group.id);
                intent.putExtra("user_id", user_id);
                intent.putExtra("account", account);
                mcontext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Group group = mGroupList.get(position);
        String name = group.group_name + "\n";
        String content = "群主: " + group.builder;
        content = content + '\n' + "管理员: ";
        for (int i = 0; i < group.group_manager_number; i++){
            content = content + group.group_manager[i] + " ";
        }
        if (position % 12 == 0){
            group.imageId = R.drawable.scenery1;
        }
        else if (position % 12 == 1){
            group.imageId = R.drawable.scenery2;
        }
        else if (position % 12 == 2){
            group.imageId = R.drawable.scenery3;
        }
        else if (position % 12 == 3){
            group.imageId = R.drawable.scenery4;
        }
        else if (position % 12 == 4){
            group.imageId = R.drawable.scenery5;
        }
        else if (position % 12 == 5){
            group.imageId = R.drawable.scenery6;
        }
        else if (position % 12 == 6){
            group.imageId = R.drawable.scenery7;
        }
        else if (position % 12 == 7){
            group.imageId = R.drawable.scenery8;
        }
        else if (position % 12 == 8){
            group.imageId = R.drawable.scenery9;
        }
        else if (position % 12 == 9){
            group.imageId = R.drawable.scenery10;
        }
        else if (position % 12 == 10){
            group.imageId = R.drawable.scenery11;
        }
        else if (position % 12 == 11){
            group.imageId = R.drawable.scenery12;
        }
        holder.groupImage.setImageResource(group.imageId);
        holder.groupContent.setText(content);
        holder.groupname.setText(name);
    }

    @Override
    public int getItemCount() {
        return mGroupList.size();
    }
}
