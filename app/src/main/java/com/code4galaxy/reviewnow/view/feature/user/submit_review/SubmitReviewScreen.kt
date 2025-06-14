package com.code4galaxy.reviewnow.view.feature.user.submit_review

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.model.Review
import com.code4galaxy.reviewnow.model.UiState
import com.code4galaxy.reviewnow.viewmodel.ReviewViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material.icons.automirrored.filled.ArrowBack

@Composable
fun SubmitReviewScreen(
    brandId: String,
    userId: String,
    modifier: Modifier = Modifier,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }

    val submitState by viewModel.submitReviewState.collectAsState()

    Column(modifier = modifier.padding(dimensionResource(id = R.dimen.dimen_16_dp))) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_28_dp))
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))

        Text(
            text = "Write a Review",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))

        Text("Rating", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_6_dp)))

        Row {
            for (i in 1..5) {
                IconButton(onClick = { rating = i }) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (rating >= i) Color(0xFFFFC107) else Color.LightGray,
                        modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_32_dp))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_20_dp)))

        Text("Review", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_6_dp)))

        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            placeholder = { Text("Write your review") },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.dimen_120_dp))
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_20_dp)))

        Button(
            onClick = {
                val newReview = Review(
                    userId = userId,
                    brandId = brandId,
                    rating = rating,
                    comment = comment
                )
                viewModel.submitReview(newReview)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SUBMIT")
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))

        when (submitState) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Success -> Text("Review submitted!", color = Color.Green)
            is UiState.Error -> Text("Error: ${(submitState as UiState.Error).message}", color = Color.Red)
        }
    }
}