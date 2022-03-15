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
import com.example.philosophy.db.entity.BooksEntity;
import com.example.philosophy.db.entity.CharactersEntity;
import com.example.philosophy.utils.ImageUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {
    private Context context;
    private List<BooksEntity> booksList;
    private OnItemClickListener listener;

    public BooksAdapter(Context context) {
        this.context = context;
    }

    public BooksAdapter(List<BooksEntity> booksList) {
        this.booksList = booksList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        BooksEntity book = booksList.get(position);
        CharactersEntity character = AppDatabase.getInstance(context).charactersDao().queryByNum(book.getcNum());

        holder.bookPic.setImageBitmap(ImageUtil.getImage(book.getbPic()));
        holder.bookName.setText(book.getbName());
        holder.characterPic.setImageBitmap(ImageUtil.getImage(character.getcPic()));
        holder.bookCharacter.setText(character.getcName());
        holder.bookIntro.setText(book.getbIntro());

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
        return booksList.size();
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookPic;
        TextView bookName;
        ImageView characterPic;
        TextView bookCharacter;
        TextView bookIntro;

        public ViewHolder(View view) {
            super(view);
            bookPic = view.findViewById(R.id.image_bookPic);
            bookName = view.findViewById(R.id.text_bookName);
            characterPic = view.findViewById(R.id.image_characterPic);
            bookCharacter = view.findViewById(R.id.text_bookCharacter);
            bookIntro = view.findViewById(R.id.text_bookIntro);
        }
    }
}