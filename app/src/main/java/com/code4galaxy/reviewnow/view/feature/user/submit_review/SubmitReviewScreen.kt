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

@Composable
fun SubmitReviewScreen(
    brandId: String,
    modifier: Modifier = Modifier,
    onSubmit: (rating: Float, review: String) -> Unit = { _, _ -> }
) {
    // State for rating and review text input
    var rating by remember { mutableStateOf(0f) }
    var review by remember { mutableStateOf("") }

    // Layout starts
    Column(modifier = modifier.padding(dimensionResource(id = R.dimen.dimen_16_dp))) {
        // Top back arrow icon (non-functional here)
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_28_dp))
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))

        // Brand title (replace with real brand name using brandId + ViewModel)
        Text(
            text = "Example Brand",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))

        Text("Rating", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_6_dp)))
        // Star rating bar (clickable)
        Row {
            for (i in 1..5) {
                IconButton(onClick = { rating = i.toFloat() }) {
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

        // Review label
        Text("Review", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_6_dp)))

        // Text input field for review
        OutlinedTextField(
            value = review,
            onValueChange = { review = it },
            placeholder = { Text("Write your review") },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.dimen_120_dp))
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_20_dp)))

        // Submit button (calls onSubmit with current state)
        Button(
            onClick = { onSubmit(rating, review) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SUBMIT")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSubmitReviewScreen() {
    SubmitReviewScreen(
        brandId = "b1",
        onSubmit = { rating, review ->
            println("Rating: $rating, Review: $review")
        }
    )
}