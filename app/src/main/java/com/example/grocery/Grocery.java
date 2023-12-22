package com.example.grocery;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import kotlin.experimental.ExperimentalTypeInference;

@Entity(tableName = "Groceries", foreignKeys = @ForeignKey(entity = Supermarket.class, parentColumns = "id",childColumns = "idSupermarket" ))
public class Grocery implements Serializable {

    @PrimaryKey
    private int id;

    private String name;
    private boolean perishable;
    private float price;

    private int idSupermarket;

    @Ignore
    public Grocery() {
    }

    @Ignore
    public Grocery(String name, boolean perishable, float price) {
        this.name = name;
        this.perishable = perishable;
        this.price = price;
    }

    public Grocery(int id, String name, boolean perishable, float price, int idSupermarket) {
        this.id = id;
        this.name = name;
        this.perishable = perishable;
        this.price = price;
        this.idSupermarket = idSupermarket;
    }

//    protected Grocery(Parcel in) {
//        name = in.readString();
//        perishable = in.readByte() != 0;
//        price = in.readFloat();
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSupermarket() {
        return idSupermarket;
    }

    public void setIdSupermarket(int idSupermarket) {
        this.idSupermarket = idSupermarket;
    }

//    public static final Creator<Grocery> CREATOR = new Creator<Grocery>() {
//        @Override
//        public Grocery createFromParcel(Parcel in) {
//            return new Grocery(in);
//        }
//
//        @Override
//        public Grocery[] newArray(int size) {
//            return new Grocery[size];
//        }
//    };

    @Override
    public String toString() {
        String p = perishable ? "is perishable" : "is not perishable";
        return "The product '" + name + '\'' + p +
                "and it has the price " + price;
    }

    public String getName() {
        return name;
    }

    public boolean isPerishable() {
        return perishable;
    }

    public float getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPerishable(boolean perishable) {
        this.perishable = perishable;
    }

    public void setPrice(float price) {
        this.price = price;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }

//    @Override
//    public void writeToParcel(@NonNull Parcel dest, int flags) {
//        dest.writeString(name);
//        dest.writeByte((byte) (perishable ? 1 : 0));
//        dest.writeFloat(price);
//    }
}
