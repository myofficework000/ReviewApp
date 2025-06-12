package com.code4galaxy.reviewnow.view.feature.admin.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.code4galaxy.reviewnow.R

data class Review(
    val type: String,
    val rating: Int,
    val text: String,
    val profileIcon: ImageVector = Icons.Default.AccountCircle
)

@Composable
fun UserReviewsScreen(
    userName: String = "User Avene",
    reviews: List<Review> = listOf(
        Review("Brand", 3, "Quick service\nhighly recommended"),
        Review("User", 3, "Average products,\nbut easy")
    ),
    onBackClick: () -> Unit = {}
) {
    val dimen8 = dimensionResource(id = R.dimen.dimen_8_dp)
    val dimen12 = dimensionResource(id = R.dimen.dimen_12_dp)
    val dimen16 = dimensionResource(id = R.dimen.dimen_16_dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimen16)
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        Text(
            text = "User Reviews",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = dimen8)
        )

        Text(
            text = userName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = dimen16)
        )

        LazyColumn {
            items(reviews) { review ->
                ReviewCard(review)
                Spacer(modifier = Modifier.height(dimen12))
            }
        }
    }
}

@Composable
fun ReviewCard(review: Review) {
    val dimen4 = dimensionResource(id = R.dimen.dimen_4_dp)
    val dimen12 = dimensionResource(id = R.dimen.dimen_12_dp)
    val dimen18 = dimensionResource(id = R.dimen.dimen_18_dp)
    val dimen40 = dimensionResource(id = R.dimen.dimen_40_dp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = dimensionResource(id = R.dimen.dimen_1_dp),
                color = colorResource(R.color.gray).copy(alpha = 0.3f),
                shape = RoundedCornerShape(dimen12)
            )
            .padding(dimen12),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = review.profileIcon,
            contentDescription = "Profile Image",
            tint = colorResource(R.color.gray),
            modifier = Modifier
                .size(dimen40)
                .padding(end = dimen12)
        )

        Column {
            Text(text = review.type, fontWeight = FontWeight.Bold)

            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(5) { index ->
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = if (index < review.rating)
                            colorResource(R.color.gold)
                        else
                            colorResource(R.color.light_gray),
                        modifier = Modifier.size(dimen18)
                    )
                }
            }

            Spacer(modifier = Modifier.height(dimen4))

            Text(text = review.text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserReviewsScreenPreview() {
    UserReviewsScreen()
}
