package com.code4galaxy.reviewnow.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code4galaxy.reviewnow.R

@Composable
fun LoginScreen(onRegisterClick: () -> Unit = {}) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(200.dp))

            Text(
                text = "Welcome back \n to ReviewConnect",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Email",
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("example@email.com") },
                trailingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Password",
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                placeholder = { Text("*****") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Forget Password",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_24_dp)))

            Button(
                onClick = {
                    // Handle login
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.dimen_48_dp)),
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {
                Text(text = stringResource(R.string.sign_in), color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Don't have an account?")
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Register",
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        onRegisterClick()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}