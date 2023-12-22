package com.example.grocery.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.grocery.Grocery;
import com.example.grocery.Supermarket;

@Database(entities = {Grocery.class, Supermarket.class}, version = 1, exportSchema = false)
public abstract class GroceryDb extends RoomDatabase {

    private final static String DB_NAME = "Groceries.db";
    private static GroceryDb INSTANCE;

    public static GroceryDb getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            INSTANCE = Room.databaseBuilder(context, GroceryDb.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
            //Constructor care primeste contextul si numele
            //se poate executa pe Main Thread
            //Se recreeaza baza de date cand se schimba schema
        }
        return INSTANCE;
    }

    public abstract GroceryDao getGroceryDao();

    public abstract SupermarketDao getSupermarketDao();


}
