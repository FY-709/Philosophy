package com.example.philosophy.lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.philosophy.R;
import com.example.philosophy.adapter.GenresAdapter;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.GenresEntity;
import com.example.philosophy.details.GenreActivity;

import java.util.List;

public class UserGenresActivity extends AppCompatActivity {
    private RecyclerView recyclerGenres;

    private TextView genreName;

    private int gNum;
    private String gName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_genres);
        init();
    }

    private void init() {
        recyclerGenres = findViewById(R.id.recycler_genres);

        List<GenresEntity> genres = AppDatabase.getInstance(this).genresDao().query();
        RecyclerView.Adapter adapter = new GenresAdapter(genres);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerGenres.setHasFixedSize(true);
        recyclerGenres.setLayoutManager(layoutManager);
        recyclerGenres.setAdapter(adapter);

        ((GenresAdapter) adapter).setOnItemClickListener(new GenresAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                genreName = view.findViewById(R.id.text_genreName);
                gName = genreName.getText().toString();

                GenresEntity genre = AppDatabase.getInstance(UserGenresActivity.this).genresDao().queryByName(gName);
                gNum = genre.getgNum();

                Intent intent = new Intent(UserGenresActivity.this, GenreActivity.class);
                intent.putExtra("gNum", gNum);
                startActivity(intent);
            }
        });
    }
}
