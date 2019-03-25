package com.nsa.cecobike;

import android.app.Activity;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.Update;
import java.util.List;
//

@Dao
public interface JourneyDao { ;
    @Query("SELECT * FROM journey")
    List<Journey> getAllJourneys();

    @Insert
    void insertAll(Journey... journeys);

    @Update
    void updateAll(Journey... journeys);

    @Delete
    void delete(Journey journey);


}
