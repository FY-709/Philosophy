package com.example.philosophy.user;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class FollowGenresActivity extends AppCompatActivity {
    private RecyclerView recyclerGenres;
    private TextView genreName;

    private String uTel;
    private int uNum;
    private int gNum;
    private String gName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_genres);
        init();
    }


    private void init() {
        SharedPreferences sp = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        uTel = sp.getString("userTel", "");
        uNum = AppDatabase.getInstance(this).usersDao().queryNumByTel(uTel);

        recyclerGenres = findViewById(R.id.recycler_genres);

        List<GenresEntity> genres = AppDatabase.getInstance(this).usersGenresDao().queryFollowedGenres(uNum);
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

                GenresEntity genre = AppDatabase.getInstance(FollowGenresActivity.this).genresDao().queryByName(gName);
                gNum = genre.getgNum();

                Intent intent = new Intent(FollowGenresActivity.this, GenreActivity.class);
                intent.putExtra("gNum", gNum);
                startActivity(intent);
            }
        });
    }
}
