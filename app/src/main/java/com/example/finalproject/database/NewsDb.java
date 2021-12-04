package com.example.finalproject.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NewsDb {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int img;
    public String title;
    public String URL;
    public String sectionName;

    public NewsDb(int img, String title, String URL, String sectionName) {
        this.img = img;
        this.title = title;
        this.URL = URL;
        this.sectionName = sectionName;
    }
}
