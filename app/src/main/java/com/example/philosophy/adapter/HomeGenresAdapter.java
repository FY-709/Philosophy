package com.example.philosophy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.philosophy.R;
import com.example.philosophy.db.entity.GenresEntity;
import com.example.philosophy.utils.ImageUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeGenresAdapter extends RecyclerView.Adapter<HomeGenresAdapter.ViewHolder> {
    private Context context;
    private List<GenresEntity> genresList;
    private OnItemClickListener listener;

    public HomeGenresAdapter(Context context) {
        this.context = context;
    }

    public HomeGenresAdapter(List<GenresEntity> genresList) {
        this.genresList = genresList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        GenresEntity genre = genresList.get(position);

        holder.imagePic.setImageBitmap(ImageUtil.getImage(genre.getgPic()));
        holder.textName.setText(genre.getgName());

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
        return genresList.size();
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePic;
        TextView textName;

        public ViewHolder(View view) {
            super(view);
            imagePic = view.findViewById(R.id.image_pic);
            textName = view.findViewById(R.id.text_name);
        }
    }
}
