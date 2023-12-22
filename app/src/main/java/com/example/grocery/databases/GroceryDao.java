package com.example.grocery.databases;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.grocery.Grocery;

import java.util.List;

@Dao
public interface GroceryDao {

    @Insert
    void insert(Grocery grocery);

    @Query("SELECT * FROM Groceries")
    List<Grocery> getAllGroceries();

    @Query("DELETE FROM Groceries")
    void deleteAll();

    @Query("DELETE FROM Groceries WHERE name= :name")
    void delete(String name);




}
