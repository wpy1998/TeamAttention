package com.example.wpy.teamattention.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wpy.teamattention.R;
import com.example.wpy.teamattention.WordActivity;
import com.example.wpy.teamattention.database.OkhttpAction;
import com.example.wpy.teamattention.database.Word;

import java.util.List;

import okhttp3.Response;


/**
 * Created by wpy on 2017/11/25.
 */

public class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ViewHolder>{
    private List<Word> mWordList;
    private Context mcontext;
    private String token;
    private int user_id;
    private int group_id;
    private int privilege;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View responseView;
        TextView response_content;
        TextView response_owner;
        public ViewHolder(View view) {
            super(view);
            responseView = view;
            response_content = view.findViewById(R.id.response_content);
            response_owner = view.findViewById(R.id.response_user);
        }
    }
    public ResponseAdapter(List<Word> wordList, Context context, String token, int user_id, int group_id, int privilege){
        mcontext = context;
        mWordList = wordList;
        this.user_id = user_id;
        this.token = token;
        this.group_id = group_id;
        this.privilege = privilege;
    }
    @Override
    public ResponseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.response_item, parent, false);
        final ResponseAdapter.ViewHolder holder = new ResponseAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ResponseAdapter.ViewHolder holder, int position) {
        Word word = mWordList.get(position);
        holder.response_owner.setText("发帖人: " + word.owner);
        holder.response_content.setText(word.content);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}
