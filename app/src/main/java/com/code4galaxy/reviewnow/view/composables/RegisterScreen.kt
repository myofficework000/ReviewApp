@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.code4galaxy.reviewnow.R
import com.code4galaxy.reviewnow.view.RegisterState
import com.code4galaxy.reviewnow.view.navigation.Screen
import com.code4galaxy.reviewnow.viewmodel.AuthViewModel
import com.code4galaxy.reviewnow.viewmodel.NavigationViewModel


@Composable
fun RegisterScreen(
    onSignInClick: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
    navigationViewModel: NavigationViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val registerState by authViewModel.registerState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val userTypes = listOf("user", "admin")
    var expanded by remember { mutableStateOf(false) }
    var selectedUserType by remember { mutableStateOf(userTypes[0]) }

    val dimen20sp = with(LocalDensity.current) { dimensionResource(R.dimen.dimen_20_sp).toSp() }
    val dimen24sp = with(LocalDensity.current) { dimensionResource(R.dimen.dimen_24_sp).toSp() }
    val dimen48sp = with(LocalDensity.current) { dimensionResource(R.dimen.dimen_48_sp).toSp() }
    val spacing16dp = dimensionResource(R.dimen.dimen_16_dp)
    val spacing32dp = dimensionResource(R.dimen.dimen_32_dp)
    val spacing56dp = dimensionResource(R.dimen.dimen_56_dp)
    val spacing100dp = dimensionResource(R.dimen.dimen_100_dp)

    LaunchedEffect(registerState) {
        when (registerState) {
            is RegisterState.Success -> {
                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                authViewModel.resetRegisterState()

                if (selectedUserType == "user") {
                    navController.navigate(Screen.USER.route)
                } else {
                    navController.navigate(Screen.ADMIN.route)
                }
            }

            is RegisterState.Error -> {
                Toast.makeText(context, (registerState as RegisterState.Error).message, Toast.LENGTH_LONG).show()
                authViewModel.resetRegisterState()
            }

            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(spacing32dp)
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (
                title, emailField, passwordField, confirmField,
                dropdownField, button, bottomText
            ) = createRefs()

            Text(
                text = stringResource(R.string.sign_up),
                fontSize = dimen48sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top, margin = spacing56dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(R.string.email)) },
                placeholder = { Text(stringResource(R.string.example_email)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(emailField) {
                        top.linkTo(title.bottom, margin = spacing32dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(R.string.password)) },
                placeholder = { Text(stringResource(R.string.example_password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(passwordField) {
                        top.linkTo(emailField.bottom, margin = spacing16dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text(stringResource(R.string.confirm_password)) },
                placeholder = { Text(stringResource(R.string.example_password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(confirmField) {
                        top.linkTo(passwordField.bottom, margin = spacing16dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.constrainAs(dropdownField) {
                    top.linkTo(confirmField.bottom, margin = spacing16dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = selectedUserType,
                    onValueChange = {},
                    label = { Text("Select Role") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    userTypes.forEach { role ->
                        DropdownMenuItem(
                            text = { Text(role.capitalize()) },
                            onClick = {
                                selectedUserType = role
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {

                    if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                        Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (password != confirmPassword) {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    authViewModel.registerUser(email, password, confirmPassword, selectedUserType)
                    navigationViewModel.saveUserType(selectedUserType)



                },
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(button) {
                        top.linkTo(dropdownField.bottom, margin = spacing100dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text(stringResource(R.string.register), fontSize = dimen20sp, color = Color.White)
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.constrainAs(bottomText) {
                    bottom.linkTo(parent.bottom, margin = spacing32dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                Text(
                    text = stringResource(R.string.already_have_account),
                    fontSize = dimen20sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_4_dp)))
                Text(
                    text = stringResource(R.string.sign_in),
                    color = Color.Blue,
                    fontSize = dimen20sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onSignInClick() }
                )
            }
        }
    }


}




//@Preview(showBackground = true)
//@Composable
//fun RegisterScreenPreview() {
//    RegisterScreen(
//        // Provide fake callbacks or sample state here
//        onSignInClick = {}
//    )
//}
