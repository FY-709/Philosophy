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
import com.example.philosophy.adapter.BooksAdapter;
import com.example.philosophy.admin.InsertBookActivity;
import com.example.philosophy.admin.UpdateBookActivity;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.BooksEntity;
import com.example.philosophy.details.AdminActivity;

import java.util.List;

public class AdminBooksActivity extends AppCompatActivity implements View.OnClickListener {
    private Button insertBook;
    private Button back;
    private RecyclerView recyclerBooks;

    private ImageView bookPic;
    private TextView bookName;
    private TextView bookCharacter;
    private TextView bookIntro;

    private int bNum;
    private String bPic;
    private String bName;
    private String bCharacter;
    private String bIntro;
    private String bContent;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_books);

        init();
    }

    private void init() {
        insertBook = findViewById(R.id.btn_insertBook);
        back = findViewById(R.id.btn_back);
        recyclerBooks = findViewById(R.id.recycler_books);

        List<BooksEntity> books = AppDatabase.getInstance(this).booksDao().query();
        RecyclerView.Adapter adapter = new BooksAdapter(books);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        builder = new AlertDialog.Builder(AdminBooksActivity.this);

        insertBook.setOnClickListener(this);
        back.setOnClickListener(this);
        recyclerBooks.setHasFixedSize(true);
        recyclerBooks.setLayoutManager(layoutManager);
        recyclerBooks.setAdapter(adapter);

        ((BooksAdapter) adapter).setOnItemClickListener(new BooksAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                bookName = view.findViewById(R.id.text_bookName);
                bName = bookName.getText().toString();

                BooksEntity book = AppDatabase.getInstance(AdminBooksActivity.this).booksDao().queryByName(bName);
                bNum = book.getbNum();
                bPic = book.getbPic();

                bookCharacter = view.findViewById(R.id.text_bookCharacter);
                bCharacter = bookCharacter.getText().toString();

                bookIntro = view.findViewById(R.id.text_bookIntro);
                bIntro = bookIntro.getText().toString();

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
            case R.id.btn_insertBook:
                Intent intent = new Intent(AdminBooksActivity.this, InsertBookActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_back:
                Intent back = new Intent(AdminBooksActivity.this, AdminActivity.class);
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
                Intent intent = new Intent(AdminBooksActivity.this, UpdateBookActivity.class);
                intent.putExtra("bNum", bNum);

                startActivity(intent);
            }
        });
    }

    private AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder) {
        return builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (deleteBook(bNum) == 1) {
                    Toast.makeText(AdminBooksActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminBooksActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public int deleteBook(int bNum) {
        return AppDatabase.getInstance(this).booksDao().deleteByNum(bNum);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
