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
import com.example.philosophy.db.entity.CharactersEntity;
import com.example.philosophy.lists.AdminCharactersActivity;
import com.example.philosophy.utils.ImageUtil;

import static java.sql.Types.NULL;

public class InsertCharacterActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CHOOSE_PICTURE = 0;

    private ImageView characterPic;
    private EditText characterName;
    private EditText characterGenre;
    private EditText characterIntro;
    private EditText characterLife;
    private EditText characterThoughts;
    private EditText characterWritings;
    private EditText characterInfluence;
    private Button resetCharacter;
    private Button insertCharacter;

    private int cNum;
    private String cPic;
    private String cName;
    private String cGenre;
    private int gNum;
    private String cIntro;
    private String cLife;
    private String cThoughts;
    private String cWritings;
    private String cInfluence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_character);

        init();
    }


    private void init() {
        characterPic = findViewById(R.id.image_characterPic);
        characterName = findViewById(R.id.edit_characterName);
        characterGenre = findViewById(R.id.edit_characterGenre);
        characterIntro = findViewById(R.id.edit_characterIntro);
        characterLife = findViewById(R.id.edit_characterLife);
        characterThoughts = findViewById(R.id.edit_characterThoughts);
        characterWritings = findViewById(R.id.edit_characterWritings);
        characterInfluence = findViewById(R.id.edit_characterInfluence);
        resetCharacter = findViewById(R.id.btn_resetCharacter);
        insertCharacter = findViewById(R.id.btn_insertCharacter);

        characterPic.setOnClickListener(this);
        resetCharacter.setOnClickListener(this);
        insertCharacter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_characterPic:
                Intent intent = new Intent("android.intent.action.GET_CONTENT");
                intent.setType("image/*");
                startActivityForResult(intent, CHOOSE_PICTURE);
                break;
            case R.id.btn_resetCharacter:
                resetCharacter();
                break;
            case R.id.btn_insertCharacter:
                cName = characterName.getText().toString();
                cGenre = characterGenre.getText().toString();
                gNum = AppDatabase.getInstance(this).genresDao().queryNumByName(cGenre);
                cIntro = characterIntro.getText().toString();
                cLife = characterLife.getText().toString();
                cThoughts = characterThoughts.getText().toString();
                cWritings = characterWritings.getText().toString();
                cInfluence = characterInfluence.getText().toString();

                insertCharacter(NULL, cPic, cName, gNum, cIntro, cLife, cThoughts, cWritings, cInfluence);
                Toast.makeText(InsertCharacterActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                Intent adminBooksIntent = new Intent(InsertCharacterActivity.this, AdminCharactersActivity.class);
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
                characterPic.setImageBitmap(bitmap);
                cPic = ImageUtil.imagePath;
            }
        }
    }

    private void resetCharacter() {
        characterPic.setImageBitmap(null);
        characterName.setText(null);
        characterGenre.setText(null);
        characterIntro.setText(null);
        characterLife.setText(null);
        characterThoughts.setText(null);
        characterWritings.setText(null);
        characterInfluence.setText(null);
    }

    public long insertCharacter(int cNum, String cPic, String cName, int gNum, String cIntro, String cLife, String cThoughts, String cWritings, String cInfluence) {
        CharactersEntity character = new CharactersEntity();
        character.setcNum(cNum);
        character.setcPic(cPic);
        character.setcName(cName);
        character.setgNum(gNum);
        character.setcIntro(cIntro);
        character.setcLife(cLife);
        character.setcThoughts(cThoughts);
        character.setcWritings(cWritings);
        character.setcInfluence(cInfluence);

        return AppDatabase.getInstance(this).charactersDao().insert(character);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
