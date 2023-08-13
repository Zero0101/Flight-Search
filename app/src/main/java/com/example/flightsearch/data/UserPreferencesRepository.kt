package com.example.flightsearch.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    val userSearchRequest: Flow<String> = dataStore.data
        .catch {
            if(it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {preferences ->
            preferences[USER_SEARCH_REQUEST] ?: ""
        }

    suspend fun saveUserSearchRequest(userSearchRequest: String) {
        dataStore.edit { preferences ->
            preferences[USER_SEARCH_REQUEST] = userSearchRequest
        }
    }

    private companion object {
        val USER_SEARCH_REQUEST = stringPreferencesKey("user_search_request")
        const val TAG = "UserPreferencesRepo"
    }
}