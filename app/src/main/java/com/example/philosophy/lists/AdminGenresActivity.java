package com.example.philosophy.lists;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.philosophy.R;
import com.example.philosophy.adapter.GenresAdapter;
import com.example.philosophy.admin.InsertGenreActivity;
import com.example.philosophy.admin.UpdateGenreActivity;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.GenresEntity;
import com.example.philosophy.details.AdminActivity;

import java.util.List;

public class AdminGenresActivity extends AppCompatActivity implements View.OnClickListener {
    private Button insertGenre;
    private Button back;
    private RecyclerView recyclerGenres;

    private ImageView genrePic;
    private TextView genreName;
    private TextView genreIntro;

    private int gNum;
    private String gPic;
    private String gName;
    private String gIntro;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_genres);
        init();
    }


    private void init() {
        insertGenre = findViewById(R.id.btn_insertGenre);
        back = findViewById(R.id.btn_back);
        recyclerGenres = findViewById(R.id.recycler_genres);

        List<GenresEntity> genres = AppDatabase.getInstance(this).genresDao().query();
        RecyclerView.Adapter adapter = new GenresAdapter(genres);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        builder = new AlertDialog.Builder(AdminGenresActivity.this);

        insertGenre.setOnClickListener(this);
        back.setOnClickListener(this);
        recyclerGenres.setHasFixedSize(true);
        recyclerGenres.setLayoutManager(layoutManager);
        recyclerGenres.setAdapter(adapter);

        ((GenresAdapter) adapter).setOnItemClickListener(new GenresAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                genreName = view.findViewById(R.id.text_genreName);
                gName = genreName.getText().toString();

                GenresEntity genre = AppDatabase.getInstance(AdminGenresActivity.this).genresDao().queryByName(gName);
                gNum = genre.getgNum();
                gPic = genre.getgPic();

                genreIntro = view.findViewById(R.id.text_genreIntro);
                gIntro = genreIntro.getText().toString();

                builder.setMessage("请选择相应操作：");
                setNegativeButton(builder);
                setPositiveButton(builder);
                builder.create();
                builder.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insertGenre:
                Intent intent = new Intent(AdminGenresActivity.this, InsertGenreActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_back:
                Intent back = new Intent(AdminGenresActivity.this, AdminActivity.class);
                startActivity(back);
                break;
            default:
                break;
        }
    }

    private AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder) {
        return builder.setNegativeButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(AdminGenresActivity.this, UpdateGenreActivity.class);
                intent.putExtra("gNum", gNum);

                startActivity(intent);
            }
        });
    }

    private AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder) {
        return builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (deleteGenre(gNum) == 1) {
                    Toast.makeText(AdminGenresActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminGenresActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public int deleteGenre(int gNum) {
        return AppDatabase.getInstance(this).genresDao().deleteByNum(gNum);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
