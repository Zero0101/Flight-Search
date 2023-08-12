package com.example.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Airport::class, Favorite::class],
    version = 2,
    exportSchema = false
)
abstract class FlightSearchDatabase: RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao


    companion object {
        @Volatile
        private var Instance: FlightSearchDatabase? = null
        fun getDatabase(context: Context):FlightSearchDatabase {
            return Instance ?: synchronized(this) {

                val MIGRATION_1_2 = object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("CREATE TABLE IF NOT EXISTS favorite (id INTEGER PRIMARY KEY, departureCode TEXT, destinationCode TEXT)")
                    }
                }

                Room.databaseBuilder(context, FlightSearchDatabase::class.java, name = "flight_search_database")
                    .createFromAsset("database/flight_search.db")
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}