package com.example.philosophy.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.philosophy.R;
import com.example.philosophy.adapter.HomeBooksAdapter;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.BooksEntity;
import com.example.philosophy.details.BookActivity;
import com.example.philosophy.reader.ReaderActivity;
import com.example.philosophy.reader.main.TxtConfig;

import java.util.List;
import java.util.Objects;

public class BookshelfFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);

        final SharedPreferences sp = getContext().getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        final String uTel = sp.getString("userTel", "");
        String identifier = Build.SERIAL;

        if (!Objects.equals(uTel, null) && !Objects.equals(uTel, "")) {
            int uNum = AppDatabase.getInstance(getActivity()).usersDao().queryNumByTel(uTel);
            identifier = Integer.toString(uNum);
        }


        final RecyclerView recyclerBooks = view.findViewById(R.id.recycler_books);
        final RecyclerView.Adapter adapter;
        final GridLayoutManager layoutManager;

        List<BooksEntity> books = AppDatabase.getInstance(getActivity()).bookshelfDao().queryBooks(identifier);

        layoutManager = new GridLayoutManager(getActivity(), 3);

        adapter = new HomeBooksAdapter(books);
        recyclerBooks.setItemViewCacheSize(500);
        recyclerBooks.setDrawingCacheEnabled(true);
        recyclerBooks.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerBooks.setNestedScrollingEnabled(false);
        recyclerBooks.setHasFixedSize(true);
        recyclerBooks.setLayoutManager(layoutManager);
        recyclerBooks.setAdapter(adapter);

        ((HomeBooksAdapter) adapter).setOnItemClickListener(new HomeBooksAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                TextView bookName = view.findViewById(R.id.text_bookName);
                String bName = bookName.getText().toString();

                BooksEntity book = AppDatabase.getInstance(getActivity()).booksDao().queryByName(bName);
                String bContent = book.getbContent();

                /*Intent intent = new Intent(getActivity(), ReaderActivity.class);
                intent.putExtra("bContent", bContent);
                startActivity(intent);*/

                TxtConfig.saveIsOnVerticalPageMode(getActivity(), false);
                ReaderActivity.loadTxtFile(getActivity(), bContent);
            }
        });

        ((HomeBooksAdapter) adapter).setOnItemLongClickListener(new HomeBooksAdapter.OnItemLongClickListener() {
            @Override
            public void onClick(View view, int position) {
                TextView bookName = view.findViewById(R.id.text_bookName);
                String bName = bookName.getText().toString();

                int bNum = AppDatabase.getInstance(getActivity()).booksDao().queryNumByName(bName);

                Intent intent = new Intent(getActivity(), BookActivity.class);
                intent.putExtra("bNum", bNum);
                startActivity(intent);
            }
        });

        return view;
    }
}
