package com.code4galaxy.reviewnow.view.feature.user.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.model.UiState
import com.code4galaxy.reviewnow.viewmodel.UserViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               viewModel: UserViewModel = hiltViewModel(),
               onClick:(brandId:String)->Unit={}) {

    val brandState by viewModel.brandsState.collectAsState()
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getAllBrands()
    }


    // Main vertical layout of the screen
    Column(modifier = Modifier.fillMaxSize()) {
        // Search bar at the top
        SearchBarUI(text = searchText,
            onValueChange = { searchText = it } )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))

        // Title above the list
        Text(
            "Brands",
            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.dimen_16_dp))
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_8_dp)))

        // Box with weight to make LazyColumn scrollable
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.dimen_16_dp))
        ) {
            when (val state = brandState) {
                is UiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Success -> {
                    val filteredList = state.data.filter {
                        it.name.contains(searchText, ignoreCase = true)
                    }

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.data) { brand ->
                            BrandCardUI(
                                name = brand.name,
                                rating = brand.rating,
                                reviewText = brand.description,
                                // THIS triggers the navigation
                                onClick = { onClick(brand.id) }
                            )
                        }
                    }
                }
                is UiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: ${state.message}")
                    }
                }
            }

        }
    }
}


@Composable
fun SearchBarUI(text: String,
                onValueChange: (String) -> Unit,
                modifier: Modifier = Modifier) {

    // Custom search bar row
    Row(
        modifier = Modifier
            .height(dimensionResource(id=R.dimen.dimen_50_dp))
            .clip(RoundedCornerShape(dimensionResource(id=R.dimen.dimen_12_dp)))
            .background(Color.LightGray)
            .padding(horizontal = dimensionResource(id=R.dimen.dimen_12_dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Search icon
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            tint = Color.DarkGray
        )

        Spacer(modifier = Modifier.width(dimensionResource(id=R.dimen.dimen_8_dp)))

        // Text input for search query
        TextField(
            value = text,
            onValueChange = onValueChange,
            placeholder = { Text("Search brands", color = Color.DarkGray) },
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun BrandCardUI(
    name: String,
    rating: String,
    reviewText: String,
    onClick: () -> Unit
) {
    // Each brand displayed as a card with name, rating, and review text
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = dimensionResource(id=R.dimen.dimen_6_dp)),
        shape = RoundedCornerShape(dimensionResource(id=R.dimen.dimen_12_dp)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id=R.dimen.dimen_2_dp))
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id=R.dimen.dimen_16_dp))) {
            // Brand name and arrow icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, fontSize = 22.sp )
                Text(text = "➔", color = Color.Gray, fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.height(dimensionResource(id=R.dimen.dimen_8_dp)))

            // Star rating display
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(dimensionResource(id=R.dimen.dimen_18_dp))
                    )
                }
                Spacer(modifier = Modifier.width(dimensionResource(id=R.dimen.dimen_6_dp)))
                Text(text = rating, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(dimensionResource(id=R.dimen.dimen_4_dp)))
            // Review text description
            Text(text = reviewText, style = MaterialTheme.typography.bodySmall)
        }
    }
}

