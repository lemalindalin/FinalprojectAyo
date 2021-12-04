package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.finalproject.database.AppDatabase;
import com.example.finalproject.database.MyAdapterDb;
import com.example.finalproject.database.NewsDb;

import java.util.ArrayList;
import java.util.List;

public class MyFavoritesActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<NewsDb> myNews2;
    private MyAdapterDb adapter;
    private AppDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites);

        myNews2 = new ArrayList<>();
        listView = findViewById(R.id.listView_db);

        database = AppDatabase.getDatabase(getApplicationContext());

        List<NewsDb> newsDbs = database.newsDao().getAllNews();
        for (int i = 0; i<newsDbs.size(); i++){
            myNews2.add(newsDbs.get(i));
        }

        adapter = new MyAdapterDb(MyFavoritesActivity.this, myNews2);
        listView.setAdapter(adapter);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("My Favorites");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}