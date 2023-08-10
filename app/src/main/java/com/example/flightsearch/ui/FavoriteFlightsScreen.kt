package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.data.Favorite

@Composable
fun FavoriteFlightsScreen(
    favoriteViewModel: FavoriteFlightsScreenViewModel = viewModel(factory = FavoriteFlightsScreenViewModel.factory)
) {
    val favoriteList by favoriteViewModel.getFavorites().collectAsState(emptyList())
    ListOfFlightsScreen(listOfFlights = favoriteList)
}

@Composable
fun FlightsFromAirport(
    iataCode: String,
    airportViewModel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.factory)
) {
    val listIataCodes = airportViewModel.getListOfIataCodes(iataCode)
    val listOfFlights = mutableListOf<Favorite>()
    for (i in listIataCodes.indices) {
        listOfFlights.add(Favorite(i + 1, iataCode, listIataCodes[i]))
    }
    ListOfFlightsScreen(listOfFlights = listOfFlights)
}

@Composable
fun ListOfFlightsScreen(listOfFlights: List<Favorite>) {
    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
        items(listOfFlights) {
            FlightFromTo(flight = it)
        }
    }
}

@Composable
fun FlightFromTo(
    flight: Favorite,
    airportViewModel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.factory)
) {
    Row(modifier = Modifier.fillMaxWidth()){
        Column {
            Text(text = "DEPART")
            Row {
                Text(text = flight.departureCode)
                Text(text = airportViewModel.getNameByIataCode(flight.departureCode))
            }
            Text(text = "ARRIVE")
            Row {
                Text(text = flight.destinationCode)
                Text(text = airportViewModel.getNameByIataCode(flight.destinationCode))
            }
        }
        Icon(imageVector = Icons.Rounded.Star, contentDescription = stringResource(R.string.star))
    }
}