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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.code4galaxy.reviewnow.R

@Composable
fun RegisterScreen() {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.dimen_32_dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val dimen32sp = with(LocalDensity.current) {
                dimensionResource(id = R.dimen.dimen_32_sp).toSp()
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_80_dp)))

            Text(stringResource(R.string.create_account), fontSize = dimen32sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_24_dp)))

            OutlinedTextField(
                value = name,
                onValueChange = {name = it},
                label = { Text(stringResource(R.string.name)) },
                placeholder = { Text(stringResource(R.string.your_name)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_16_dp)))

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

            Button(
                onClick = { /* Handle register */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(stringResource(R.string.register), color = Color.White)
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_16_dp)))

            Text(
                text = stringResource(R.string.already_have_account),
                color = Color.Blue,
                modifier = Modifier.clickable {

                }
            )
        }
    }
}

@Preview
@Composable
private fun RegisterScreenPreview(){
    RegisterScreen()
}
