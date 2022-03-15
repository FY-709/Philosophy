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

public class UpdateCharacterActivity extends AppCompatActivity implements View.OnClickListener {
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
    private Button updateCharacter;

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
        setContentView(R.layout.activity_update_character);

        init();
        resetCharacter();
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
        updateCharacter = findViewById(R.id.btn_updateCharacter);

        characterPic.setOnClickListener(this);
        resetCharacter.setOnClickListener(this);
        updateCharacter.setOnClickListener(this);
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
            case R.id.btn_updateCharacter:
                cName = characterName.getText().toString();
                cGenre = characterGenre.getText().toString();
                gNum = AppDatabase.getInstance(this).genresDao().queryNumByName(cGenre);
                cIntro = characterIntro.getText().toString();
                cLife = characterLife.getText().toString();
                cThoughts = characterThoughts.getText().toString();
                cWritings = characterWritings.getText().toString();
                cInfluence = characterInfluence.getText().toString();

                if (updateCharacter(cNum, cPic, cName, gNum, cIntro, cLife, cThoughts, cWritings, cInfluence) == 1) {
                    Toast.makeText(UpdateCharacterActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    Intent adminBooksIntent = new Intent(UpdateCharacterActivity.this, AdminCharactersActivity.class);
                    startActivity(adminBooksIntent);
                } else {
                    Toast.makeText(UpdateCharacterActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
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
                characterPic.setImageBitmap(bitmap);
                cPic = ImageUtil.imagePath;
            }
        }
    }

    private void resetCharacter() {
        Intent intent = getIntent();
        cNum = intent.getIntExtra("cNum", NULL);

        CharactersEntity character = AppDatabase.getInstance(this).charactersDao().queryByNum(cNum);
        cPic = character.getcPic();
        cName = character.getcName();
        gNum = character.getgNum();
        cGenre = AppDatabase.getInstance(this).genresDao().queryNameByNum(gNum);
        cIntro = character.getcIntro();
        cLife = character.getcLife();
        cThoughts = character.getcThoughts();
        cWritings = character.getcWritings();
        cInfluence = character.getcInfluence();

        characterPic.setImageBitmap(ImageUtil.getImage(cPic));
        characterName.setText(cName);
        characterGenre.setText(cGenre);
        characterIntro.setText(cIntro);
        characterLife.setText(cLife);
        characterThoughts.setText(cThoughts);
        characterWritings.setText(cWritings);
        characterInfluence.setText(cInfluence);
    }

    public long updateCharacter(int cNum, String cPic, String cName, int gNum, String cIntro, String cLife, String cThoughts, String cWritings, String cInfluence) {
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

        return AppDatabase.getInstance(this).charactersDao().update(character);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
