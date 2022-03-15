package com.example.philosophy.admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.philosophy.R;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.GenresEntity;
import com.example.philosophy.lists.AdminGenresActivity;
import com.example.philosophy.utils.ImageUtil;

import static java.sql.Types.NULL;

public class InsertGenreActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CHOOSE_PICTURE = 0;

    private ImageView genrePic;
    private EditText genreName;
    private EditText genreIntro;
    private Button resetGenre;
    private Button insertGenre;

    private int gNum;
    private String gPic;
    private String gName;
    private String gIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_genre);

        init();
    }


    private void init() {
        genrePic = findViewById(R.id.image_genrePic);
        genreName = findViewById(R.id.edit_genreName);
        genreIntro = findViewById(R.id.edit_genreIntro);
        resetGenre = findViewById(R.id.btn_resetGenre);
        insertGenre = findViewById(R.id.btn_insertGenre);

        genrePic.setOnClickListener(this);
        resetGenre.setOnClickListener(this);
        insertGenre.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_genrePic:
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PICTURE);
                break;
            case R.id.btn_resetGenre:
                resetGenre();
                break;
            case R.id.btn_insertGenre:
                gName = genreName.getText().toString();
                gIntro = genreIntro.getText().toString();

                insertGenre(NULL, gPic, gName, gIntro);
                Toast.makeText(InsertGenreActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                Intent adminBooksIntent = new Intent(InsertGenreActivity.this, AdminGenresActivity.class);
                startActivity(adminBooksIntent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_PICTURE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = ImageUtil.handleImageOnKitKat(this, data);
                genrePic.setImageBitmap(bitmap);
                gPic = ImageUtil.imagePath;
            }
        }
    }

    private void resetGenre() {
        genrePic.setImageBitmap(null);
        genreName.setText(null);
        genreIntro.setText(null);
    }

    public long insertGenre(int gNum, String gPic, String gName, String gIntro) {
        GenresEntity genre = new GenresEntity();
        genre.setgNum(gNum);
        genre.setgPic(gPic);
        genre.setgName(gName);
        genre.setgIntro(gIntro);
        return AppDatabase.getInstance(this).genresDao().insert(genre);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
