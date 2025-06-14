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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.material3.CircularProgressIndicator
import androidx.hilt.navigation.compose.hiltViewModel
import com.code4galaxy.reviewnow.viewmodel.ReviewViewModel
import com.code4galaxy.reviewnow.model.UiState
import com.code4galaxy.reviewnow.model.Review
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@Composable
fun BrandDetailScreen(
    brandId: String,
    modifier: Modifier = Modifier,
    onSubmit: () -> Unit,
    viewModel: ReviewViewModel = hiltViewModel() // ✅ Injected ViewModel
) {
    // 🔹 Load and observe reviews
    LaunchedEffect(brandId) {
        viewModel.getReviewsForBrand(brandId)
    }
    val reviewState by viewModel.brandReviews.collectAsState()

    Column(modifier = modifier.padding(dimensionResource(id = R.dimen.dimen_16_dp))) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_28_dp))
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))

        Text(
            text = "Brand ID: $brandId", // You can replace with actual brand name from another ViewModel
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_10_dp)))

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
            Text("4.5", fontSize = 18.sp, fontWeight = FontWeight.Bold) // You can average this later
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_6_dp)))

        Text("Based on above reviews", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))

        Text(
            text = "Coffeehouse chain known for its signature toasties, light bites, and call culture.",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))

        OutlinedButton(onClick = onSubmit) {
            Text("Write a review", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_28_dp)))

        Text(
            text = "Reviews",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 🔹 Render reviews using UiState
        when (reviewState) {
            is UiState.Loading -> CircularProgressIndicator()

            is UiState.Error -> Text(
                text = (reviewState as UiState.Error).message,
                color = Color.Red,
                fontSize = 16.sp
            )

            is UiState.Success -> {
                val reviews = (reviewState as UiState.Success<List<Review>>).data

                if (reviews.isEmpty()) {
                    Text("No reviews yet. Be the first to write one!")
                } else {
                    reviews.forEach {
                        Spacer(modifier = Modifier.height(12.dp))
                        UserReview(name = it.userId, rating = it.rating.toFloat(), review = it.comment)
                    }
                }
            }
        }
    }
}

@Composable
fun UserReview(name: String, rating: Float, review: String) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_40_dp))
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dimen_10_dp)))
                Column {
                    Text(text = name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Row {
                        repeat(5) { i ->
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = if (rating >= i + 1) Color(0xFFFFC107) else Color.LightGray,
                                modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_16_dp))
                            )
                        }
                    }
                }
            }
            Text(rating.toString(), fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_6_dp)))

        Text(text = review, fontSize = 15.sp)
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