package com.code4galaxy.reviewnow.view.composables

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.model.User
import com.code4galaxy.reviewnow.view.RegisterState
import com.code4galaxy.reviewnow.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegisterScreen(onSignInClick: () -> Unit,
                   authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val registerState by authViewModel.registerState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // Handle side effects (toasts & navigation)
    LaunchedEffect(registerState) {
        when (registerState) {
            is RegisterState.Success -> {
                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                authViewModel.resetRegisterState()
                onSignInClick()
            }
            is RegisterState.Error -> {
                Toast.makeText(context, (registerState as RegisterState.Error).message, Toast.LENGTH_LONG).show()
                authViewModel.resetRegisterState()
            }
            else -> {}
        }
    }



    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.dimen_32_dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val dimen20sp = with(LocalDensity.current) {
                dimensionResource(id = R.dimen.dimen_20_sp).toSp()
            }
            val dimen48sp = with(LocalDensity.current) {
                dimensionResource(id = R.dimen.dimen_48_sp).toSp()
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_56_dp)))

            Text(stringResource(R.string.sign_up), fontSize = dimen48sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_32_dp)))

            OutlinedTextField(
                value = email,

                onValueChange = {email = it},
                label = { Text(stringResource(R.string.email)) },
                placeholder = { Text(stringResource(R.string.example_email)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_16_dp)))

            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = { Text(stringResource(R.string.password)) },
                placeholder = { Text(stringResource(R.string.example_password)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_24_dp)))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {confirmPassword = it},
                label = { Text(stringResource(R.string.confirm_password)) },
                placeholder = { Text(stringResource(R.string.example_password)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_32_dp)))


            Button(
                onClick = { authViewModel.registerUser(email, password, confirmPassword)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text(stringResource(R.string.register), fontSize = dimen20sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_16_dp)))

            Box(
                modifier = Modifier
                    .fillMaxSize().padding(bottom = dimensionResource(R.dimen.dimen_32_dp))
                    .padding(16.dp)
            ) {
                Column (
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                        .align(Alignment.Center),horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val dimen24sp = with(LocalDensity.current) {
                        dimensionResource(id = R.dimen.dimen_24_sp).toSp()
                    }
                    val dimen20sp = with(LocalDensity.current) {
                        dimensionResource(id = R.dimen.dimen_20_sp).toSp()
                    }
                    Text(text = stringResource(R.string.already_have_account),
                        fontSize = dimen24sp,
                        fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_4_dp)))
                    Text(
                        text = stringResource(R.string.sign_in),
                        color = Color.Blue,
                        fontSize = dimen20sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            onSignInClick()
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        // Provide fake callbacks or sample state here
        onSignInClick = {}
    )
}
