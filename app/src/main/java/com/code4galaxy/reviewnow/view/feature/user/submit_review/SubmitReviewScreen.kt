package com.code4galaxy.reviewnow.view.feature.user.submit_review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.model.Review
import com.code4galaxy.reviewnow.model.UiState
import com.code4galaxy.reviewnow.view.navigation.Screen
import com.code4galaxy.reviewnow.viewmodel.ReviewViewModel
import com.code4galaxy.reviewnow.viewmodel.UserViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay

@Composable
fun SubmitReviewScreen(
    brandId: String,
    userId: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ReviewViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
) {

    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }

    val userState by userViewModel.userDataState.collectAsState()
    val submitState by viewModel.submitReviewState.collectAsState()

    var userName by remember { mutableStateOf("") }

    LaunchedEffect(userId) {
        userViewModel.getUserData(userId)
      
    }

    LaunchedEffect(userState) {
        if (userState is UiState.Success) {
            userName = (userState as UiState.Success).data.name
        } else if (userState is UiState.Error) {
            userName = "Unknown"
        }

    }

    Column(modifier = modifier.fillMaxSize().padding(dimensionResource(id = R.dimen.dimen_16_dp))) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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


