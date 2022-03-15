package com.example.philosophy.admin;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.philosophy.R;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.BooksEntity;
import com.example.philosophy.lists.AdminBooksActivity;
import com.example.philosophy.utils.ImageUtil;

import static java.sql.Types.NULL;

public class UpdateBookActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CHOOSE_PICTURE = 0x00;
    private static final int CHOOSE_BOOK = 0x01;

    private ImageView bookPic;
    private EditText bookName;
    private EditText bookCharacter;
    private EditText bookIntro;
    private TextView bookContentPath;
    private Button bookContent;
    private Button resetBook;
    private Button updateBook;

    private int bNum;
    private String bPic;
    private String bName;
    private String bCharacter;
    private int cNum;
    private String bIntro;
    private String bContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        init();
        resetBook();
    }

    private void init() {
        bookPic = findViewById(R.id.image_bookPic);
        bookName = findViewById(R.id.edit_bookName);
        bookCharacter = findViewById(R.id.edit_bookCharacter);
        bookIntro = findViewById(R.id.edit_bookIntro);
        bookContentPath = findViewById(R.id.text_bookContent);
        bookContent = findViewById(R.id.btn_bookContent);
        resetBook = findViewById(R.id.btn_resetBook);
        updateBook = findViewById(R.id.btn_updateBook);

        bookPic.setOnClickListener(this);
        bookContent.setOnClickListener(this);
        resetBook.setOnClickListener(this);
        updateBook.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_bookPic:
                Intent intentPic = new Intent(Intent.ACTION_GET_CONTENT);
                intentPic.setType("image/*");
                intentPic.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intentPic, CHOOSE_PICTURE);
                break;
            case R.id.btn_bookContent:
                Intent intentContent = new Intent(Intent.ACTION_GET_CONTENT);
                intentContent.setType("text/plain");
                intentContent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intentContent, CHOOSE_BOOK);
                break;
            case R.id.btn_resetBook:
                resetBook();
                break;
            case R.id.btn_updateBook:
                bName = bookName.getText().toString();
                bCharacter = bookCharacter.getText().toString();
                cNum = AppDatabase.getInstance(this).charactersDao().queryNumByName(bCharacter);
                bIntro = bookIntro.getText().toString();

                if (updateBook(bNum, bPic, bName, cNum, bIntro, bContent) == 1) {
                    Toast.makeText(UpdateBookActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    Intent adminBooksIntent = new Intent(UpdateBookActivity.this, AdminBooksActivity.class);
                    startActivity(adminBooksIntent);
                } else {
                    Toast.makeText(UpdateBookActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
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
                bookPic.setImageBitmap(bitmap);
                bPic = ImageUtil.imagePath;
            }
        }
        if (requestCode == CHOOSE_BOOK) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String[] pros = {MediaStore.Files.FileColumns.DATA};
                Cursor cursor = managedQuery(uri, pros, null, null, null);
                int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                bContent = cursor.getString(index);
                bookContentPath.setText(bContent);
            }
        }
    }

    private void resetBook() {
        Intent intent = getIntent();
        bNum = intent.getIntExtra("bNum", NULL);

        BooksEntity book = AppDatabase.getInstance(this).booksDao().queryByNum(bNum);
        bPic = book.getbPic();
        bName = book.getbName();
        cNum = book.getcNum();
        bCharacter = AppDatabase.getInstance(this).charactersDao().queryNameByNum(cNum);
        bIntro = book.getbIntro();
        bContent = book.getbContent();

        bookPic.setImageBitmap(ImageUtil.getImage(bPic));
        bookName.setText(bName);
        bookCharacter.setText(bCharacter);
        bookIntro.setText(bIntro);
        bookContentPath.setText(bContent);
    }

    public long updateBook(int bNum, String bPic, String bName, int cNum, String bIntro, String bContent) {
        BooksEntity book = new BooksEntity();
        book.setbNum(bNum);
        book.setbPic(bPic);
        book.setbName(bName);
        book.setcNum(cNum);
        book.setbIntro(bIntro);
        book.setbContent(bContent);
        return AppDatabase.getInstance(this).booksDao().update(book);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
