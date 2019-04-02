package com.nsa.cecobike;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class DataTypeConverter {
    @TypeConverter
    public static ArrayList<Point> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Point>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Point> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static Date toDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value) {
        return value == null ? null : value.getTime();

    }
}