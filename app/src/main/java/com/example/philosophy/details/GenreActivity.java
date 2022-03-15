package com.example.philosophy.details;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.philosophy.R;
import com.example.philosophy.adapter.DiscussionsAdapter;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.CharactersEntity;
import com.example.philosophy.db.entity.DiscussionsEntity;
import com.example.philosophy.db.entity.GenresEntity;
import com.example.philosophy.db.entity.UsersGenresEntity;
import com.example.philosophy.utils.ImageUtil;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.sql.Types.NULL;

public class GenreActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView genrePic;
    private TextView genreName;
    private TextView genreIntro;
    private ImageView characterPic;
    private TextView genreCharacter;
    private Button followedGenre;
    private EditText discussionContent;
    private Button discussion;
    private RecyclerView recyclerDiscussions;

    private int uNum;
    private String uTel;
    private int gNum;
    private String gPic;
    private String gName;
    private String gIntro;
    private String cPic;
    private String gCharacter;
    private Date dTime;
    private String dLocation;
    private String dContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);
        init();
        resetGenre();
    }

    private void init() {
        Intent intent = getIntent();
        gNum = intent.getIntExtra("gNum", NULL);
        dLocation = "流派" + gNum;

        genrePic = findViewById(R.id.image_genrePic);
        genreName = findViewById(R.id.text_genreName);
        genreIntro = findViewById(R.id.text_genreIntro);
        characterPic = findViewById(R.id.image_characterPic);
        genreCharacter = findViewById(R.id.text_genreCharacter);
        followedGenre = findViewById(R.id.btn_followedGenre);
        discussionContent = findViewById(R.id.edit_discussionContent);
        discussion = findViewById(R.id.btn_discussion);
        recyclerDiscussions = findViewById(R.id.recycler_discussions);

        List<DiscussionsEntity> discussions = AppDatabase.getInstance(this).discussionsDao().queryByLocation(dLocation);
        RecyclerView.Adapter adapter = new DiscussionsAdapter(discussions);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerDiscussions.setHasFixedSize(true);
        recyclerDiscussions.setLayoutManager(layoutManager);
        recyclerDiscussions.setAdapter(adapter);
        followedGenre.setOnClickListener(this);
        discussion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_followedGenre:
                if (isUserLogin()) {
                    if (isFollowedGenre()) {
                        if (AppDatabase.getInstance(this).usersGenresDao().deleteUsersGenres(uNum, gNum) == 1) {
                            Toast.makeText(GenreActivity.this, "已取消关注", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(GenreActivity.this, "取消关注失败", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        UsersGenresEntity usersGenres = new UsersGenresEntity();
                        usersGenres.setuNum(uNum);
                        usersGenres.setgNum(gNum);
                        AppDatabase.getInstance(this).usersGenresDao().insert(usersGenres);
                        Toast.makeText(GenreActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GenreActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_discussion:
                if (isUserLogin()) {
                    dTime = new Date();
                    dContent = discussionContent.getText().toString();
                    insertDiscussion(NULL, uNum, dLocation, dTime, dContent);
                    Toast.makeText(GenreActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                    discussionContent.setText(null);
                } else {
                    Toast.makeText(GenreActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    private void resetGenre() {

        GenresEntity genre = AppDatabase.getInstance(this).genresDao().queryByNum(gNum);
        gPic = genre.getgPic();
        gName = genre.getgName();
        gIntro = genre.getgIntro();
        CharactersEntity character = AppDatabase.getInstance(this).charactersDao().queryCharacterByGenre(gNum);
        if (character != null) {
            cPic = character.getcPic();
            gCharacter = character.getcName();
        } else {
            cPic = null;
            gCharacter = "暂无";
        }

        genrePic.setImageBitmap(ImageUtil.getImage(gPic));
        genreName.setText(gName);
        genreIntro.setText(gIntro);
        characterPic.setImageBitmap(ImageUtil.getImage(cPic));
        genreCharacter.setText(gCharacter);

        if (isUserLogin()) {
            if (isFollowedGenre()) {
                followedGenre.setText("取消关注");
            }
        }
    }

    private boolean isUserLogin() {
        SharedPreferences sp = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        uTel = sp.getString("userTel", "");

        return !Objects.equals(uTel, null) && !Objects.equals(uTel, "");
    }

    private boolean isFollowedGenre() {
        uNum = AppDatabase.getInstance(this).usersDao().queryNumByTel(uTel);
        UsersGenresEntity usersGenres = AppDatabase.getInstance(this).usersGenresDao().queryUsersGenres(uNum, gNum);

        return usersGenres != null;
    }

    public long insertDiscussion(int dNum, int uNum, String dLocation, Date dTime, String dContent) {
        DiscussionsEntity discussion = new DiscussionsEntity();
        discussion.setdNum(dNum);
        discussion.setuNum(uNum);
        discussion.setdLocation(dLocation);
        discussion.setdTime(dTime);
        discussion.setdContent(dContent);
        return AppDatabase.getInstance(this).discussionsDao().insert(discussion);
    }
}
