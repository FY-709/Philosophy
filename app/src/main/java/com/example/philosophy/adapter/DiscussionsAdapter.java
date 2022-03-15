package com.example.philosophy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.philosophy.R;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.DiscussionsEntity;
import com.example.philosophy.db.entity.UsersEntity;
import com.example.philosophy.utils.ImageUtil;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;

public class DiscussionsAdapter extends RecyclerView.Adapter<DiscussionsAdapter.ViewHolder> {
    private Context context;
    private List<DiscussionsEntity> discussionsList;
    private OnItemClickListener listener;

    public DiscussionsAdapter(Context context) {
        this.context = context;
    }

    public DiscussionsAdapter(List<DiscussionsEntity> discussionsList) {
        this.discussionsList = discussionsList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discussions, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        DiscussionsEntity discussion = discussionsList.get(position);
        int uNum = discussion.getuNum();
        UsersEntity user = AppDatabase.getInstance(context).usersDao().queryByNum(uNum);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        holder.userPic.setImageBitmap(ImageUtil.getImage(user.getuPic()));
        if (user.getuName() != null) {
            holder.userName.setText(user.getuName());
        }
        holder.discussionsTime.setText(simpleDateFormat.format(discussion.getdTime()));
        holder.discussionContent.setText(discussion.getdContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return discussionsList.size();
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userPic;
        TextView userName;
        TextView discussionsTime;
        TextView discussionContent;

        public ViewHolder(View view) {
            super(view);
            userPic = view.findViewById(R.id.image_userPic);
            userName = view.findViewById(R.id.text_userName);
            discussionsTime = view.findViewById(R.id.text_discussionsTime);
            discussionContent = view.findViewById(R.id.text_discussions);
        }
    }
}
