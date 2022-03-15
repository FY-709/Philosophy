package com.example.philosophy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.philosophy.R;
import com.example.philosophy.adapter.HomeBooksAdapter;
import com.example.philosophy.adapter.HomeCharactersAdapter;
import com.example.philosophy.adapter.HomeGenresAdapter;
import com.example.philosophy.db.AppDatabase;
import com.example.philosophy.db.entity.BooksEntity;
import com.example.philosophy.db.entity.CharactersEntity;
import com.example.philosophy.db.entity.GenresEntity;
import com.example.philosophy.details.BookActivity;
import com.example.philosophy.details.CharacterActivity;
import com.example.philosophy.details.GenreActivity;
import com.example.philosophy.lists.UserBooksActivity;
import com.example.philosophy.lists.UserCharactersActivity;
import com.example.philosophy.lists.UserGenresActivity;
import com.example.philosophy.search.SearchResultActivity;

import java.util.List;

public class HomeFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final SearchView search = view.findViewById(R.id.search);
        //final Spinner sifting = view.findViewById(R.id.sifting);

        final TextView moreBooks = view.findViewById(R.id.text_moreBooks);
        final RecyclerView recyclerBooks = view.findViewById(R.id.recycler_books);

        final TextView moreCharacters = view.findViewById(R.id.text_moreCharacters);
        final RecyclerView recyclerCharacters = view.findViewById(R.id.recycler_characters);

        final TextView moreGenres = view.findViewById(R.id.text_moreGenres);
        final RecyclerView recyclerGenres = view.findViewById(R.id.recycler_genres);

        final RecyclerView.Adapter booksAdapter, charactersAdapter, genresAdapter;
        final GridLayoutManager booksLayoutManager, charactersLayoutManager, genresLayoutManager;

        List<BooksEntity> books = AppDatabase.getInstance(getActivity()).booksDao().queryLimit();
        List<CharactersEntity> characters = AppDatabase.getInstance(getActivity()).charactersDao().queryLimit();
        List<GenresEntity> genres = AppDatabase.getInstance(getActivity()).genresDao().queryLimit();

        search.setSubmitButtonEnabled(true);
        //ImageView submit = search.findViewById(R.id.search_go_btn);
        //submit.setImageResource(R.drawable.ic_search);

        //SpinnerAdapter adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.sifting, android.R.layout.simple_spinner_dropdown_item);
        //sifting.setAdapter(adapter);

        /*Spinner sifting = new Spinner(getActivity());
        String[] strArray = new String[]{"书籍","人物","流派"};
        List<String> strList = Arrays.asList(strArray);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, strList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sifting.setItems(strList);*/

        booksLayoutManager = new GridLayoutManager(getActivity(), 3);
        booksLayoutManager.setInitialPrefetchItemCount(3);

        booksAdapter = new HomeBooksAdapter(books);
        recyclerBooks.setHasFixedSize(true);
        recyclerBooks.setLayoutManager(booksLayoutManager);
        recyclerBooks.setAdapter(booksAdapter);

        ((HomeBooksAdapter) booksAdapter).setOnItemClickListener(new HomeBooksAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                TextView bookName = view.findViewById(R.id.text_bookName);
                String bName = bookName.getText().toString();

                int bNum = AppDatabase.getInstance(getActivity()).booksDao().queryNumByName(bName);

                Intent intent = new Intent(getActivity(), BookActivity.class);
                intent.putExtra("bNum", bNum);
                startActivity(intent);
            }
        });

        charactersLayoutManager = new GridLayoutManager(getActivity(), 3);
        charactersLayoutManager.setInitialPrefetchItemCount(3);

        charactersAdapter = new HomeCharactersAdapter(characters);
        recyclerCharacters.setItemViewCacheSize(500);
        recyclerCharacters.setDrawingCacheEnabled(true);
        recyclerCharacters.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerCharacters.setNestedScrollingEnabled(false);
        recyclerCharacters.setHasFixedSize(true);
        recyclerCharacters.setLayoutManager(charactersLayoutManager);
        recyclerCharacters.setAdapter(charactersAdapter);

        ((HomeCharactersAdapter) charactersAdapter).setOnItemClickListener(new HomeCharactersAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                TextView characterName = view.findViewById(R.id.text_name);
                String cName = characterName.getText().toString();

                CharactersEntity character = AppDatabase.getInstance(getActivity()).charactersDao().queryByName(cName);
                int cNum = character.getcNum();

                Intent intent = new Intent(getActivity(), CharacterActivity.class);
                intent.putExtra("cNum", cNum);

                startActivity(intent);
            }
        });

        genresLayoutManager = new GridLayoutManager(getActivity(), 3);
        genresLayoutManager.setInitialPrefetchItemCount(3);

        genresAdapter = new HomeGenresAdapter(genres);
        recyclerGenres.setItemViewCacheSize(500);
        recyclerGenres.setDrawingCacheEnabled(true);
        recyclerGenres.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerGenres.setNestedScrollingEnabled(false);
        recyclerGenres.setHasFixedSize(true);
        recyclerGenres.setLayoutManager(genresLayoutManager);
        recyclerGenres.setAdapter(genresAdapter);

        ((HomeGenresAdapter) genresAdapter).setOnItemClickListener(new HomeGenresAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                TextView genreName = view.findViewById(R.id.text_name);
                String gName = genreName.getText().toString();

                GenresEntity genre = AppDatabase.getInstance(getActivity()).genresDao().queryByName(gName);
                int gNum = genre.getgNum();

                Intent intent = new Intent(getActivity(), GenreActivity.class);
                intent.putExtra("gNum", gNum);
                startActivity(intent);
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    Toast.makeText(getActivity(), "请输入搜索内容", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                    intent.putExtra("query", query);
                    startActivity(intent);
                }
                return true;
            }

            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        /*sifting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //这个方法里可以对点击事件进行处理
                //i指的是点击的位置,通过i可以取到相应的数据源
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/

        moreBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserBooksActivity.class);
                startActivity(intent);
            }
        });

        moreCharacters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserCharactersActivity.class);
                startActivity(intent);
            }
        });

        moreGenres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserGenresActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
