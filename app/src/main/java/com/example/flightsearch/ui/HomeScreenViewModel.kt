package com.example.flightsearch.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.AirportDao
import com.example.flightsearch.data.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class HomeScreenViewModel(
    private val airportDao: AirportDao,
    private val userPreferencesRepository: UserPreferencesRepository
    ): ViewModel() {

    val userPreferences: StateFlow<String> =  userPreferencesRepository.userSearchRequest.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ""
    )
    var preferences by mutableStateOf("")
        private set
    init {
        preferences = userPreferences.value
    }

    fun getNameByIataCode(iataCode: String): String = airportDao.getNameByIataCode(iataCode)

    fun getListOfIataCodes(iataCode: String): List<String> = airportDao.getIataCodes(iataCode)

    fun getAirports(searchRequest: String): Flow<List<Airport>> {
        return if (searchRequest.isNotEmpty()) airportDao.getItems("%$searchRequest%")
        else airportDao.getItems(searchRequest)
    }

    fun saveUserSearchRequest(userSearchRequest: String) {
        preferences = userSearchRequest
        viewModelScope.launch {
            userPreferencesRepository.saveUserSearchRequest(userSearchRequest)
        }
    }

    companion object {
        val factory:ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                HomeScreenViewModel(
                    application.database.airportDao(),
                    application.userPreferencesRepository
                )
            }
        }
    }
}