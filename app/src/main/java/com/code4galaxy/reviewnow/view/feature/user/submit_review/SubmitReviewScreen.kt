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
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.model.Review
import com.code4galaxy.reviewnow.model.UiState
import com.code4galaxy.reviewnow.viewmodel.ReviewViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.code4galaxy.reviewnow.view.navigation.Screen
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay

@Composable
fun SubmitReviewScreen(
    brandId: String,
    userId: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ReviewViewModel = hiltViewModel()
) {

    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }

    val submitState by viewModel.submitReviewState.collectAsState()

    var userName by remember { mutableStateOf("Loading...") }

    LaunchedEffect(userId) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                userName = document.getString("name") ?: "Unknown"
            }
            .addOnFailureListener {
                userName = "Unknown"
            }
    }

    Column(modifier = modifier.padding(dimensionResource(id = R.dimen.dimen_16_dp))) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.size(dimensionResource(id = R.dimen.dimen_28_dp))
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_16_dp)))

        Text("Hello, $userName", fontSize = 18.sp, fontWeight = FontWeight.Medium)

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dimen_12_dp)))

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

        val submitState by viewModel.submitReviewState.collectAsState()

        when (submitState) {
            is UiState.Success -> Text(
                text = "Review submitted!",
                color = Color.Green,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            is UiState.Error -> Text(
                text = "Error: ${(submitState as UiState.Error).message}",
                color = Color.Red,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            else -> {}
        }

        LaunchedEffect(submitState) {
            if (submitState is UiState.Success) {
                // Delay optional: gives time for UI to show success message
                delay(4000)
                navController.navigate(Screen.MyReviews.route) {
                    popUpTo(Screen.SubmitReview.route) { inclusive = true }
                }
            }
        }
    }
}



