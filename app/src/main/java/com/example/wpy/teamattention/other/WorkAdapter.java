package com.example.wpy.teamattention.other;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wpy.teamattention.R;
import com.example.wpy.teamattention.WorkActivity;
import com.example.wpy.teamattention.database.Group;
import com.example.wpy.teamattention.database.Work;

import java.util.List;

/**
 * Created by wpy on 2017/11/15.
 */

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder>{
    private List<Work> mWorkList;
    private Context mcontext;
    private String token;
    private int user_id;
    private int group_id;
    private TextView refresh;
    private String account;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View workView;
        ImageView workImage;
        TextView workContent;
        public ViewHolder(View view) {
            super(view);
            workView = view;
            workImage = (ImageView)view.findViewById(R.id.work_image);
            workContent = (TextView)view.findViewById(R.id.work_content);
        }
    }
    public WorkAdapter(List<Work> workList, Context context, String token, int user_id, int group_id, String account, TextView refresh){
        mcontext = context;
        mWorkList = workList;
        this.user_id = user_id;
        this.token = token;
        this.group_id = group_id;
        this.refresh = refresh;
        this.account = account;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.workView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Work work = mWorkList.get(position);
                Intent intent = new Intent(mcontext, WorkActivity.class);
                intent.putExtra("account", work.getAccount());
                intent.putExtra("user_id", user_id);
                intent.putExtra("group_id", group_id);
                intent.putExtra("work_id", work.id);
                intent.putExtra("token", token);
                mcontext.startActivity(intent);
            }
        });
        return (ViewHolder) holder;
    }

    @Override
    public void onBindViewHolder(WorkAdapter.ViewHolder holder, int position) {
        Work work = mWorkList.get(position);
        holder.workImage.setImageResource(work.getImageId());
        holder.workContent.setText(work.getTitle());
    }

    @Override
    public int getItemCount() {
        return mWorkList.size();
    }
}
