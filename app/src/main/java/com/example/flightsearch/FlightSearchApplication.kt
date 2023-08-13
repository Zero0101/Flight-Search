package com.example.flightsearch

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearch.data.FlightSearchDatabase
import com.example.flightsearch.data.UserPreferencesRepository


private const val USER_SEARCH_REQUEST = "user_search_request"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_SEARCH_REQUEST
)
class FlightSearchApplication: Application() {
    val database:FlightSearchDatabase by lazy { FlightSearchDatabase.getDatabase(this) }
    lateinit var userPreferencesRepository: UserPreferencesRepository
    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}