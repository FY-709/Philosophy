package com.example.philosophy.lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.philosophy.R;
import com.example.philosophy.adapter.BooksAdapter;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.BooksEntity;
import com.example.philosophy.details.BookActivity;

import java.util.List;

public class UserBooksActivity extends AppCompatActivity {
    private RecyclerView recyclerBooks;

    private TextView bookName;

    private int bNum;
    private String bName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_books);

        init();
    }

    private void init() {
        recyclerBooks = findViewById(R.id.recycler_books);

        List<BooksEntity> books = AppDatabase.getInstance(this).booksDao().query();
        RecyclerView.Adapter adapter = new BooksAdapter(books);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerBooks.setHasFixedSize(true);
        recyclerBooks.setLayoutManager(layoutManager);
        recyclerBooks.setAdapter(adapter);

        ((BooksAdapter) adapter).setOnItemClickListener(new BooksAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                bookName = view.findViewById(R.id.text_bookName);
                bName = bookName.getText().toString();

                bNum = AppDatabase.getInstance(UserBooksActivity.this).booksDao().queryNumByName(bName);

                Intent intent = new Intent(UserBooksActivity.this, BookActivity.class);
                intent.putExtra("bNum", bNum);
                startActivity(intent);
            }
        });

        /*Cursor cursor  = AppDatabase.getInstance(this).booksDao().queryCursor();
        List<Map<String, Object>> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<>();

            String bPicPath = cursor.getString(1);
            Bitmap bPic = ImageUtil.getImage(bPicPath);
            map.put("bPic", bPic);

            map.put("bName", cursor.getString(2));

            int cNum = cursor.getInt(3);
            CharactersEntity character = AppDatabase.getInstance(this).charactersDao().queryByNum(cNum);

            String cPicPath =character.getcPic();
            Bitmap cPic = ImageUtil.getImage(cPicPath);
            map.put("cPic", cPic);

            String bCharacter = character.getcName();
            map.put("bCharacter", bCharacter);
            map.put("bIntro", "类型：" + cursor.getString(4));

            list.add(map);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.item_books,
                new String[]{"bPic", "bName", "cPic", "bCharacter", "bIntro"},
                new int[]{R.id.image_bookPic, R.id.text_bookName, R.id.image_characterPic, R.id.text_bookCharacter, R.id.text_bookIntro,});

        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView imageView = (ImageView) view;
                    imageView.setImageBitmap((Bitmap) data);
                    return true;
                } else {
                    return false;
                }
            }
        });

        ListView listView = findViewById(R.id.list_books);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long bNum) {
                bookName = view.findViewById(R.id.text_bookName);
                bName = bookName.getText().toString();

                bNum = AppDatabase.getInstance(UserBooksActivity.this).booksDao().queryNumByName(bName);

                Intent intent = new Intent(UserBooksActivity.this, BookActivity.class);
                intent.putExtra("bNum", bNum);
                startActivity(intent);
            }
        });*/
    }
}
