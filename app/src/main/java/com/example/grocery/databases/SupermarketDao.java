package com.example.grocery.databases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.grocery.Supermarket;

import java.util.List;

@Dao
public interface SupermarketDao {

    @Insert
    void insert(Supermarket s);

    @Query("SELECT * FROM Supermarkets")
    List<Supermarket> getAllSupermarkets();
}
