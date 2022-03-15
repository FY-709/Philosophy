package com.example.philosophy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.philosophy.R;
import com.example.philosophy.db.entity.BooksEntity;
import com.example.philosophy.utils.ImageUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeBooksAdapter extends RecyclerView.Adapter<HomeBooksAdapter.ViewHolder> {
    private Context context;
    private List<BooksEntity> booksList;
    private OnItemClickListener listener;
    private OnItemLongClickListener longClickListener;


    public HomeBooksAdapter(Context context) {
        this.context = context;
    }

    public HomeBooksAdapter(List<BooksEntity> booksList) {
        this.booksList = booksList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_books, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        BooksEntity book = booksList.get(position);

        holder.imageBook.setImageBitmap(ImageUtil.getImage(book.getbPic()));
        holder.textBook.setText(book.getbName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v, position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.onClick(v, position);
                }
                return true;
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

    public interface OnItemLongClickListener {
        void onClick(View v, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageBook;
        TextView textBook;

        public ViewHolder(View view) {
            super(view);
            imageBook = view.findViewById(R.id.image_bookPic);
            textBook = view.findViewById(R.id.text_bookName);
        }
    }
}