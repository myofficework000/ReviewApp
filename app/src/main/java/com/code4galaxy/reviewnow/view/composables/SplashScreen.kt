package com.code4galaxy.reviewnow.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.code4galaxy.reviewnow.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000) // Delay 2 seconds
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0563AD)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_reviews_24), //  logo
                contentDescription = "App Logo",
                tint = Color.Unspecified,
                modifier = Modifier.size(dimensionResource(R.dimen.dimen_100_dp))
            )
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_16_dp)))
            val dimen32sp = with(LocalDensity.current) {
                dimensionResource(id = R.dimen.dimen_32_sp).toSp()
            }
            Text(
                text = stringResource(R.string.review_connect),
                fontSize = dimen32sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xF8FFFFFF)
            )
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview(){
    SplashScreen(onTimeout = {} )
}