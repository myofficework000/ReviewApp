package com.code4galaxy.reviewnow.view.feature.common.authenticaton

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.code4galaxy.reviewnow.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun WelcomeScreen() {
    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful) {
                        Toast.makeText(context, "Google Sign-In Success", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Sign-In Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: ApiException) {
            Toast.makeText(context, "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    val googleSignInClient = remember {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id)) // Get from google-services.json
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(dimensionResource(R.dimen.dimen_24_dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D426C)),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(stringResource(R.string.sign_in))
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_12_dp)))

            Button(
                onClick = {
                    val signInIntent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .border(
                        BorderStroke(dimensionResource(R.dimen.dimen_1_dp), Color.Gray),
                        shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_8_dp))
                    )

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.g_logo),
                    contentDescription = "Google Logo",
                    tint = Color.Unspecified, // Keep original image color
                    modifier = Modifier.size(dimensionResource(R.dimen.dimen_24_dp))
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.dimen_16_dp)))
                Text(stringResource(R.string.sign_in_with_Google), color = Color.Black)


            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_40_dp)))
        }

        val dimen36sp = with(LocalDensity.current) {
            dimensionResource(id = R.dimen.dimen_36_sp).toSp()
        }

        Text(
            text = stringResource(R.string.welcome_to_reviewconnect),
            color = Color.Black,
            fontSize = dimen36sp,
            fontWeight = FontWeight.Bold,

            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}
