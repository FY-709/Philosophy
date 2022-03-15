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
import com.example.philosophy.db.entity.UsersCharactersEntity;
import com.example.philosophy.utils.ImageUtil;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.sql.Types.NULL;

public class CharacterActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView characterPic;
    private TextView characterName;
    private ImageView genrePic;
    private TextView characterGenre;
    private TextView characterIntro;
    private TextView characterLife;
    private TextView characterThoughts;
    private TextView characterWritings;
    private TextView characterInfluence;
    private Button followedCharacter;
    private EditText discussionContent;
    private Button discussion;
    private RecyclerView recyclerDiscussions;

    private int uNum;
    private String uTel;
    private int cNum;
    private String cPic;
    private String cName;
    private int gNum;
    private String gPic;
    private String cGenre;
    private String cIntro;
    private String cLife;
    private String cThoughts;
    private String cWritings;
    private String cInfluence;
    private Date dTime;
    private String dLocation;
    private String dContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        init();
        resetCharacter();
    }

    private void init() {
        Intent intent = getIntent();
        cNum = intent.getIntExtra("cNum", NULL);
        dLocation = "人物" + cNum;

        characterPic = findViewById(R.id.image_characterPic);
        characterName = findViewById(R.id.text_characterName);
        genrePic = findViewById(R.id.image_genrePic);
        characterGenre = findViewById(R.id.text_characterGenre);
        characterIntro = findViewById(R.id.text_characterIntro);
        characterLife = findViewById(R.id.text_characterLife);
        characterThoughts = findViewById(R.id.text_characterThoughts);
        characterWritings = findViewById(R.id.text_characterWritings);
        characterInfluence = findViewById(R.id.text_characterInfluence);
        followedCharacter = findViewById(R.id.btn_followedCharacter);
        discussionContent = findViewById(R.id.edit_discussionContent);
        discussion = findViewById(R.id.btn_discussion);
        recyclerDiscussions = findViewById(R.id.recycler_discussions);

        List<DiscussionsEntity> discussions = AppDatabase.getInstance(this).discussionsDao().queryByLocation(dLocation);
        RecyclerView.Adapter adapter = new DiscussionsAdapter(discussions);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerDiscussions.setHasFixedSize(true);
        recyclerDiscussions.setLayoutManager(layoutManager);
        recyclerDiscussions.setAdapter(adapter);
        followedCharacter.setOnClickListener(this);
        discussion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_followedCharacter:
                if (isUserLogin()) {
                    if (isFollowedCharacter()) {
                        if (AppDatabase.getInstance(this).usersCharactersDao().deleteUsersCharacters(uNum, cNum) == 1) {
                            Toast.makeText(CharacterActivity.this, "已取消关注", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CharacterActivity.this, "取消关注失败", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        UsersCharactersEntity usersCharacters = new UsersCharactersEntity();
                        usersCharacters.setuNum(uNum);
                        usersCharacters.setcNum(cNum);
                        AppDatabase.getInstance(this).usersCharactersDao().insert(usersCharacters);
                        Toast.makeText(CharacterActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CharacterActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_discussion:
                if (isUserLogin()) {
                    dTime = new Date();
                    dContent = discussionContent.getText().toString();
                    insertDiscussion(NULL, uNum, dLocation, dTime, dContent);
                    Toast.makeText(CharacterActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                    discussionContent.setText(null);
                } else {
                    Toast.makeText(CharacterActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void resetCharacter() {
        CharactersEntity character = AppDatabase.getInstance(this).charactersDao().queryByNum(cNum);
        cPic = character.getcPic();
        cName = character.getcName();
        gNum = character.getgNum();
        GenresEntity genre = AppDatabase.getInstance(this).genresDao().queryByNum(gNum);
        gPic = genre.getgPic();
        cGenre = genre.getgName();
        cIntro = character.getcIntro();
        cLife = character.getcLife();
        cThoughts = character.getcThoughts();
        cWritings = character.getcWritings();
        cInfluence = character.getcInfluence();

        characterPic.setImageBitmap(ImageUtil.getImage(cPic));
        characterName.setText(cName);
        genrePic.setImageBitmap(ImageUtil.getImage(gPic));
        characterGenre.setText(cGenre);
        characterIntro.setText(cIntro);
        characterLife.setText(cLife);
        characterThoughts.setText(cThoughts);
        characterWritings.setText(cWritings);
        characterInfluence.setText(cInfluence);

        if (isUserLogin()) {
            if (isFollowedCharacter()) {
                followedCharacter.setText("取消关注");
            }
        }
    }

    private boolean isUserLogin() {
        SharedPreferences sp = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        uTel = sp.getString("userTel", "");

        return !Objects.equals(uTel, null) && !Objects.equals(uTel, "");
    }

    private boolean isFollowedCharacter() {
        uNum = AppDatabase.getInstance(this).usersDao().queryNumByTel(uTel);

        UsersCharactersEntity usersCharacters = AppDatabase.getInstance(this).usersCharactersDao().queryUsersCharacters(uNum, cNum);

        return usersCharacters != null;
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
