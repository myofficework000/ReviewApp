package com.code4galaxy.reviewnow.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.code4galaxy.reviewnow.R

@Composable
fun LoginScreen(onRegisterClick: () -> Unit,onSignInClick:()->Unit) {
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
                .padding(horizontal = dimensionResource(R.dimen.dimen_32_dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_200_dp)))

            val dimen48sp = with(LocalDensity.current) {
                dimensionResource(id = R.dimen.dimen_48_sp).toSp()
            }

            Text(
                text = stringResource(id = R.string.sign_in),
                fontSize = dimen48sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_24_dp)))
            val dimen12sp = with(LocalDensity.current) {
                dimensionResource(id = R.dimen.dimen_12_sp).toSp()
            }

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                placeholder = { Text("example@email.com") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_24_dp)))

            var passwordVisible by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                placeholder = { Text("*****") },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff

                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_16_dp)))

            Text(
                text = stringResource(R.string.forget_password),
                fontSize = dimen12sp,
                color = Color.Blue,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_32_dp)))

            Button(
                onClick = {
                   onSignInClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.dimen_48_dp)),
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Text(text = stringResource(R.string.sign_in), color = Color.White)
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_16_dp)))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = dimensionResource(R.dimen.dimen_32_dp))
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val dimen24sp = with(LocalDensity.current) {
                        dimensionResource(id = R.dimen.dimen_24_sp).toSp()
                    }
                    val dimen20sp = with(LocalDensity.current) {
                        dimensionResource(id = R.dimen.dimen_20_sp).toSp()
                    }
                    Text(
                        text = stringResource(R.string.dont_have_account),
                        fontSize = dimen24sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_4_dp)))
                    Text(
                        text = stringResource(R.string.register),
                        color = Color.Blue,
                        fontSize = dimen20sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            onRegisterClick()
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        onRegisterClick = {},  // Dummy lambdas for preview
        onSignInClick = {}
    )
}