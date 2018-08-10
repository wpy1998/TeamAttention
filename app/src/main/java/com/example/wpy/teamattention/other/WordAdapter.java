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
import android.widget.Toast;

import com.example.wpy.teamattention.R;
import com.example.wpy.teamattention.WordActivity;
import com.example.wpy.teamattention.WorkActivity;
import com.example.wpy.teamattention.database.Group;
import com.example.wpy.teamattention.database.OkhttpAction;
import com.example.wpy.teamattention.database.Word;

import java.util.List;

/**
 * Created by wpy on 2017/11/23.
 */

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder>{
    private List<Word> mWordList;
    private Context mcontext;
    private String token;
    private int user_id;
    private int group_id;
    private String account;
    private int privilege;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View wordView;
        TextView word_title;
        TextView word_content;
        TextView word_owner;
        public ViewHolder(View view) {
            super(view);
            wordView = view;
            word_content = view.findViewById(R.id.word_content);
            word_owner = view.findViewById(R.id.word_owner);
            word_title = view.findViewById(R.id.word_title);
        }
    }
    public WordAdapter(List<Word> wordList, Context context, String token, int user_id, int group_id, String account, int privilege){
        mcontext = context;
        mWordList = wordList;
        this.user_id = user_id;
        this.token = token;
        this.group_id = group_id;
        this.account = account;
        this.privilege = privilege;
    }
    @Override
    public WordAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent, false);
        final WordAdapter.ViewHolder holder = new WordAdapter.ViewHolder(view);
        holder.wordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Word word = mWordList.get(position);
                Intent intent = new Intent(mcontext, WordActivity.class);
                intent.putExtra("group_id",group_id);
                intent.putExtra("token", token);
                intent.putExtra("subject_id", word.id);
                intent.putExtra("user_id", user_id);
                intent.putExtra("title", word.title);
                intent.putExtra("content", word.content);
                intent.putExtra("owner", word.owner);
                intent.putExtra("owner_id", word.owner_id);
                intent.putExtra("owner_email", word.owner_email);
                intent.putExtra("details", word.details);
                intent.putExtra("privilege", privilege);
                mcontext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(WordAdapter.ViewHolder holder, int position) {
        Word word = mWordList.get(position);
        holder.word_title.setText(word.title);
        holder.word_owner.setText( word.owner + ", 发布于" + word.year + "年"
        + word.month + "月"
        + word.day + "日"
        + word.hour + "时"
        + word.minute + "分");
        holder.word_content.setText(word.content);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}
