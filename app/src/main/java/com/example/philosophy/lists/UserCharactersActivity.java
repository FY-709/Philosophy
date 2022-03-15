package com.example.philosophy.lists;

import android.content.Intent;
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

public class UserCharactersActivity extends AppCompatActivity {
    private RecyclerView recyclerCharacters;

    private TextView characterName;

    private int cNum;
    private String cName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_characters);

        init();
    }


    private void init() {
        recyclerCharacters = findViewById(R.id.recycler_characters);

        List<CharactersEntity> characters = AppDatabase.getInstance(this).charactersDao().query();
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

                CharactersEntity character = AppDatabase.getInstance(UserCharactersActivity.this).charactersDao().queryByName(cName);
                cNum = character.getcNum();

                Intent intent = new Intent(UserCharactersActivity.this, CharacterActivity.class);
                intent.putExtra("cNum", cNum);

                startActivity(intent);
            }
        });
    }
}
