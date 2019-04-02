package com.nsa.cecobike;

import android.app.Activity;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.Update;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
//

@Dao
public interface JourneyDao { ;
    @Query("SELECT * FROM Journey")
    List<Journey> getAllJourneys();

    @Insert
    void insertJourneys(Journey... journey);

    @Query("DELETE FROM Journey")
    void clearJourneys();


}
