package com.example.flightsearch.ui

import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class HomeScreenViewModel(
    private val airportDao: AirportDao,
    private val userPreferencesRepository: UserPreferencesRepository
    ): ViewModel() {

    val userPreferencesFlow =  userPreferencesRepository.userSearchRequest
    var searchRequest = userPreferencesFlow.collect()


    fun updateSearchRequest(airportToSearch: String) {
        searchRequest = airportToSearch
        saveUserSearchRequest(airportToSearch)
    }

    fun getNameByIataCode(iataCode: String): String = airportDao.getNameByIataCode(iataCode)

    fun getListOfIataCodes(iataCode: String): List<String> = airportDao.getIataCodes(iataCode)

    fun getAirports(): Flow<List<Airport>> {
        return if (searchRequest.isNotEmpty()) airportDao.getItems("%$searchRequest%")
        else airportDao.getItems(searchRequest)
    }

    fun saveUserSearchRequest(userSearchRequest: String) {
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