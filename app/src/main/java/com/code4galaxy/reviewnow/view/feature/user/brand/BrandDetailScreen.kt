package com.code4galaxy.reviewnow.view.feature.user.brand

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code4galaxy.reviewnow.R

@Composable
fun BrandDetailScreen(
    modifier: Modifier = Modifier,
    brandId: String,
    onSubmit: () -> Unit
) {
    Column(modifier = modifier.padding(dimensionResource(id = R.dimen.dimen_16_dp))) {
        // Back arrow icon
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_28_dp))
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))
        // Brand title
        Text(
            text = "Brand ID: $brandId", // Replace with actual brand name from ViewModel if needed
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_10_dp)))
        // Static 5-star rating row
        Row(verticalAlignment = Alignment.CenterVertically) {
            repeat(5) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_20_dp))
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimen_6_dp)))
            Text(
                text = "4.5",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_6_dp)))
        // Total reviews count
        Text(
            text = "Based on 120 reviews",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))

        // Brand description
        Text(
            text = "Coffeehouse chain known for its signature toasis, light bites, and calls culture",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))

        // review button – triggers onSubmit callback
        OutlinedButton(onClick = onSubmit) {
            Text(text = "Write a review", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_28_dp)))

        // Section title for reviews
        Text(
            text = "Reviews",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Sample hardcoded reviews(should replace with real values)
        UserReview("Jane Doe", 4.0f, "Great coffee and femdly staff!")
        Spacer(modifier = Modifier.height(12.dp))
        UserReview("John Smith", 5.0f, "Love the atmosphore here.\nHighly recommend!")
    }
}

@Composable
fun UserReview(name: String, rating: Float, review: String) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // User icon and name + stars
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_40_dp))
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimen_10_dp)))
                Column {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    // Static 5 stars
                    Row {
                        repeat(5) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_16_dp))
                            )
                        }
                    }
                }
            }

            // Show rating value
            Text(
                text = rating.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_6_dp)))

        // User's review text
        Text(
            text = review,
            fontSize = 15.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBrandDetailScreen() {
    BrandDetailScreen(
        brandId = "b1",
        onSubmit = {}
    )
}