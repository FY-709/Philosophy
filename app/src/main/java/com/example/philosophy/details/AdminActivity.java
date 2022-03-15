package com.example.philosophy.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.philosophy.MainActivity;
import com.example.philosophy.R;
import com.example.philosophy.lists.AdminBooksActivity;
import com.example.philosophy.lists.AdminCharactersActivity;
import com.example.philosophy.lists.AdminGenresActivity;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView adminBooks;
    private TextView adminCharacters;
    private TextView adminGenres;
    private TextView adminLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        init();
    }

    private void init() {
        adminBooks = findViewById(R.id.text_adminBooks);
        adminCharacters = findViewById(R.id.text_adminCharacters);
        adminGenres = findViewById(R.id.text_adminGenres);
        adminLogout = findViewById(R.id.text_adminLogout);

        adminBooks.setOnClickListener(this);
        adminCharacters.setOnClickListener(this);
        adminGenres.setOnClickListener(this);
        adminLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_adminBooks:
                Intent adminBooksIntent = new Intent(AdminActivity.this, AdminBooksActivity.class);
                startActivity(adminBooksIntent);
                break;
            case R.id.text_adminCharacters:
                Intent adminCharactersIntent = new Intent(AdminActivity.this, AdminCharactersActivity.class);
                startActivity(adminCharactersIntent);
                break;
            case R.id.text_adminGenres:
                Intent adminGenresIntent = new Intent(AdminActivity.this, AdminGenresActivity.class);
                startActivity(adminGenresIntent);
                break;
            case R.id.text_adminLogout:
                Intent mainIntent = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(mainIntent);
                break;
            default:
                break;
        }
    }
}
