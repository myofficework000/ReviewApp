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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import com.code4galaxy.reviewnow.view.navigation.Screen
import com.code4galaxy.reviewnow.viewmodel.AuthViewModel
import com.code4galaxy.reviewnow.viewmodel.ForgotPasswordState
import com.code4galaxy.reviewnow.viewmodel.LoginState

// TODO(abeer) after logging it does not go to right screen.user type should be detected and accordingly should be navigated.
@Composable
fun LoginScreen(
    navController: NavController,
    onRegisterClick: () -> Unit,
    onSignInClick: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel = hiltViewModel()
) {
    val loginState by authViewModel.loginState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(loginState) {
        when (loginState) {
            is LoginState.Success -> {
                authViewModel.resetLoginState()
                val userType = (loginState as LoginState.Success).userType
                if (userType == "user") {
                    navController.navigate(Screen.USER.route)
                } else if (userType == "admin") {
                    navController.navigate(Screen.ADMIN.route)
                } else {
                    Toast.makeText(context, "Unknown user type", Toast.LENGTH_SHORT).show()
                }
            }
            is LoginState.Error -> {
                Toast.makeText(
                    context,
                    (loginState as LoginState.Error).message,
                    Toast.LENGTH_LONG
                ).show()
                authViewModel.resetLoginState()
            }
            else -> {}
        }
    }


    val forgotPasswordState by authViewModel.forgotPasswordState.collectAsState()

    LaunchedEffect(forgotPasswordState) {
        when (forgotPasswordState) {
            is ForgotPasswordState.Success -> {
                Toast.makeText(context, (forgotPasswordState as ForgotPasswordState.Success).message, Toast.LENGTH_LONG).show()
                authViewModel.resetForgotPasswordState()
            }
            is ForgotPasswordState.Error -> {
                Toast.makeText(context, (forgotPasswordState as ForgotPasswordState.Error).message, Toast.LENGTH_LONG).show()
                authViewModel.resetForgotPasswordState()
            }
            else -> {}
        }
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var resetEmail by remember { mutableStateOf("") }

    val dimen48sp = with(LocalDensity.current) {
        dimensionResource(id = R.dimen.dimen_48_sp).toSp()
    }
    val dimen12sp = with(LocalDensity.current) {
        dimensionResource(id = R.dimen.dimen_12_sp).toSp()
    }
    val dimen24sp = with(LocalDensity.current) {
        dimensionResource(id = R.dimen.dimen_24_sp).toSp()
    }
    val dimen20sp = with(LocalDensity.current) {
        dimensionResource(id = R.dimen.dimen_20_sp).toSp()
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = dimensionResource(R.dimen.dimen_32_dp))
    ) {
        val (
            title,
            emailField,
            passwordField,
            forgotPasswordText,
            signInButton,
            registerPrompt,
            registerText
        ) = createRefs()


        val guideline = createGuidelineFromTop(0.3f)

        Text(
            text = stringResource(R.string.sign_in),
            fontSize = dimen48sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(guideline)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                }
        )
        val spacing = dimensionResource(R.dimen.dimen_24_dp)

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            placeholder = { Text("example@email.com") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(emailField) {
                    top.linkTo(title.bottom, margin = spacing)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            placeholder = { Text("*****") },
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = description)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(passwordField) {
                    top.linkTo(emailField.bottom, margin = spacing)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        val spacing16dp = dimensionResource(R.dimen.dimen_24_dp)

        var showResetPasswordDialog by remember { mutableStateOf(false) }

        Text(
            text = stringResource(R.string.forget_password),
            fontSize = dimen12sp,
            color = Color.Blue,
            modifier = Modifier
                .constrainAs(forgotPasswordText) {
                    top.linkTo(passwordField.bottom, margin = spacing16dp)
                    end.linkTo(parent.end)
                }
                .clickable { showResetPasswordDialog = true }
        )

        // Show the dialog if user clicked on "Forget Password"
        if (showResetPasswordDialog) {
            AlertDialog(
                onDismissRequest = { showResetPasswordDialog = false },
                title = { Text("Reset Password") },
                text = {
                    OutlinedTextField(
                        value = resetEmail,
                        onValueChange = { resetEmail = it },
                        label = { Text("Email") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            authViewModel.sendPasswordResetEmail(resetEmail.trim())
                            showResetPasswordDialog = false
                        }
                    ) {
                        Text("Send")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showResetPasswordDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
        val spacing32dp = dimensionResource(R.dimen.dimen_24_dp)

        Button(
            onClick = { authViewModel.loginUser(email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.dimen_48_dp))
                .constrainAs(signInButton) {
                    top.linkTo(forgotPasswordText.bottom, margin = spacing32dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(stringResource(R.string.sign_in), color = Color.White)
        }

        val spacing4dp = dimensionResource(R.dimen.dimen_24_dp)

        Text(
            text = stringResource(R.string.dont_have_account),
            fontSize = dimen24sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(registerPrompt) {
                bottom.linkTo(registerText.top, margin = spacing4dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        Text(
            text = stringResource(R.string.register),
            color = Color.Blue,
            fontSize = dimen20sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .clickable { onRegisterClick() }
                .constrainAs(registerText) {
                    bottom.linkTo(parent.bottom, margin = spacing32dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}


//@Preview
//@Composable
//private fun LoginScreenPreview() {
//    LoginScreen(
//        onRegisterClick = {},
//        onSignInClick = {}
//    )
//}