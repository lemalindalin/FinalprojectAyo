package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.finalproject.database.NewsDb;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<News> {
    Context context;
    ArrayList rNews;

    MyAdapter(Context c, ArrayList<News> newsDbArrayList){
        super(c, R.layout.row, newsDbArrayList);
        this.context = c;
        this.rNews = newsDbArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row, parent, false);

        News newsDb = getItem(position);
        ImageView image = row.findViewById(R.id.image);
        TextView myTitle = row.findViewById(R.id.textView2);
        TextView mySection = row.findViewById(R.id.textView1);

        image.setImageResource(newsDb.img);
        myTitle.setText(newsDb.title);
        mySection.setText(newsDb.sectionName);
        return row;
    }
}
