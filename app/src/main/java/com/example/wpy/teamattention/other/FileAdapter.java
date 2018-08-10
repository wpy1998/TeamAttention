package com.example.wpy.teamattention.other;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wpy.teamattention.FileActivity;
import com.example.wpy.teamattention.R;
import com.example.wpy.teamattention.database.FileT;

import java.io.File;
import java.util.List;

/**
 * Created by wpy on 2017/12/5.
 */

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder>{
    private List<FileT> fileList;
    private Context mcontext;
    private String token;
    private int user_id;
    private int group_id;
    private String account;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View fileView;
        ImageView fileImage;
        TextView filename;
        public ViewHolder(View view) {
            super(view);
            fileView = view;
            fileImage = view.findViewById(R.id.file_image);
            filename = view.findViewById(R.id.file_name);
        }
    }
    public FileAdapter(List<FileT> fileList, Context context, String token, int user_id, int group_id, String account){
        mcontext = context;
        this.fileList = fileList;
        this.user_id = user_id;
        this.token = token;
        this.group_id = group_id;
        this.account = account;
    }
    @Override
    public FileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, parent, false);
        final FileAdapter.ViewHolder holder = new FileAdapter.ViewHolder(view);
        holder.fileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                FileT fileT = fileList.get(position);
                fileT.imageId = holder.fileImage.getId();
                Intent intent = new Intent(mcontext, FileActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("user_id",user_id);
                intent.putExtra("group_id", group_id);
                intent.putExtra("filename", fileT.filename);
                intent.putExtra("file_url", fileT.file_url);
                intent.putExtra("account", fileT.upload_user);
                intent.putExtra("imageId", fileT.imageId);
                mcontext.startActivity(intent);
            }
        });
        return (FileAdapter.ViewHolder) holder;
    }
    @Override
    public void onBindViewHolder(FileAdapter.ViewHolder holder, int position) {
        FileT filet = fileList.get(position);
        holder.filename.setText(filet.filename);
        holder.fileImage.setImageResource(R.drawable.file_txt);
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }
}
