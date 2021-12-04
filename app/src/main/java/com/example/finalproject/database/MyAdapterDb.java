package com.example.finalproject.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.MainActivity;
import com.example.finalproject.MyFavoritesActivity;
import com.example.finalproject.News;
import com.example.finalproject.NewsActivity;
import com.example.finalproject.NewsActivityDb;
import com.example.finalproject.R;

import java.util.ArrayList;

public class MyAdapterDb extends ArrayAdapter<NewsDb> {
    Context context;
    ArrayList rNewsDb;
    NewsDb newsDb;

    public MyAdapterDb(@NonNull Context context, ArrayList<NewsDb> newsDbArrayList) {
        super(context, R.layout.row_db, newsDbArrayList);
        this.context = context;
        this.rNewsDb = newsDbArrayList;
    }

    private static class ViewHolder{
        ImageView imageDb;
        TextView myTitleDb;
        TextView mySectionDb;
        Button btnDeleteDb;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row_db, parent, false);
        ViewHolder viewHolder;


        newsDb = getItem(position);

//        ImageView imageDb = row.findViewById(R.id.image_db);
//        TextView myTitleDb = row.findViewById(R.id.textView2_db);
//        TextView mySectionDb = row.findViewById(R.id.textView1_db);
//        Button btnDeleteDb = row.findViewById(R.id.delete);
        viewHolder = new ViewHolder();
        viewHolder.imageDb = row.findViewById(R.id.image_db);
        viewHolder.myTitleDb = row.findViewById(R.id.textView2_db);
        viewHolder.mySectionDb = row.findViewById(R.id.textView1_db);
        viewHolder.btnDeleteDb = row.findViewById(R.id.delete);
        row.setTag(viewHolder);
        final NewsDb x = (NewsDb) rNewsDb.get(position);



        viewHolder.imageDb.setImageResource(newsDb.img);
        viewHolder.myTitleDb.setText(newsDb.title);
        viewHolder.mySectionDb.setText(newsDb.sectionName);

        viewHolder.imageDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callWebView(x);
            }
        });


        viewHolder.btnDeleteDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase.getDatabase(getContext()).newsDao().deleteByNewsId(x.id);
                Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_LONG).show();
                ((Activity)context).finish();
                Intent i = new Intent(getContext(), MyFavoritesActivity.class);
                getContext().startActivity(i);

            }
        });

        return row;
    }



    private void callWebView(NewsDb newsDb2){
//        NewsDb newsDb= new NewsDb();

        Intent sendStuff = new Intent(getContext(), NewsActivityDb.class);
        sendStuff.putExtra("title", newsDb2.title);
        sendStuff.putExtra("url", newsDb2.URL);
        sendStuff.putExtra("sectionName", newsDb2.sectionName);
        context.startActivity(sendStuff);
    }
}
