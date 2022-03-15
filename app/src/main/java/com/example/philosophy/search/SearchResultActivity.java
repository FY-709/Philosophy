package com.example.philosophy.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.philosophy.R;
import com.example.philosophy.adapter.BooksAdapter;
import com.example.philosophy.adapter.CharactersAdapter;
import com.example.philosophy.adapter.GenresAdapter;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.BooksEntity;
import com.example.philosophy.db.entity.CharactersEntity;
import com.example.philosophy.db.entity.GenresEntity;
import com.example.philosophy.details.BookActivity;
import com.example.philosophy.details.CharacterActivity;
import com.example.philosophy.details.GenreActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    private RecyclerView recycler1;
    private RecyclerView recycler2;
    private RecyclerView recycler3;
    private TextView result;

    private TextView bookName;
    private TextView characterName;
    private TextView genreName;

    private int bNum;
    private String bName;
    private int cNum;
    private String cName;
    private int gNum;
    private String gName;

    private String query;
    private List<BooksEntity> books = new ArrayList<>();
    private List<CharactersEntity> characters = new ArrayList<>();
    private List<GenresEntity> genres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        init();
    }

    private void init() {
        Intent intent = getIntent();
        query = intent.getStringExtra("query");

        recycler1 = findViewById(R.id.recycler_1);
        recycler2 = findViewById(R.id.recycler_2);
        recycler3 = findViewById(R.id.recycler_3);
        result = findViewById(R.id.text_result);

        BooksEntity book = AppDatabase.getInstance(this).booksDao().queryLikeByName(query);
        CharactersEntity character = AppDatabase.getInstance(this).charactersDao().queryLikeByName(query);
        GenresEntity genre = AppDatabase.getInstance(this).genresDao().queryLikeByName(query);

        if (book != null) {
            searchBooks(book);
        } else if (character != null) {
            searchCharacters(character);
        } else if (genre != null) {
            searchGenres(genre);
        } else {
            result.setText("无搜索结果");
        }
    }

    private void searchBooks(BooksEntity book) {
        books.add(book);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.Adapter adapter1 = new BooksAdapter(books);

        recycler1.setHasFixedSize(true);
        recycler1.setLayoutManager(layoutManager1);
        recycler1.setAdapter(adapter1);

        ((BooksAdapter) adapter1).setOnItemClickListener(new BooksAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                bookName = view.findViewById(R.id.text_bookName);
                bName = bookName.getText().toString();

                bNum = AppDatabase.getInstance(SearchResultActivity.this).booksDao().queryNumByName(bName);

                Intent intent = new Intent(SearchResultActivity.this, BookActivity.class);
                intent.putExtra("bNum", bNum);
                startActivity(intent);
            }
        });

        CharactersEntity character = AppDatabase.getInstance(this).charactersDao().queryByNum(book.getcNum());
        characters.add(character);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.Adapter adapter2 = new CharactersAdapter(characters);

        recycler2.setHasFixedSize(true);
        recycler2.setLayoutManager(layoutManager2);
        recycler2.setAdapter(adapter2);

        ((CharactersAdapter) adapter2).setOnItemClickListener(new CharactersAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                characterName = view.findViewById(R.id.text_characterName);
                cName = characterName.getText().toString();

                CharactersEntity character = AppDatabase.getInstance(SearchResultActivity.this).charactersDao().queryByName(cName);
                cNum = character.getcNum();

                Intent intent = new Intent(SearchResultActivity.this, CharacterActivity.class);
                intent.putExtra("cNum", cNum);

                startActivity(intent);
            }
        });


        GenresEntity genre = AppDatabase.getInstance(this).genresDao().queryByNum(character.getgNum());
        genres.add(genre);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.Adapter adapter3 = new GenresAdapter(genres);

        recycler3.setHasFixedSize(true);
        recycler3.setLayoutManager(layoutManager3);
        recycler3.setAdapter(adapter3);

        ((GenresAdapter) adapter3).setOnItemClickListener(new GenresAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                genreName = view.findViewById(R.id.text_genreName);
                gName = genreName.getText().toString();

                GenresEntity genre = AppDatabase.getInstance(SearchResultActivity.this).genresDao().queryByName(gName);
                gNum = genre.getgNum();

                Intent intent = new Intent(SearchResultActivity.this, GenreActivity.class);
                intent.putExtra("gNum", gNum);
                startActivity(intent);
            }
        });
    }


    private void searchCharacters(CharactersEntity character) {
        characters.add(character);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.Adapter adapter1 = new CharactersAdapter(characters);

        recycler1.setHasFixedSize(true);
        recycler1.setLayoutManager(layoutManager1);
        recycler1.setAdapter(adapter1);

        ((CharactersAdapter) adapter1).setOnItemClickListener(new CharactersAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                characterName = view.findViewById(R.id.text_characterName);
                cName = characterName.getText().toString();

                CharactersEntity character = AppDatabase.getInstance(SearchResultActivity.this).charactersDao().queryByName(cName);
                cNum = character.getcNum();

                Intent intent = new Intent(SearchResultActivity.this, CharacterActivity.class);
                intent.putExtra("cNum", cNum);

                startActivity(intent);
            }
        });

        books = AppDatabase.getInstance(this).booksDao().queryByCnum(character.getcNum());

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.Adapter adapter2 = new BooksAdapter(books);

        recycler2.setHasFixedSize(true);
        recycler2.setLayoutManager(layoutManager2);
        recycler2.setAdapter(adapter2);

        ((BooksAdapter) adapter2).setOnItemClickListener(new BooksAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                bookName = view.findViewById(R.id.text_bookName);
                bName = bookName.getText().toString();

                bNum = AppDatabase.getInstance(SearchResultActivity.this).booksDao().queryNumByName(bName);

                Intent intent = new Intent(SearchResultActivity.this, BookActivity.class);
                intent.putExtra("bNum", bNum);
                startActivity(intent);
            }
        });

        GenresEntity genre = AppDatabase.getInstance(this).genresDao().queryByNum(character.getgNum());
        genres.add(genre);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.Adapter adapter3 = new GenresAdapter(genres);

        recycler3.setHasFixedSize(true);
        recycler3.setLayoutManager(layoutManager3);
        recycler3.setAdapter(adapter3);

        ((GenresAdapter) adapter3).setOnItemClickListener(new GenresAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                genreName = view.findViewById(R.id.text_genreName);
                gName = genreName.getText().toString();

                GenresEntity genre = AppDatabase.getInstance(SearchResultActivity.this).genresDao().queryByName(gName);
                gNum = genre.getgNum();

                Intent intent = new Intent(SearchResultActivity.this, GenreActivity.class);
                intent.putExtra("gNum", gNum);
                startActivity(intent);
            }
        });
    }


    private void searchGenres(GenresEntity genre) {
        genres.add(genre);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.Adapter adapter1 = new GenresAdapter(genres);

        recycler1.setHasFixedSize(true);
        recycler1.setLayoutManager(layoutManager1);
        recycler1.setAdapter(adapter1);

        ((GenresAdapter) adapter1).setOnItemClickListener(new GenresAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                genreName = view.findViewById(R.id.text_genreName);
                gName = genreName.getText().toString();

                GenresEntity genre = AppDatabase.getInstance(SearchResultActivity.this).genresDao().queryByName(gName);
                gNum = genre.getgNum();

                Intent intent = new Intent(SearchResultActivity.this, GenreActivity.class);
                intent.putExtra("gNum", gNum);
                startActivity(intent);
            }
        });

        characters = AppDatabase.getInstance(this).charactersDao().queryByGenre(genre.getgNum());

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.Adapter adapter2 = new CharactersAdapter(characters);

        recycler2.setHasFixedSize(true);
        recycler2.setLayoutManager(layoutManager2);
        recycler2.setAdapter(adapter2);

        ((CharactersAdapter) adapter2).setOnItemClickListener(new CharactersAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                characterName = view.findViewById(R.id.text_characterName);
                cName = characterName.getText().toString();

                CharactersEntity character = AppDatabase.getInstance(SearchResultActivity.this).charactersDao().queryByName(cName);
                cNum = character.getcNum();

                Intent intent = new Intent(SearchResultActivity.this, CharacterActivity.class);
                intent.putExtra("cNum", cNum);

                startActivity(intent);
            }
        });

        for (int i = 0; i < characters.size(); i++) {
            books.addAll(AppDatabase.getInstance(this).booksDao().queryByCnum(characters.get(i).getcNum()));
        }

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.Adapter adapter3 = new BooksAdapter(books);

        recycler3.setHasFixedSize(true);
        recycler3.setLayoutManager(layoutManager3);
        recycler3.setAdapter(adapter3);

        ((BooksAdapter) adapter3).setOnItemClickListener(new BooksAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                bookName = view.findViewById(R.id.text_bookName);
                bName = bookName.getText().toString();

                bNum = AppDatabase.getInstance(SearchResultActivity.this).booksDao().queryNumByName(bName);

                Intent intent = new Intent(SearchResultActivity.this, BookActivity.class);
                intent.putExtra("bNum", bNum);
                startActivity(intent);
            }
        });
    }
}
