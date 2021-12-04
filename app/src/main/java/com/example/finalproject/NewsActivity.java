package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.database.AppDatabase;
import com.example.finalproject.database.NewsDb;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class NewsActivity extends AppCompatActivity {

    TextView title, url, sectionName;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        setTitle("The Guardian news article");
        title = findViewById(R.id.tvTitle);
        url = findViewById(R.id.tvUrl);
        sectionName = findViewById(R.id.tvSectionName);

        //Database
        database = AppDatabase.getDatabase(getApplicationContext());

        Intent intent = getIntent();

        title.setText(intent.getStringExtra("title"));
        url.setText(intent.getStringExtra("url"));
        sectionName.setText(intent.getStringExtra("sectionName"));

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewsActivity.this, WebviewActivity.class);
                i.putExtra("url", url.getText().toString());
                startActivity(i);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.save_news, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                List<NewsDb> list = database.newsDao().getAllNews();

                NewsDb newsDb = new NewsDb(R.drawable.news, title.getText().toString(), url.getText().toString(), sectionName.getText().toString());
                database.newsDao().addNews(newsDb);
                Toast.makeText(this, "Item added to your favorites", Toast.LENGTH_LONG).show();


            case R.id.action_help:
                Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), "Help about this activity", Snackbar.LENGTH_LONG);

                snackbar.show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}