package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.R
import com.example.flightsearch.data.Favorite

@Composable
fun FavoriteFlightsScreen(
    favoriteViewModel: FavoriteFlightsScreenViewModel = viewModel(factory = FavoriteFlightsScreenViewModel.factory)
) {
    val favoriteList by favoriteViewModel.getFavorites().collectAsState(emptyList())
    Column{
        Text(text = "Favorite routes")
        ListOfFlightsScreen(listOfFlights = favoriteList)
    }
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
    Column {
        Text(text = "Flights from $iataCode")
        ListOfFlightsScreen(listOfFlights = listOfFlights)
    }
}

@Composable
fun ListOfFlightsScreen(listOfFlights: List<Favorite>) {
    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
        items(listOfFlights) {
            FlightFromTo(
                flight = it,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun FlightFromTo(
    flight: Favorite,
    modifier: Modifier = Modifier,
    airportViewModel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.factory),
    favoriteViewModel: FavoriteFlightsScreenViewModel = viewModel(factory = FavoriteFlightsScreenViewModel.factory)
) {
    val favoriteList by favoriteViewModel.getFavorites().collectAsState(emptyList())
    val inFavorites = favoriteList.map { Pair(it.departureCode, it.destinationCode) }.contains(Pair(flight.departureCode, flight.destinationCode))
        Card(modifier = modifier){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "DEPART",
                        fontWeight = Bold
                    )
                    Row {
                        Text(
                            text = flight.departureCode,
                            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                        )
                        Text(
                            text = airportViewModel.getNameByIataCode(flight.departureCode),
                            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                        )
                    }
                    Text(
                        text = "ARRIVE",
                        fontWeight = Bold
                    )
                    Row {
                        Text(
                            text = flight.destinationCode,
                            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                        )
                        Text(
                            text = airportViewModel.getNameByIataCode(flight.destinationCode),
                            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                        )
                    }
                }
                IconButton(
                    onClick = {
                        if (inFavorites) favoriteViewModel.removeFromFavorite(flight)
                        else favoriteViewModel.addToFavorite(flight)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = stringResource(R.string.star),
                        tint = if (inFavorites) Color.Red else LocalContentColor.current
                    )
                }
            }
        }
}