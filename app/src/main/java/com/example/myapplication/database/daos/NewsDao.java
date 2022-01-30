package com.example.myapplication.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.database.entites.NewsEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM NewsEntity")
    Observable<List<NewsEntity>> getAll();

    @Query("SELECT * FROM NewsEntity WHERE category = :category")
    Observable<List<NewsEntity>> getNewsByCategory(String category);

    @Query("SELECT * FROM NewsEntity WHERE id = :id")
    NewsEntity getNewsById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(NewsEntity... newsEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewsEntity newsEntity);

    @Delete
    void delete(NewsEntity newsEntity);

}
