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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.code4galaxy.reviewnow.R

@Composable
fun FlaggedReviewScreen(
    onBackClick: () -> Unit = {},
    onReviewClick: () -> Unit = {}
) {
    val dimen1 = dimensionResource(id = R.dimen.dimen_1_dp)
    val dimen8 = dimensionResource(id = R.dimen.dimen_8_dp)
    val dimen12 = dimensionResource(id = R.dimen.dimen_12_dp)
    val dimen16 = dimensionResource(id = R.dimen.dimen_16_dp)
    val dimen18 = dimensionResource(id = R.dimen.dimen_18_dp)
    val dimen20 = dimensionResource(id = R.dimen.dimen_20_dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimen16)
    ) {
        // Back Button
        IconButton(onClick = onBackClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        // Screen Title
        Text(
            text = "Flagged Reviews",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = dimen16)
        )

        // Flagged Review Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(dimen1, colorResource(R.color.gray), shape = RoundedCornerShape(dimen12))
                .padding(dimen16)
        ) {
            Text(
                text = "Alice Bob", // Replace with userName
                fontWeight = FontWeight.Bold,
                fontSize = dimen18.value.sp
            )

            // Rating Stars
            Row(verticalAlignment = Alignment.CenterVertically) {
                repeat(4) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = colorResource(R.color.gold),
                        modifier = Modifier.size(dimen20)
                    )
                }
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star",
                    tint = colorResource(R.color.light_gray),
                    modifier = Modifier.size(dimen20)
                )
            }

            Spacer(modifier = Modifier.height(dimen8))

            Text(
                text = "Stupid product. Waste of money.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(dimen16))

            Button(
                onClick = onReviewClick,
                shape = RoundedCornerShape(dimen12),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Review")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlaggedReviewScreenPreview() {
    FlaggedReviewScreen()
}
