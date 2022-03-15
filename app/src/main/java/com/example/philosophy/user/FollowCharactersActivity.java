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
import com.example.philosophy.adapter.CharactersAdapter;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.CharactersEntity;
import com.example.philosophy.details.CharacterActivity;

import java.util.List;

public class FollowCharactersActivity extends AppCompatActivity {
    private RecyclerView recyclerCharacters;
    private TextView characterName;

    private String uTel;
    private int uNum;

    private int cNum;
    private String cName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_characters);
        init();
    }


    private void init() {
        SharedPreferences sp = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        uTel = sp.getString("userTel", "");
        uNum = AppDatabase.getInstance(this).usersDao().queryNumByTel(uTel);

        recyclerCharacters = findViewById(R.id.recycler_characters);

        List<CharactersEntity> characters = AppDatabase.getInstance(this).usersCharactersDao().queryFollowedCharacters(uNum);
        RecyclerView.Adapter adapter = new CharactersAdapter(characters);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerCharacters.setHasFixedSize(true);
        recyclerCharacters.setLayoutManager(layoutManager);
        recyclerCharacters.setAdapter(adapter);

        ((CharactersAdapter) adapter).setOnItemClickListener(new CharactersAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                characterName = view.findViewById(R.id.text_characterName);
                cName = characterName.getText().toString();

                CharactersEntity character = AppDatabase.getInstance(FollowCharactersActivity.this).charactersDao().queryByName(cName);
                cNum = character.getcNum();

                Intent intent = new Intent(FollowCharactersActivity.this, CharacterActivity.class);
                intent.putExtra("cNum", cNum);

                startActivity(intent);
            }
        });
    }
}
