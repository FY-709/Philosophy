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
import com.example.philosophy.db.entity.CharactersEntity;
import com.example.philosophy.db.entity.GenresEntity;
import com.example.philosophy.utils.ImageUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ViewHolder> {
    private Context context;
    private List<CharactersEntity> charactersList;
    private OnItemClickListener listener;

    public CharactersAdapter(Context context) {
        this.context = context;
    }

    public CharactersAdapter(List<CharactersEntity> charactersList) {
        this.charactersList = charactersList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_characters, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CharactersEntity character = charactersList.get(position);
        GenresEntity genre = AppDatabase.getInstance(context).genresDao().queryByNum(character.getgNum());

        holder.characterPic.setImageBitmap(ImageUtil.getImage(character.getcPic()));
        holder.characterName.setText(character.getcName());
        holder.genrePic.setImageBitmap(ImageUtil.getImage(genre.getgPic()));
        holder.characterGenre.setText(genre.getgName());
        holder.characterThoughts.setText(character.getcThoughts());

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
        ImageView characterPic;
        TextView characterName;
        ImageView genrePic;
        TextView characterGenre;
        TextView characterThoughts;

        public ViewHolder(View view) {
            super(view);
            characterPic = view.findViewById(R.id.image_characterPic);
            characterName = view.findViewById(R.id.text_characterName);
            genrePic = view.findViewById(R.id.image_genrePic);
            characterGenre = view.findViewById(R.id.text_characterGenre);
            characterThoughts = view.findViewById(R.id.text_characterThoughts);
        }
    }
}
