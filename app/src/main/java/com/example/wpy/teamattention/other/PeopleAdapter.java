package com.example.wpy.teamattention.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wpy.teamattention.BuilderActivity;
import com.example.wpy.teamattention.ManagerActivity;
import com.example.wpy.teamattention.MemberActivity;
import com.example.wpy.teamattention.R;
import com.example.wpy.teamattention.database.User;

import java.util.List;

/**
 * Created by wpy on 2017/11/16.
 */

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder>{
    private List<User> mUserList;
    private Context mcontext;
    private String token;
    int group_id;
    private int rank;
    int user_id;
    int privilege;
    public PeopleAdapter(Context context, List<User> userList, String token, int group_id, int user_id, int rank,int privilege){
        mcontext = context;
        mUserList = userList;
        this.token = token;
        this.group_id = group_id;
        this.rank = rank;
        this.user_id = user_id;
        this.privilege = privilege;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        View userView;
        ImageView userImage;
        TextView username;
        public ViewHolder(View view) {
            super(view);
            userView = view;
            username = view.findViewById(R.id.people_name);
            userImage = view.findViewById(R.id.people_image);
        }
    }
    @Override
    public PeopleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_item, parent, false);
        final PeopleAdapter.ViewHolder holder = new PeopleAdapter.ViewHolder(view);
        holder.userView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                User user = mUserList.get(position);
                if (rank == 1){
                    Intent intent = new Intent(mcontext, BuilderActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("group_id", group_id);
                    intent.putExtra("rank", rank);
                    intent.putExtra("name", user.name);
                    intent.putExtra("id", user.id);
                    intent.putExtra("email", user.email);
                    intent.putExtra("privilege", privilege);
                    mcontext.startActivity(intent);
                }
                else if (rank == 2){
                    Intent intent = new Intent(mcontext, ManagerActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("group_id", group_id);
                    intent.putExtra("rank", rank);
                    intent.putExtra("name", user.name);
                    intent.putExtra("id", user.id);
                    intent.putExtra("email", user.email);
                    intent.putExtra("privilege", privilege);
                    mcontext.startActivity(intent);
                }
                else if (rank == 3){
                    Intent intent = new Intent(mcontext, MemberActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("group_id", group_id);
                    intent.putExtra("rank", rank);
                    intent.putExtra("name", user.name);
                    intent.putExtra("id", user.id);
                    intent.putExtra("email", user.email);
                    intent.putExtra("privilege", privilege);
                    mcontext.startActivity(intent);
                }
            }
        });
        return holder;
    }

    public void dealactivity(){
        if (Activity.class.isInstance(mcontext)){
            Activity activity = (Activity)mcontext;
            activity.finish();
        }
    }
    @Override
    public void onBindViewHolder(PeopleAdapter.ViewHolder holder, int position) {
        final User user = mUserList.get(position);
        holder.userImage.setImageResource(R.drawable.people);
        holder.username.setText(user.name);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
