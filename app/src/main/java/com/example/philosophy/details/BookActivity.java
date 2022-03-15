package com.example.philosophy.details;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import com.example.philosophy.db.entity.BooksEntity;
import com.example.philosophy.db.entity.BookshelfEntity;
import com.example.philosophy.db.entity.CharactersEntity;
import com.example.philosophy.db.entity.DiscussionsEntity;
import com.example.philosophy.reader.ReaderActivity;
import com.example.philosophy.reader.main.TxtConfig;
import com.example.philosophy.utils.ImageUtil;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.sql.Types.NULL;

public class BookActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView bookPic;
    private TextView bookName;
    private ImageView characterPic;
    private TextView bookCharacter;
    private TextView bookIntro;
    private Button bookshelf;
    private Button reader;
    private EditText discussionContent;
    private Button discussion;
    private RecyclerView recyclerDiscussions;

    private int uNum;
    private String uTel;
    private String identifier;
    private int bNum;
    private String bPic;
    private String bName;
    private int cNum;
    private String cPic;
    private String bCharacter;
    private String bIntro;
    private String bContent;
    private Date dTime;
    private String dLocation;
    private String dContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        init();
        resetBook();
    }

    private void init() {
        Intent intent = getIntent();
        bNum = intent.getIntExtra("bNum", NULL);
        dLocation = "书籍" + bNum;

        if (isUserLogin()) {
            identifier = Integer.toString(uNum);
        } else {
            identifier = Build.SERIAL;
        }

        bookPic = findViewById(R.id.image_bookPic);
        bookName = findViewById(R.id.text_bookName);
        characterPic = findViewById(R.id.image_characterPic);
        bookCharacter = findViewById(R.id.text_bookCharacter);
        bookIntro = findViewById(R.id.text_bookIntro);
        bookshelf = findViewById(R.id.btn_bookshelf);
        reader = findViewById(R.id.btn_reader);
        discussionContent = findViewById(R.id.edit_discussionContent);
        discussion = findViewById(R.id.btn_discussion);
        recyclerDiscussions = findViewById(R.id.recycler_discussions);

        List<DiscussionsEntity> discussions = AppDatabase.getInstance(this).discussionsDao().queryByLocation(dLocation);
        RecyclerView.Adapter adapter = new DiscussionsAdapter(discussions);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerDiscussions.setHasFixedSize(true);
        recyclerDiscussions.setLayoutManager(layoutManager);
        recyclerDiscussions.setAdapter(adapter);
        bookshelf.setOnClickListener(this);
        reader.setOnClickListener(this);
        discussion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bookshelf:
                if (isIdentityBookshelf()) {
                    if (AppDatabase.getInstance(this).bookshelfDao().deleteBookshelf(identifier, bNum) == 1) {
                        Toast.makeText(BookActivity.this, "已删除书籍", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BookActivity.this, "删除书籍失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    BookshelfEntity bookshelf = new BookshelfEntity();
                    bookshelf.setsIdentifier(identifier);
                    bookshelf.setbNum(bNum);
                    AppDatabase.getInstance(this).bookshelfDao().insert(bookshelf);
                    Toast.makeText(BookActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_reader:
                TxtConfig.saveIsOnVerticalPageMode(BookActivity.this, false);
                ReaderActivity.loadTxtFile(BookActivity.this, bContent);
                break;
            case R.id.btn_discussion:
                if (isUserLogin()) {
                    dTime = new Date();
                    dContent = discussionContent.getText().toString();
                    insertDiscussion(NULL, uNum, dLocation, dTime, dContent);
                    Toast.makeText(BookActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                    discussionContent.setText(null);
                } else {
                    Toast.makeText(BookActivity.this, "您尚未登录", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void resetBook() {
        BooksEntity book = AppDatabase.getInstance(this).booksDao().queryByNum(bNum);
        bPic = book.getbPic();
        bName = book.getbName();
        cNum = book.getcNum();
        CharactersEntity character = AppDatabase.getInstance(this).charactersDao().queryByNum(cNum);
        cPic = character.getcPic();
        bCharacter = character.getcName();
        bIntro = book.getbIntro();
        bContent = book.getbContent();

        bookPic.setImageBitmap(ImageUtil.getImage(bPic));
        bookName.setText(bName);
        characterPic.setImageBitmap(ImageUtil.getImage(cPic));
        bookCharacter.setText(bCharacter);
        bookIntro.setText(bIntro);

        if (isIdentityBookshelf()) {
            bookshelf.setText("删除书籍");
        }
    }

    private boolean isUserLogin() {
        SharedPreferences sp = getSharedPreferences("UserInfo", Activity.MODE_PRIVATE);
        uTel = sp.getString("userTel", "");
        uNum = AppDatabase.getInstance(this).usersDao().queryNumByTel(uTel);

        return !Objects.equals(uTel, null) && !Objects.equals(uTel, "");
    }

    private boolean isIdentityBookshelf() {
        BookshelfEntity bookshelf = AppDatabase.getInstance(this).bookshelfDao().queryBookshelf(identifier, bNum);

        return bookshelf != null;
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
