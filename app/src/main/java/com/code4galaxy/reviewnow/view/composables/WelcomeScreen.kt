package com.code4galaxy.reviewnow.view.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.code4galaxy.reviewnow.R

@Composable
fun WelcomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D426C)),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Sign in")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { /* Handle Google Sign In */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth(0.8f).border(
                    BorderStroke(1.dp, Color.Gray),
                    shape = RoundedCornerShape(8.dp)
                )

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.g_logo),
                    contentDescription = "Google Logo",
                    tint = Color.Unspecified, // Keep original image color
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text("Sign in with Google", color = Color.Black)



            }
            Spacer(modifier = Modifier.height(40.dp))
        }

        Text(
            text = "    Welcome to \n ReviewConnect",
            color = Color.Black,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,

            modifier = Modifier.align(Alignment.Center)
        )
    }
}
@Preview
@Composable
fun WelcomeScreenPreview(){
    WelcomeScreen()
}
