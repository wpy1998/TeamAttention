package com.example.wpy.teamattention.other;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wpy.teamattention.R;
import com.example.wpy.teamattention.database.OkhttpAction;
import com.example.wpy.teamattention.database.User;

import java.util.List;

/**
 * Created by wpy on 2017/11/15.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{
    private List<User> mUserList;
    private Context mcontext;
    private String token;
    int group_id;
    int style;
    TextView show;
    public UserAdapter(Context context, List<User> userList, String token, int group_id, int style, TextView show){
        mcontext = context;
        mUserList = userList;
        this.token = token;
        this.group_id = group_id;
        this.style = style;
        this.show = show;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        View userView;
        ImageView userImage;
        TextView username;
        Button useradd;
        public ViewHolder(View view) {
            super(view);
            userView = view;
            username = view.findViewById(R.id.user_name);
            userImage = view.findViewById(R.id.user_image);
            useradd = view.findViewById(R.id.user_add);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        if (style == 0){
            holder.useradd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    User user = mUserList.get(position);
                    OkhttpAction okhttpAction = new OkhttpAction(mcontext);
                    okhttpAction.add_member(token, user.id, group_id);
                }
            });
        }
        else if (style == 1){
            holder.userView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
                    User user = mUserList.get(position);
                    OkhttpAction okhttpAction = new OkhttpAction(mcontext);
                    show.setText("");
                    okhttpAction.set_rank(token, user.id, group_id, "manager", show);
                    for (;;){
                        if (show.getText().toString().equals("")){
                            SystemClock.sleep(200);
                        }
                        else break;
                    }
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        final User user = mUserList.get(position);
        holder.userImage.setImageResource(R.drawable.people);
        holder.username.setText(user.name);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
