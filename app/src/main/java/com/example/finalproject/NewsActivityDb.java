package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NewsActivityDb extends AppCompatActivity {

    TextView title, url, sectionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_db);

        setTitle("From your saved articles");
        title = findViewById(R.id.tvTitleDb);
        url = findViewById(R.id.tvUrlDb);
        sectionName = findViewById(R.id.tvSectionNameDb);

        Intent intent = getIntent();

        title.setText(intent.getStringExtra("title"));
        url.setText(intent.getStringExtra("url"));
        sectionName.setText(intent.getStringExtra("sectionName"));

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NewsActivityDb.this, WebviewActivity.class);
                i.putExtra("url", url.getText().toString());
                startActivity(i);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}