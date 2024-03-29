package dev.mainhq.schedules.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import dev.mainhq.schedules.database.entities.StopTimes;

@Dao
interface StopTimesDAO {
    //todo the query trips.serviceid is very wrong!!! Need to update database
    @Query("SELECT arrivaltime FROM StopTimes LIMIT 10")//JOIN Trips ON StopTimes.tripid = Trips.serviceid ") +
            //"WHERE Trips.trip_headsign LIKE (:headsign) LIMIT 10;")
    suspend fun getArrivalTimes(/*headsign : String*/) : List<String>;
}
