package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalproject.database.AppDatabase;
import com.example.finalproject.database.NewsDb;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    private NewsDb messageDb;
    private AppDatabase database;
    ArrayList<News> myNews;
    ArrayList<NewsDb> myNews2;
    Button search;
    EditText editText;
    ProgressBar progressBar;
    String str;

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myNews = new ArrayList<>();

        str="https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&amp;q=";
        String Tesla = "Tesla";
        str = str + Tesla;


        // Get all the data
        JsonTask task = new JsonTask();
        task.execute();

        myNews2 = new ArrayList<>();

        database = AppDatabase.getDatabase(getApplicationContext());
        //database.newsDao().removeAllNews();

        search = findViewById(R.id.search);
        editText = findViewById(R.id.edit_text);
        progressBar = findViewById(R.id.progress_circular);

        listView = findViewById(R.id.listView);
        adapter = new MyAdapter(MainActivity.this, myNews);
        listView.setAdapter(adapter);
        listView.setVisibility(View.INVISIBLE);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str = str + editText.getText().toString().trim();
                editText.setText("");
                progressBar.setVisibility(View.VISIBLE);
                listView.setVisibility(View.INVISIBLE);
                JsonTask task2 = new JsonTask();
                task2.execute();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News news = adapter.getItem(i);

                Intent sendStuff = new Intent(MainActivity.this, NewsActivity.class);
                sendStuff.putExtra("title", news.title);
                sendStuff.putExtra("url", news.URL);
                sendStuff.putExtra("sectionName", news.sectionName);
                startActivity(sendStuff);

            }
        });


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
                Intent intent = new Intent(MainActivity.this, MyFavoritesActivity.class);
                startActivity(intent);

            case R.id.action_help:
                Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), "Help about this activity", Snackbar.LENGTH_LONG);

                snackbar.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private class JsonTask extends AsyncTask<Void, Void, String>
    {
        String data = "";

        @Override
        protected String doInBackground(Void... voids) {
            URLConnection urlConn = null;


            BufferedReader bufferedReader = null;
            try
            {
                URL url = new URL(str);
                Log.i("testtt", str);
                urlConn = url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line = "";
                while (line != null){
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                JSONObject jsonObject = new JSONObject(data);
                JSONObject responseObject = jsonObject.getJSONObject("response");
                JSONArray results = responseObject.getJSONArray("results");


                for (int i=0; i < results.length(); i++){
                    JSONObject jo = (JSONObject) results.get(i);
                    String jsonTitlte = (String) jo.getString("webTitle");
                    String jsonURL = (String) jo.getString("webUrl");
                    String jsonSection = (String) jo.getString("sectionName");


                    News news = new News(R.drawable.news, jsonTitlte, jsonURL, jsonSection);
                    myNews.add(news);
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        adapter.notifyDataSetChanged();
                        for (int i = 0; i < 100; i = i + 20) {

                            try {
                                Thread.sleep(1000);
                                progressBar.setProgress(i);
                            } catch (Exception e) {
                                e.getLocalizedMessage();
                            }
                        }

                        progressBar.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        str="https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&amp;q=";



                    }
                });


            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();

            }
            return data;
        }
    }
}