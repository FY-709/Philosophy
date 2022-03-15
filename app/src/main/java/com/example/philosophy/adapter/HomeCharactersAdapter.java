package com.example.philosophy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.philosophy.R;
import com.example.philosophy.db.entity.CharactersEntity;
import com.example.philosophy.utils.ImageUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeCharactersAdapter extends RecyclerView.Adapter<HomeCharactersAdapter.ViewHolder> {
    private Context context;
    private List<CharactersEntity> charactersList;
    private OnItemClickListener listener;

    public HomeCharactersAdapter(Context context) {
        this.context = context;
    }

    public HomeCharactersAdapter(List<CharactersEntity> charactersList) {
        this.charactersList = charactersList;
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
        CharactersEntity character = charactersList.get(position);

        holder.imagePic.setImageBitmap(ImageUtil.getImage(character.getcPic()));
        holder.textName.setText(character.getcName());

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
        return charactersList.size();
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
