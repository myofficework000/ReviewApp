package com.code4galaxy.reviewnow.view.feature.user.reviews

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.code4galaxy.reviewnow.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MyReviewsScreen(
    modifier: Modifier = Modifier,
    reviews: List<Review> = listOf(
        // Sample hardcoded review list for preview
        Review(
            reviewId = "r1",
            userId = "u1",
            brandId = "b1",
            rating = 4.0f,
            reviewText = "Great coffee and femdly staff!",
            createdAt = 1717872000000 // Sample timestamp
        ),
        Review(
            reviewId = "r2",
            userId = "u1",
            brandId = "b1",
            rating = 5.0f,
            reviewText = "Love the atmosphere here. Highly recommend!",
            createdAt = 1717536000000
        )
    )
) {
    // Main vertical layout with padding
    Column(modifier = modifier.padding(dimensionResource(id = R.dimen.dimen_16_dp))) {
        // Top back icon
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_28_dp))
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))
        // Screen title
        Text("My Reviews", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))
        // Loop through each review and shows it
        reviews.forEach {
            ReviewItem(review = it)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Column {
        // Brand ID (should be replaced with actual brand name using brandId)
        Text("Brand ID: ${review.brandId}", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_4_dp)))
        // Row of stars (highlighted up to the rating)
        Row {
            repeat(5) { i ->
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = if (review.rating >= i + 1) Color(0xFFFFC107) else Color.LightGray,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_20_dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_4_dp)))
        // Formatted date of the review
        Text(review.createdAt.toReadableDate(), fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_4_dp)))

        Text(review.reviewText, fontSize = 16.sp)
    }
}

// Extension function to format timestamp to readable date
fun Long.toReadableDate(): String {
    val sdf = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
    return sdf.format(Date(this))
}

data class Review(
    val reviewId: String = "",
    val userId: String = "",
    val brandId: String = "",
    val rating: Float = 0f,
    val reviewText: String = "",
    val isFlagged: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
// Data class representing a Review object
@Preview(showBackground = true)
@Composable
fun PreviewMyReviewsScreen() {
    MyReviewsScreen()
}