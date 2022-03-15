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
import com.example.philosophy.adapter.CharactersAdapter;
import com.example.philosophy.admin.InsertCharacterActivity;
import com.example.philosophy.admin.UpdateCharacterActivity;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.CharactersEntity;
import com.example.philosophy.details.AdminActivity;

import java.util.List;

public class AdminCharactersActivity extends AppCompatActivity implements View.OnClickListener {
    private Button insertCharacter;
    private Button back;
    private RecyclerView recyclerCharacters;

    private ImageView characterPic;
    private TextView characterName;
    private TextView characterGenre;
    private TextView characterThoughts;

    private int cNum;
    private String cPic;
    private String cName;
    private String cGenre;
    private String cThoughts;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_characters);
        init();
    }

    private void init() {
        insertCharacter = findViewById(R.id.btn_insertCharacter);
        back = findViewById(R.id.btn_back);
        recyclerCharacters = findViewById(R.id.recycler_characters);

        List<CharactersEntity> characters = AppDatabase.getInstance(this).charactersDao().query();
        RecyclerView.Adapter adapter = new CharactersAdapter(characters);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        builder = new AlertDialog.Builder(AdminCharactersActivity.this);

        insertCharacter.setOnClickListener(this);
        back.setOnClickListener(this);
        recyclerCharacters.setHasFixedSize(true);
        recyclerCharacters.setLayoutManager(layoutManager);
        recyclerCharacters.setAdapter(adapter);

        ((CharactersAdapter) adapter).setOnItemClickListener(new CharactersAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(AdminBooksActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
                characterName = view.findViewById(R.id.text_characterName);
                cName = characterName.getText().toString();

                CharactersEntity character = AppDatabase.getInstance(AdminCharactersActivity.this).charactersDao().queryByName(cName);
                cNum = character.getcNum();
                cPic = character.getcPic();

                characterGenre = view.findViewById(R.id.text_characterGenre);
                cGenre = characterGenre.getText().toString();

                characterThoughts = view.findViewById(R.id.text_characterThoughts);
                cThoughts = characterThoughts.getText().toString();

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
            case R.id.btn_insertCharacter:
                Intent intent = new Intent(AdminCharactersActivity.this, InsertCharacterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_back:
                Intent back = new Intent(AdminCharactersActivity.this, AdminActivity.class);
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
                Intent intent = new Intent(AdminCharactersActivity.this, UpdateCharacterActivity.class);
                intent.putExtra("cNum", cNum);

                startActivity(intent);
            }
        });
    }

    private AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder) {
        return builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (deleteCharacter(cNum) == 1) {
                    Toast.makeText(AdminCharactersActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminCharactersActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public int deleteCharacter(int cNum) {
        return AppDatabase.getInstance(this).charactersDao().deleteByNum(cNum);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
