package com.example.finalproject.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNews(NewsDb newsDb);

    @Query("select * from newsdb")
    public List<NewsDb> getAllNews();

    @Query("select * from newsDb where id = :newsDbId")
    public List<NewsDb> getNews(long newsDbId);

    @Query("DELETE FROM newsdb WHERE id = :newsDbId")
    abstract void deleteByNewsId(long newsDbId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateNews(NewsDb newsDb);

    @Query("delete from newsdb")
    void removeAllNews();
}
