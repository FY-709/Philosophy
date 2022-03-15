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

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.ViewHolder> {
    private Context context;
    private List<GenresEntity> genresList;
    private OnItemClickListener listener;

    public GenresAdapter(Context context) {
        this.context = context;
    }

    public GenresAdapter(List<GenresEntity> genresList) {
        this.genresList = genresList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genres, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        GenresEntity genre = genresList.get(position);

        holder.genrePic.setImageBitmap(ImageUtil.getImage(genre.getgPic()));
        holder.genreName.setText(genre.getgName());
        holder.genreIntro.setText(genre.getgIntro());

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
        ImageView genrePic;
        TextView genreName;
        TextView genreIntro;

        public ViewHolder(View view) {
            super(view);
            genrePic = view.findViewById(R.id.image_genrePic);
            genreName = view.findViewById(R.id.text_genreName);
            genreIntro = view.findViewById(R.id.text_genreIntro);
        }
    }
}