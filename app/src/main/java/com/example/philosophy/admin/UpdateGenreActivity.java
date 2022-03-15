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

public class UpdateGenreActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CHOOSE_PICTURE = 0;

    private ImageView genrePic;
    private EditText genreName;
    private EditText genreIntro;
    private Button resetGenre;
    private Button updateGenre;

    private int gNum;
    private String gPic;
    private String gName;
    private String gIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_genre);

        init();
        resetGenre();
    }


    private void init() {
        genrePic = findViewById(R.id.image_genrePic);
        genreName = findViewById(R.id.edit_genreName);
        genreIntro = findViewById(R.id.edit_genreIntro);
        resetGenre = findViewById(R.id.btn_resetGenre);
        updateGenre = findViewById(R.id.btn_updateGenre);

        genrePic.setOnClickListener(this);
        resetGenre.setOnClickListener(this);
        updateGenre.setOnClickListener(this);
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
            case R.id.btn_updateGenre:
                gName = genreName.getText().toString();
                gIntro = genreIntro.getText().toString();

                if (updateGenre(gNum, gPic, gName, gIntro) == 1) {
                    Toast.makeText(UpdateGenreActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    Intent adminBooksIntent = new Intent(UpdateGenreActivity.this, AdminGenresActivity.class);
                    startActivity(adminBooksIntent);
                } else {
                    Toast.makeText(UpdateGenreActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                }
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
        Intent intent = getIntent();
        gNum = intent.getIntExtra("gNum", NULL);

        GenresEntity genre = AppDatabase.getInstance(this).genresDao().queryByNum(gNum);
        gPic = genre.getgPic();
        gName = genre.getgName();
        gIntro = genre.getgIntro();

        genrePic.setImageBitmap(ImageUtil.getImage(gPic));
        genreName.setText(gName);
        genreIntro.setText(gIntro);
    }

    public long updateGenre(int gNum, String gPic, String gName, String gIntro) {
        GenresEntity genre = new GenresEntity();
        genre.setgNum(gNum);
        genre.setgPic(gPic);
        genre.setgName(gName);
        genre.setgIntro(gIntro);
        return AppDatabase.getInstance(this).genresDao().update(genre);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
