package com.example.myapplication.Data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Category implements Serializable { //implements Parcelable
    private final int id;
    private final String name;

//    protected Category(Parcel in) {
//        id = in.readInt();
//        name = in.readString();
//    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;

        Category category = (Category) o;
        return category.getId() == this.getId()
                || category.getName().equals(this.getName());
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(id);
//        dest.writeString(name);
//    }
//
//    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
//        public Category createFromParcel(Parcel in) {
//            return new Category(in);
//        }
//
//        public Category[] newArray(int size) {
//            return new Category[size];
//        }
//    };
}