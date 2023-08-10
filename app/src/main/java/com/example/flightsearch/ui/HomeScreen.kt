package com.example.flightsearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.flightsearch.R
import com.example.flightsearch.data.Airport
import com.example.flightsearch.ui.theme.FlightSearchTheme

enum class HomeScreen () {
    HomeScreen,
    ListOfAirports
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = viewModel(factory = HomeScreenViewModel.factory )
) {
    val airportsList by viewModel.getAirports().collectAsState(emptyList())
    val navController = rememberNavController()
    Scaffold(
        topBar = { FlightSearchTopAppBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TextField(
                value = viewModel.searchRequest,
                onValueChange = { viewModel.updateSearchRequest(it) },
                label = { Text("Input your search request") },
                modifier = Modifier.fillMaxWidth()
            )
            /*NavHost(
                navController = navController,
                startDestination = HomeScreen.HomeScreen.name) {
                composable(HomeScreen.HomeScreen.name) {
                    if (viewModel.searchRequest.isEmpty()) FavoriteFlightsScreen()
                    else AirportsScreen(airports = airportsList)
                }
                composable(HomeScreen.ListOfAirports.name) {
                    FlightsFromAirport()
                }
            }*/
            FlightsFromAirport("FCO")
        }
    }
}

@Composable
fun AirportsScreen(
    airports: List<Airport>,
    onAirportClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(vertical = 8.dp)) {
        items(airports) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 16.dp
                    )
                    .clickable { },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = it.iataCode,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = it.name,
                    modifier = Modifier.weight(7f)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) },
        modifier = modifier
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    FlightSearchTheme {
        HomeScreen()
    }
}