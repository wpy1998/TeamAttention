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
import com.example.wpy.teamattention.database.Message2;

import java.util.List;

/**
 * Created by wpy on 2017/11/26.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    private List<Message2> messageList;
    private Context mcontext;
    private String account;
    private String token;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View messageView;
        ImageView messageImage;
        TextView messageContent;
        TextView messageName;
        public ViewHolder(View view) {
            super(view);
            messageView = view;
            messageImage = (ImageView) view.findViewById(R.id.message_image);
            messageContent = (TextView)view.findViewById(R.id.message_content);
            messageName = view.findViewById(R.id.message_user);
        }
    }
    public MessageAdapter(Context context, List<Message2> messList, String token, String account){
        mcontext = context;
        messageList = messList;
        this.account = account;
        this.token = token;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {
        Message2 message = messageList.get(position);
        holder.messageImage.setImageResource(R.drawable.people);
        holder.messageContent.setText(message.message);
        holder.messageName.setText(message.name);
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        final MessageAdapter.ViewHolder holder = new MessageAdapter.ViewHolder(view);
        return holder;
    }
    @Override
    public int getItemCount() {
        return messageList.size();
    }
}
