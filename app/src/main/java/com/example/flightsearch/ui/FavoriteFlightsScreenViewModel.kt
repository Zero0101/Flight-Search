package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.Favorite
import com.example.flightsearch.data.FavoriteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavoriteFlightsScreenViewModel(private val favoriteDao: FavoriteDao): ViewModel() {
    fun addToFavorite(flight: Favorite) {
        viewModelScope.launch { favoriteDao.addToFavorite(flight) }
    }

    fun removeFromFavorite(flight: Favorite) {
        viewModelScope.launch { favoriteDao.removeFromFavorite(flight) }
    }

    fun getFavorites(): Flow<List<Favorite>> = favoriteDao.getFavorites()

    companion object {
        val factory:ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                FavoriteFlightsScreenViewModel(application.database.favoriteDao())
            }
        }
    }
}