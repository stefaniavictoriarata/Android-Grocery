package com.example.grocery;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Supermarkets")
public class Supermarket {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String supermarketName;
    private String location;

    public Supermarket(int id, String supermarketName, String location) {
        this.id = id;
        this.supermarketName = supermarketName;
        this.location = location;
    }

    @Override
    public String toString() {
        return "Supermarket{" +
                "id=" + id +
                ", supermarketName='" + supermarketName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupermarketName() {
        return supermarketName;
    }

    public void setSupermarketName(String supermarketName) {
        this.supermarketName = supermarketName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
