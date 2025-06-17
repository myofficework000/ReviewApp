package com.code4galaxy.reviewnow.view.feature.user.reviews

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.code4galaxy.reviewnow.viewmodel.ReviewViewModel
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.model.Review
import com.code4galaxy.reviewnow.model.UiState
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toReadableDate(): String {
    val sdf = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
    return sdf.format(Date(this))
}

@Composable
fun MyReviewsScreen(
    modifier: Modifier = Modifier,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val reviewState by viewModel.userReviews.collectAsState()

    // Trigger load once
    LaunchedEffect(Unit) {
        viewModel.getReviewsForUser(userId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.dimen_16_dp))
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_28_dp))
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))

        Text("My Reviews", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))

        when (reviewState) {
            is UiState.Loading -> CircularProgressIndicator()

            is UiState.Success -> {
                val reviews = (reviewState as UiState.Success<List<Review>>).data
                if (reviews.isEmpty()) {
                    Text("No reviews found.")
                } else {
                    reviews.forEach {
                        ReviewItem(it)
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))
                    }
                }
            }

            is UiState.Error -> {
                Text(
                    text = "Error: ${(reviewState as UiState.Error).message}",
                    color = Color.Red
                )
            }
        }
    }
}



@Composable
fun ReviewItem(review: Review) {
    var userName by remember { mutableStateOf("Loading...") }

    // Fetch the user's name from Firestore using userId
    LaunchedEffect(review.userId) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(review.userId)
            .get()
            .addOnSuccessListener { document ->
                userName = document.getString("name") ?: "Unknown"
            }
            .addOnFailureListener {
                userName = "Unknown"
            }
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Icon( // user icon
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "User Icon",
            modifier = Modifier
                .size(32.dp)
                .padding(end = 8.dp)
        )

        Column {
            Text("User: $userName", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)

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
            Text(review.comment, fontSize = 16.sp)
            Text(review.timestamp.toReadableDate(), fontSize = 14.sp, color = Color.Gray)
        }
        Text(
            text = review.rating.toString(),
            modifier = Modifier.align(Alignment.CenterVertically),
            fontWeight = FontWeight.Medium
        )
    }
}



