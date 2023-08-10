package com.example.flightsearch.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("SELECT name FROM airport WHERE iata_code LIKE :iataCode")
    fun getNameByIataCode(iataCode: String): String

    @Query("SELECT iata_code FROM airport WHERE iata_code != :iataCode")
    fun getIataCodes(iataCode: String): List<String>

    @Query(
        "SELECT * FROM airport " +
                "WHERE iata_code LIKE :searchRequest OR name LIKE :searchRequest " +
                "ORDER BY passengers DESC")
    fun getItems(searchRequest: String): Flow<List<Airport>>
}