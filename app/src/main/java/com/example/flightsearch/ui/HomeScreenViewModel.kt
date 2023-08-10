package com.example.flightsearch.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.Airport
import com.example.flightsearch.data.AirportDao
import kotlinx.coroutines.flow.Flow


class HomeScreenViewModel(private val airportDao: AirportDao): ViewModel() {
    var searchRequest by mutableStateOf("")
        private set

    fun updateSearchRequest(airportToSearch: String) {
        searchRequest = airportToSearch
    }

    fun getNameByIataCode(iataCode: String): String = airportDao.getNameByIataCode(iataCode)

    fun getListOfIataCodes(iataCode: String): List<String> = airportDao.getIataCodes(iataCode)

    fun getAirports(): Flow<List<Airport>> {
        return if (searchRequest.isNotEmpty()) airportDao.getItems("%$searchRequest%")
        else airportDao.getItems(searchRequest)
    }

    companion object {
        val factory:ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                HomeScreenViewModel(application.database.airportDao())
            }
        }
    }
}